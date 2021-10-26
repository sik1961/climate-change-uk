package com.sik.climatechangeuk.configuration;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.sik.climatechangeuk.status.AdapterStatus;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collections;

@Slf4j
@Configuration
public class CommonConfiguration {

    @Bean
    public AdapterStatus adapterStatus() {
        return new AdapterStatus();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RestTemplate commonRestTemplate(
            RestTemplateBuilder restTemplateBuilder,
            ClientHttpRequestInterceptor contentTypeInterceptor,
            @Value("${restTemplate.connectTimeoutMillis:30000}") int connectTimeout,
            @Value("${restTemplate.readTimeoutMillis:30000}") int readTimeout
    ) {
        return restTemplateBuilder.additionalInterceptors(contentTypeInterceptor)
                .setConnectTimeout(Duration.ofMillis(connectTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .build();
    }

    @Bean
    public ClientHttpRequestInterceptor contentTypeInterceptor() {
        return (request, body, execution) -> {
            request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return execution.execute(request, body);
        };
    }



    @Bean
    public RestTemplate postTagRestTemplate(
            RestTemplateBuilder restTemplateBuilder,
            ClientHttpRequestInterceptor contentTypeInterceptor,
            @Value("${restTemplate.connectTimeoutMillis:30000}") int connectTimeout,
            @Value("${restTemplate.readTimeoutMillis:30000}") int readTimeout
    ) {
        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        converter.setDefaultCharset(StandardCharsets.UTF_8);

        return restTemplateBuilder
                .additionalInterceptors(contentTypeInterceptor)
                .messageConverters(converter)
                .setConnectTimeout(Duration.ofMillis(connectTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .build();
    }

    @Bean
    public RestTemplate postTagOverridesRestTemplate(
            RestTemplateBuilder restTemplateBuilder,
            ClientHttpRequestInterceptor contentTypeInterceptor,
            @Value("${restTemplate.connectTimeoutMillis:30000}") int connectTimeout,
            @Value("${restTemplate.readTimeoutMillis:30000}") int readTimeout
    ) {
        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        converter.setDefaultCharset(StandardCharsets.UTF_8);

        return restTemplateBuilder
                .additionalInterceptors(contentTypeInterceptor)
                .messageConverters(converter)
                .setConnectTimeout(Duration.ofMillis(connectTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .build();
    }


}
