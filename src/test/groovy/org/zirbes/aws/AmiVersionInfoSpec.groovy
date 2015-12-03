package org.zirbes.aws

import spock.lang.Specification
import spock.lang.Unroll

class AmiVersionInfoSpec extends Specification {

    @Unroll
    void 'can parse version info from AMI #name'() {
        when:
        AmiVersionInfo info = new AmiVersionInfo(name)

        then:
        info.service == service
        info.version == version
        info.snapshot == snapshot
        info.timestamp == timestamp
        info.valid == valid

        where:
        name                                                              | service                        | version   | snapshot | timestamp             | valid
        'base-ami'                                                        | 'base-ami'                     | null      | false    | null                  | false
        'kafka-ami-2015-10-16_19-17-32'                                   | 'kafka-ami'                    | null      | false    | '2015-10-16_19-17-32' | false
        'authentication-service-0.47.0-SNAPSHOT-2015-10-16_19-17-32'      | 'authentication-service'       | '0.47.0'  | true     | '2015-10-16_19-17-32' | true
        'comms-device-service-1.12.0-SNAPSHOT-2015-09-23_14-14-31'        | 'comms-device-service'         | '1.12.0'  | true     | '2015-09-23_14-14-31' | true
        'comms-router-1.18.0-SNAPSHOT-2015-10-16_23-03-54'                | 'comms-router'                 | '1.18.0'  | true     | '2015-10-16_23-03-54' | true
        'entity-service-3.5.0-SNAPSHOT-2015-10-19_20-22-13'               | 'entity-service'               | '3.5.0'   | true     | '2015-10-19_20-22-13' | true
        'oem-portal-0.20.0-SNAPSHOT-2015-06-01_19-00-46'                  | 'oem-portal'                   | '0.20.0'  | true     | '2015-06-01_19-00-46' | true
        'authentication-service-0.37.0-SNAPSHOT-2015-09-03_18-10-04'      | 'authentication-service'       | '0.37.0'  | true     | '2015-09-03_18-10-04' | true
        'com.peoplenet.entity-gateway-0.20.0-SNAPSHOT'                    | 'entity-gateway'               | '0.20.0'  | true     | null                  | true
        'entity-gateway-3.10.0-SNAPSHOT-2015-10-29_20-41-22'              | 'entity-gateway'               | '3.10.0'  | true     | '2015-10-29_20-41-22' | true
        'trojan-horse-0.1.0-SNAPSHOT-2015-11-16_20-16-30'                 | 'trojan-horse'                 | '0.1.0'   | true     | '2015-11-16_20-16-30' | true
        'oem-portal-1.25.0-SNAPSHOT-2015-07-24_18-53-30'                  | 'oem-portal'                   | '1.25.0'  | true     | '2015-07-24_18-53-30' | true
        'siege-engine-1.2.0-SNAPSHOT-2015-06-04_16-29-27'                 | 'siege-engine'                 | '1.2.0'   | true     | '2015-06-04_16-29-27' | true
        'admin-portal-0.21.0-SNAPSHOT-2015-07-14_17-04-42'                | 'admin-portal'                 | '0.21.0'  | true     | '2015-07-14_17-04-42' | true
        'oem-portal-1.36.0-SNAPSHOT-2015-10-22_22-04-53'                  | 'oem-portal'                   | '1.36.0'  | true     | '2015-10-22_22-04-53' | true
        'leanstats-service-0.3.0-SNAPSHOT-2015-06-26_19-32-53'            | 'leanstats-service'            | '0.3.0'   | true     | '2015-06-26_19-32-53' | true
        'com.peoplenet.security-gateway-1.1.0-SNAPSHOT'                   | 'security-gateway'             | '1.1.0'   | true     | null                  | true
        'search-migration-10.3.0-SNAPSHOT-2015-11-11_15-56-52'            | 'search-migration'             | '10.3.0'  | true     | '2015-11-11_15-56-52' | true
        'oem-portal-0.13.0-SNAPSHOT-2015-05-18_15-36-28'                  | 'oem-portal'                   | '0.13.0'  | true     | '2015-05-18_15-36-28' | true
        'oem-portal-0.14.0-SNAPSHOT-2015-05-18_20-39-27'                  | 'oem-portal'                   | '0.14.0'  | true     | '2015-05-18_20-39-27' | true
        'oem-portal-0.16.0-SNAPSHOT-2015-05-21_22-18-49'                  | 'oem-portal'                   | '0.16.0'  | true     | '2015-05-21_22-18-49' | true
        'oem-portal-0.10.11-SNAPSHOT-2015-04-24_14-08-10'                 | 'oem-portal'                   | '0.10.11' | true     | '2015-04-24_14-08-10' | true
        'comms-vehicle-service-0.15.0-SNAPSHOT-2015-11-03_18-32-04'       | 'comms-vehicle-service'        | '0.15.0'  | true     | '2015-11-03_18-32-04' | true
        'oem-portal-0.12.7-SNAPSHOT-2015-05-11_20-57-15'                  | 'oem-portal'                   | '0.12.7'  | true     | '2015-05-11_20-57-15' | true
        'com.peoplenet.security-gateway-1.0.3-SNAPSHOT'                   | 'security-gateway'             | '1.0.3'   | true     | null                  | true
        'admin-gateway-service-0.1.0-SNAPSHOT-2015-09-15_19-27-26'        | 'admin-gateway-service'        | '0.1.0'   | true     | '2015-09-15_19-27-26' | true
        'epo-install-packages-0.6.0-SNAPSHOT-2015-09-30_19-42-59'         | 'epo-install-packages'         | '0.6.0'   | true     | '2015-09-30_19-42-59' | true
        'oem-portal-1.16.0-SNAPSHOT-2015-07-06_21-24-23'                  | 'oem-portal'                   | '1.16.0'  | true     | '2015-07-06_21-24-23' | true
        'call-monitor-service-0.2.0-SNAPSHOT-2015-09-02_04-15-08'         | 'call-monitor-service'         | '0.2.0'   | true     | '2015-09-02_04-15-08' | true
        'entity-gateway-3.3.0-SNAPSHOT-2015-10-05_21-58-51'               | 'entity-gateway'               | '3.3.0'   | true     | '2015-10-05_21-58-51' | true
        'trojan-horse-0.1.0-SNAPSHOT-2015-11-16_14-54-39'                 | 'trojan-horse'                 | '0.1.0'   | true     | '2015-11-16_14-54-39' | true
        'pfm-entity-bridge-0.1.0-SNAPSHOT-2015-08-07_17-45-22'            | 'pfm-entity-bridge'            | '0.1.0'   | true     | '2015-08-07_17-45-22' | true
        'oem-portal-0.22.0-SNAPSHOT-2015-06-04_17-58-33'                  | 'oem-portal'                   | '0.22.0'  | true     | '2015-06-04_17-58-33' | true
        'search-service-8.1.0-SNAPSHOT-2015-09-04_13-06-12'               | 'search-service'               | '8.1.0'   | true     | '2015-09-04_13-06-12' | true
        'data-import-service-2.5.0-SNAPSHOT-2015-10-29_20-58-05'          | 'data-import-service'          | '2.5.0'   | true     | '2015-10-29_20-58-05' | true
        'admin-portal-0.13.0-SNAPSHOT-2015-06-11_22-09-12'                | 'admin-portal'                 | '0.13.0'  | true     | '2015-06-11_22-09-12' | true
        'edriver-log-importer-service-0.1.0-SNAPSHOT-2015-10-15_16-10-10' | 'edriver-log-importer-service' | '0.1.0'   | true     | '2015-10-15_16-10-10' | true
        'comms-vehicle-service-0.8.0-SNAPSHOT-2015-08-26_20-23-27'        | 'comms-vehicle-service'        | '0.8.0'   | true     | '2015-08-26_20-23-27' | true
        'entity-service-2.30.0-SNAPSHOT-2015-08-05_17-14-35'              | 'entity-service'               | '2.30.0'  | true     | '2015-08-05_17-14-35' | true
        'admin-portal-0.25.0-SNAPSHOT-2015-07-17_18-25-03'                | 'admin-portal'                 | '0.25.0'  | true     | '2015-07-17_18-25-03' | true
        'oem-portal-1.31.0-SNAPSHOT-2015-09-24_19-22-26'                  | 'oem-portal'                   | '1.31.0'  | true     | '2015-09-24_19-22-26' | true
        'oem-portal-1.18.0-SNAPSHOT-2015-07-13_19-48-55'                  | 'oem-portal'                   | '1.18.0'  | true     | '2015-07-13_19-48-55' | true
        'entity-service-2.29.0-SNAPSHOT-2015-07-30_17-54-16'              | 'entity-service'               | '2.29.0'  | true     | '2015-07-30_17-54-16' | true
        'admin-portal-0.25.0-2015-07-17_18-25-03'                         | 'admin-portal'                 | '0.25.0'  | false    | '2015-07-17_18-25-03' | true
        'oem-portal-1.31.2'                                               | 'oem-portal'                   | '1.31.2'  | false    | null                  | true
        'oem-portal-1.18.0-2015-07-13_19-48-55'                           | 'oem-portal'                   | '1.18.0'  | false    | '2015-07-13_19-48-55' | true
        'entity-service-LATEST-2015-11-04_19-51-28'                       | 'entity-service'               | null      | true     | '2015-11-04_19-51-28' | true

    }

