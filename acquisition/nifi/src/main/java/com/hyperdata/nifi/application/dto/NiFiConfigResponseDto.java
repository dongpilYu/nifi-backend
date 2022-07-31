package com.hyperdata.nifi.application.dto;

import com.hyperdata.nifi.domain.NiFi;

public class NiFiConfigResponseDto {
    private String nifiIp;
    private String nifiPort;
    private String rootProcessGroupId;

    public String getNifiIp() {
        return nifiIp;
    }

    public void setNifiIp(String nifiIp) {
        this.nifiIp = nifiIp;
    }

    public NiFiConfigResponseDto(String nifiIp, String nifiPort, String rootProcessGroupId) {
        this.nifiIp = nifiIp;
        this.nifiPort = nifiPort;
        this.rootProcessGroupId = rootProcessGroupId;
    }

    public String getNifiPort() {
        return nifiPort;
    }

    public void setNifiPort(String nifiPort) {
        this.nifiPort = nifiPort;
    }

    public String getRootProcessGroupId() {
        return rootProcessGroupId;
    }

    public void setRootProcessGroupId(String rootProcessGroupId) {
        this.rootProcessGroupId = rootProcessGroupId;
    }

    public static NiFiConfigResponseDto from(NiFi nifi) {
        return new NiFiConfigResponseDto(nifi.getNifiIp(), nifi.getNifiPort(), nifi.getRootProcessGroupId());
    }
}
