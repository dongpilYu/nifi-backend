package com.hyperdata.nifi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "TB_NiFi")
public class NiFi extends BaseEntity {

    @Column(name = "nifi_ip", nullable = false)
    private String nifiIp;
    @Column(name = "nifi_port", nullable = false)
    private String nifiPort;
    @Column(name = "root_process_group_id", nullable = false)
    private String rootProcessGroupId;

    @Builder
    public NiFi(String id, String nifiIp, String nifiPort, String rootProcessGroupId) {
        super(id);
        this.nifiIp = nifiIp;
        this.nifiPort = nifiPort;
        this.rootProcessGroupId = rootProcessGroupId;
    }

    @Builder
    public NiFi(String nifiIp, String nifiPort, String rootProcessGroupId) {
        this.nifiIp = nifiIp;
        this.nifiPort = nifiPort;
        this.rootProcessGroupId = rootProcessGroupId;
    }

    public static NiFi of(String nifiIp, String nifiPort, String rootProcessGroupId) {

        return new NiFi(nifiIp, nifiPort, rootProcessGroupId);
    }

}
