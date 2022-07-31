package com.hyperdata.nifi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.LoggingCodecSupport;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.hyperdata.nifi.util.ThrowingConsumer;

import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Configuration
@Slf4j
@Profile("local")
public class WebClientConfiguration {

    @Value("${nifi.base-url")
    private static String BASE_URL;
    private static final Logger logger = LoggerFactory.getLogger(WebClientConfiguration.class);

    WebClientConfiguration(@Value("${nifi.base-url}") String BASE_URL) {
        this.BASE_URL = BASE_URL;
    }

    @Bean
    public WebClient webClient() {

        logger.info("BASE_URL : " + BASE_URL);

        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
            .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024 * 10))
            .build();
        exchangeStrategies
            .messageWriters().stream()
            .filter(LoggingCodecSupport.class::isInstance)
            .forEach(writer -> ((LoggingCodecSupport)writer).setEnableLoggingRequestDetails(true));

        return WebClient.builder()
            .clientConnector(
                new ReactorClientHttpConnector(
                    HttpClient
                        .create()
                        .secure(
                            ThrowingConsumer.unchecked(
                                sslContextSpec -> sslContextSpec.sslContext(
                                    SslContextBuilder.forClient()
                                        .trustManager(InsecureTrustManagerFactory.INSTANCE)
                                        .build()
                                )
                            )
                        )
                )
            )
            .exchangeStrategies(exchangeStrategies)
            .filter(ExchangeFilterFunction.ofRequestProcessor(
                clientRequest -> {
                    log.debug("Request: {} {}", clientRequest.method(), clientRequest.url());
                    clientRequest.headers()
                        .forEach((name, values) -> values.forEach(value -> log.debug("{} : {}", name, value)));
                    return Mono.just(clientRequest);
                }
            ))
            .filter(ExchangeFilterFunction.ofResponseProcessor(
                clientResponse -> {
                    clientResponse.headers()
                        .asHttpHeaders()
                        .forEach((name, values) -> values.forEach(value -> log.debug("{} : {}", name, value)));
                    return Mono.just(clientResponse);
                }
            ))
            .build();
    }
}
