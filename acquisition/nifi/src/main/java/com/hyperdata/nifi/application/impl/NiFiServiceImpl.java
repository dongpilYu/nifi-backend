package com.hyperdata.nifi.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hyperdata.nifi.application.dto.NiFiConfigRequestDto;
import com.hyperdata.nifi.application.dto.NiFiConfigResponseDto;
import com.hyperdata.nifi.domain.NiFi;
import com.hyperdata.nifi.domain.repository.NiFiRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NiFiServiceImpl {

    private final NiFiRepository nifiRepository;
    private final WebClient client;

    @Autowired
    public NiFiServiceImpl(NiFiRepository nifiRepository, WebClient.Builder webClientBuilder) {
        this.nifiRepository = nifiRepository;
        this.client = WebClient.builder().baseUrl("http://192.1.1.92:31147/nifi-api").build();
    }

    public NiFiConfigResponseDto store(NiFiConfigRequestDto niFiConfigRequestDto) {

        String result = client.get().uri("/process-groups/root/").retrieve().bodyToMono(String.class).block();
        JsonElement element = JsonParser.parseString(result);
        JsonObject object = element.getAsJsonObject();
        String rootProcessGroupId = object.get("id").getAsString();

        NiFi nifi = NiFi.of(niFiConfigRequestDto.getNifiIp(), niFiConfigRequestDto.getNifiPort(),
            rootProcessGroupId);

        nifiRepository.save(nifi);

        return NiFiConfigResponseDto.from(nifi);
    }
}
