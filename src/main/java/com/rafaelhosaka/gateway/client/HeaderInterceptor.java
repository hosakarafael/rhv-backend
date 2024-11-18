package com.rafaelhosaka.gateway.client;

import com.rafaelhosaka.gateway.utils.HmacUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HeaderInterceptor implements RequestInterceptor {

    @Value("${HEADER_VALUE}")
    private String value;

    @Value("${HEADER_SECRET}")
    private String secret;

    @Override
    public void apply(RequestTemplate template) {
        try {
            template.header("RHV-Header", HmacUtils.generateHmacSignature(value, secret));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}