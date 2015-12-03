package org.zirbes.aws

class AmiVersionInfo implements Comparable<AmiVersionInfo> {

    static final String VERSION_FORMAT = /\d+\.\d+\.\d+/
    static final String VERSION_MATCH = /^\d+\.\d+\.\d+-.*/
    static final String TIMESTAMP_MATCH = /.*-\d{4}-\d{2}-\d{2}_\d{2}-\d{2}-\d{2}$/
    static final String TIMESTAMP_FORMAT = /\d{4}-\d{2}-\d{2}_\d{2}-\d{2}-\d{2}$/

    final String name

    AmiVersionInfo(String name) {
        assert name
        this.name = name
    }

    String getService() {
        name.split(/-\d/)[0]
            .replaceFirst(/com\.peoplenet\./, '')
            .replaceFirst(/-SNAPSHOT/, '')
            .replaceFirst(/-LATEST/, '')
    }

    String getVersion() {
        String tail = name.replaceFirst(name.split(/-\d/)[0] + '-', '')
        if (tail ==~ VERSION_FORMAT) { return tail }
        if (tail ==~ VERSION_MATCH) { return (tail =~ VERSION_FORMAT)[0] }
    }

    Collection<Integer> getSemanticVersion() {
        if (version) { version.split(/\./).collect{ Integer.parseInt(it) } }
    }

    Integer getMajorVersion() {
        if (version) { semanticVersion[0] }
    }

    Integer getMinorVersion() {
        if (version) { semanticVersion[1] }
    }

    Integer getPatchVersion() {
        if (version) { semanticVersion[2] }
    }

    boolean isSnapshot() {
        name ==~ /.*-SNAPSHOT.*/ || name ==~ /.*-LATEST.*/
    }

    boolean isLatest() {
        name ==~ /.*-LATEST.*/
    }

    String getTimestamp() {
        if (name ==~ TIMESTAMP_MATCH) { (name =~ TIMESTAMP_FORMAT)[0] }
    }

    boolean isValid() {
        service && ( version || snapshot )
    }

    @Override
    String toString() { name }

    @Override
    int compareTo(AmiVersionInfo other) {
          if (this.service != other.service      ) { return this.service      <=> other.service }

          // Latest is considered newer than snapshot
          if (this.latest != other.latest && this.snapshot && other.snapshot) { return this.latest <=> other.latest }

          if (this.majorVersion != other.majorVersion ) { return this.majorVersion <=> other.majorVersion }
          if (this.minorVersion != other.minorVersion ) { return this.minorVersion <=> other.minorVersion }
          if (this.patchVersion != other.patchVersion ) { return this.patchVersion <=> other.patchVersion }
          if (this.snapshot     != other.snapshot     ) { return this.snapshot     <=> other.snapshot }
          if (this.timestamp    != other.timestamp    ) { return this.timestamp    <=> other.timestamp }
          this.name <=> other.name
    }

}
