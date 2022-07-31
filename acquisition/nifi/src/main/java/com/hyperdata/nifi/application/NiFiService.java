package com.hyperdata.nifi.application;

import com.hyperdata.nifi.application.dto.NiFiConfigRequestDto;
import com.hyperdata.nifi.application.dto.NiFiConfigResponseDto;

public interface NiFiService {
    NiFiConfigResponseDto store(NiFiConfigRequestDto niFiConfigRequestDto);
}
