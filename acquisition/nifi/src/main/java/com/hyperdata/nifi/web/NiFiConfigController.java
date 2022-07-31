package com.hyperdata.nifi.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hyperdata.nifi.application.dto.NiFiConfigRequestDto;
import com.hyperdata.nifi.application.dto.NiFiConfigResponseDto;
import com.hyperdata.nifi.application.impl.NiFiServiceImpl;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1/nifi-config")
public class NiFiConfigController {
    private final NiFiServiceImpl niFiService;

    @Autowired
    public NiFiConfigController(NiFiServiceImpl niFiService) {
        this.niFiService = niFiService;
    }

    @PostMapping("/upload")
    @ResponseStatus(value = HttpStatus.OK)
    public NiFiConfigResponseDto upload(NiFiConfigRequestDto nifiConfigRequestDto) {

        return niFiService.store(nifiConfigRequestDto);
    }

}