    void 'will sort AMIs correctly'() {
        given: 'a list of AMI in order'
        final Collection<String> ORDERED = [
            'entity-service-3.5.0-SNAPSHOT-2015-10-19_20-22-13',
            'entity-service-LATEST-2015-11-04_19-51-28',
            'entity-service-LATEST-2015-11-20_20-11-14',
            'com.peoplenet.oem-portal-0.2.4-SNAPSHOT',
            'com.peoplenet.oem-portal-0.8.4-SNAPSHOT',
            'oem-portal-0.8.5-SNAPSHOT',
            'com.peoplenet.oem-portal-0.8.17-SNAPSHOT',
            'com.peoplenet.oem-portal-0.9.4-SNAPSHOT',
            'com.peoplenet.oem-portal-0.10.4-SNAPSHOT',
            'com.peoplenet.oem-portal-0.10.9-SNAPSHOT',
            'oem-portal-0.10.10-SNAPSHOT-2015-04-23_20-11-27',
            'oem-portal-0.10.10-SNAPSHOT-2015-04-23_22-12-49',
            'oem-portal-0.10.10-SNAPSHOT-2015-04-23_22-21-10',
            'oem-portal-0.10.11-SNAPSHOT-2015-04-23_22-45-15',
            'oem-portal-0.10.11-SNAPSHOT-2015-04-23_23-25-00',
            'oem-portal-0.10.14-SNAPSHOT-2015-04-29_16-12-52',
            'oem-portal-0.11.1-SNAPSHOT-2015-05-01_18-58-45',
            'oem-portal-0.25.0-SNAPSHOT-2015-06-05_22-33-37',
            'oem-portal-0.25.0-SNAPSHOT-2015-06-08_14-50-29',
            'oem-portal-1.4.0-SNAPSHOT-2015-06-25_19-43-40',
            'oem-portal-1.4.0-SNAPSHOT-2015-06-25_20-13-13',
            'oem-portal-1.8.0-SNAPSHOT-2015-06-29_20-05-21',
            'oem-portal-1.19.0-SNAPSHOT-2015-07-14_18-29-00',
            'oem-portal-1.19.0-SNAPSHOT-2015-07-14_22-40-22',
            'oem-portal-1.20.5-SNAPSHOT',
            'oem-portal-1.21.0-SNAPSHOT-2015-07-16_07-26-42',
            'oem-portal-1.21.0-SNAPSHOT-2015-07-16_13-25-39',
            'oem-portal-1.22.0-SNAPSHOT-2015-07-16_14-39-44',
            'oem-portal-1.40.0-SNAPSHOT-2015-10-30_19-56-36',
            'oem-portal-1.44.0-SNAPSHOT-2015-11-17_23-10-50',
            'oem-portal-1.45.0-SNAPSHOT-2015-12-01_17-58-33',
            'oem-portal-2.3.4',
            'com.peoplenet.oem-portal-gateway-0.0.1-SNAPSHOT',
            'search-migration-10.3.0-SNAPSHOT-2015-11-11_15-56-52'
        ].asImmutable()

        and: 'we can print output if we need it'
        boolean printOutput = true

        and: 'and an empty list to randomize it in'
        Collection<String> randomized = ORDERED.collect()

        and: 'we randomize it'
        long seed = System.nanoTime() + n
        Collections.shuffle(randomized, new Random(seed))

        and: 'we create an empty treeset'
        TreeSet<AmiVersionInfo> sorted = [] as TreeSet

        when:
        randomized.each{ sorted << new AmiVersionInfo(it) }

        and: 'we print the output if we need it'
        if (printOutput) {
            println 'n'.padRight(5) + 'sorted'.padRight(60) + '=? ORDERED'
            println '-'.padRight(5) + '------'.padRight(60) + '-- -------'
            sorted.eachWithIndex{ AmiVersionInfo ami, Integer i ->
                String eq = '=='
                if (ami != ORDERED[i]) { eq = '!=' }
                println i.toString().padRight(5) + ami.name.padRight(60) + eq + ' ' + ORDERED[i]
            }
            println ''
        }

        then:
        sorted.eachWithIndex{ AmiVersionInfo ami, Integer i ->
            assert ami.name == ORDERED[i]
        }

        where: 'we run this 5 times for good measure'
        n << (1..5)
    }

}
