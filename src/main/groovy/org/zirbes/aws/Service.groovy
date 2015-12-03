package org.zirbes.aws

import com.amazonaws.services.ec2.model.Image

import groovy.transform.CompileStatic

@CompileStatic
class Service implements Comparable<Service> {

    AmiVersionInfo info
    Image image
    List<String> snapshotIds = []

    Service(Image image) {
        this.image = image
        this.info = new AmiVersionInfo(image.name)
    }

    String getName() { info.name }

    boolean keep = true

    @Override
    int compareTo(Service other) {
        this.info <=> other.info
    }

}
