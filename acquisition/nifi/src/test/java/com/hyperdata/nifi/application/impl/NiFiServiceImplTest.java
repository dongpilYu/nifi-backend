package com.hyperdata.nifi.application.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class NiFiServiceImplTest {

    @Autowired
    private WebClient client;

    @BeforeEach
    public void setUp() {
        this.client = WebClient.builder().baseUrl("http://192.1.1.92:31147/nifi-api").build();
    }

    @Test
    public void store() {

        String result = client.get().uri("/process-groups/root/").retrieve().bodyToMono(String.class).block();
        JsonElement element = JsonParser.parseString(result);
        JsonObject object = element.getAsJsonObject();
        String id = object.get("id").getAsString();

        System.out.println(id);
    }

}
