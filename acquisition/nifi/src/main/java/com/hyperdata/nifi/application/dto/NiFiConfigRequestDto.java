package com.hyperdata.nifi.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NiFiConfigRequestDto {

    private String nifiIp;
    private String nifiPort;
    private String rootProcessGroupId;
}
