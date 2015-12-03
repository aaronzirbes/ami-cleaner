package org.zirbes.aws

import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.ec2.AmazonEC2
import com.amazonaws.services.ec2.AmazonEC2Client
import com.amazonaws.services.ec2.model.BlockDeviceMapping
import com.amazonaws.services.ec2.model.DeleteSnapshotRequest
import com.amazonaws.services.ec2.model.DeregisterImageRequest
import com.amazonaws.services.ec2.model.DescribeImagesRequest
import com.amazonaws.services.ec2.model.DescribeImagesResult
import com.amazonaws.services.ec2.model.DescribeSnapshotsRequest
import com.amazonaws.services.ec2.model.Filter
import com.amazonaws.services.ec2.model.Image
import com.amazonaws.services.ec2.model.Snapshot
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClient
import com.fasterxml.jackson.databind.ObjectMapper

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

@CompileStatic
@Slf4j
class AmiCleaner {

    ObjectMapper objectMapper = new ObjectMapper()

    AWSCredentialsProvider credProvider = new ProfileCredentialsProvider()

    void clean() {
        log.info "Querying AWS."
        Collection<Service> services = findSnapshots()
        findLatest().each{ services << it }
        log.info "Looking for things to delete."
        markForRemoval(services)
        decorateSnapshot(services)
        logImages(services)
    }

    protected boolean getDryRun() {
        String isDryRun = System.getProperty('dryRun')
        if (isDryRun == 'false') {return false }
        return true
    }

    protected Collection<Service> findSnapshots() {
        String snapshotQuery = "*-SNAPSHOT*"
        //String snapshotQuery = "*entity-*-SNAPSHOT*"

        // use a sorted map
        Collection<Service> images = [] as SortedSet

        Filter filter = new Filter('name', [snapshotQuery])
        DescribeImagesRequest describeImagesRequest = new DescribeImagesRequest().withFilters(filter)
                .withOwners(ownerId)
        DescribeImagesResult describeImagesResult =  ec2.describeImages(describeImagesRequest)
        describeImagesResult.images.each{ Image image -> images << new Service(image) }
        return images
    }

    protected Collection<Service> findLatest() {
        String snapshotQuery = "*-LATEST*"

        // use a sorted map
        Collection<Service> images = [] as SortedSet


        Filter filter = new Filter('name', [snapshotQuery])
        DescribeImagesRequest describeImagesRequest = new DescribeImagesRequest().withFilters(filter)
                .withOwners(ownerId)
        DescribeImagesResult describeImagesResult =  ec2.describeImages(describeImagesRequest)
        describeImagesResult.images.each{ Image image -> images << new Service(image) }
        return images
    }

    protected Collection<Service> markForRemoval(Collection<Service> allServices) {
        allServices.groupBy{ it.info.service }.each{ String svcName, Collection<Service> services ->
            if (services.size() > 2) {
                // mark services for removal
                services[0..-3].each{ Service service -> service.keep = false }
            }
        }
        return allServices
    }

    protected Collection<Service> decorateSnapshot(Collection<Service> services) {
        services.findAll{ !it.keep }.each{ Service service ->
            service.image.blockDeviceMappings.each{ BlockDeviceMapping mapping ->
                if (mapping.ebs) {
                    String snapshotId = mapping.ebs.snapshotId
                    service.snapshotIds << snapshotId
                } else {
                    log.debug "found non-snapshot mapping: ${mapping}, ignoring."
                }
            }
            DescribeSnapshotsRequest request = new DescribeSnapshotsRequest()
        }
        return services
    }

    void logImages(Collection<Service> allServices) {
        int deleteCount = 0
        String verb = 'REMOVING'
        if (dryRun) { verb = 'would remove' }
        allServices.groupBy{ it.info.service }.each{ String svcName, Collection<Service> services ->
            log.info "${svcName}"
            services.each{ Service service ->
                Image image = service.image
                if (service.keep) {
                    log.info " * ${image.imageId} (${service.info}) - keeping."
                } else {
                    log.warn " * ${image.imageId} (${service.info}) - ${verb}"
                    service.snapshotIds.each{ String snapshotId ->
                        log.warn "   * ${snapshotId} - ${verb}"
                    }

                    deleteCount++
                    DeregisterImageRequest deregImage = new DeregisterImageRequest().withImageId(image.imageId)
                    if (!dryRun) {
                        ec2.deregisterImage(deregImage)
                        log.warn " * DELETED EC2 imageId=${image.imageId}"
                    }
                    service.snapshotIds.each{ String snapshotId ->
                        DeleteSnapshotRequest delSnap = new DeleteSnapshotRequest().withSnapshotId(snapshotId)
                        if (!dryRun) {
                            ec2.deleteSnapshot(delSnap)
                            log.warn "   * DELETED EC2 snapshotId=${snapshotId}"
                        }
                    }
                }
            }
        }

        log.info "Found ${deleteCount} AMIs and snapshots to be removed out of ${allServices.size()} images."
        BigDecimal spaceSaved = deleteCount * 8.0

        // https://aws.amazon.com/ebs/pricing/
        BigDecimal costSaved = spaceSaved * 0.10

        if (spaceSaved > 1000) {
            log.info "Cleaned up ${spaceSaved / 1000} TB"

        } else {
            log.info "Cleaned up ${spaceSaved} GB"
        }
        log.info "Saving \$${costSaved} / month"
    }

    protected AmazonEC2 getEc2() {
        new AmazonEC2Client(credProvider.credentials)
    }

    protected String getOwnerId() {
        AmazonIdentityManagementClient iamClient = new AmazonIdentityManagementClient(credProvider)
        String arn = iamClient.user.user.arn
        return arn.replaceFirst(/arn:aws:iam::/, '').replaceFirst(/:.*/, '')
    }

}
