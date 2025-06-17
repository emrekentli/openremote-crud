package com.emrekentli.openremotecrud.config;

import com.emrekentli.openremotecrud.service.OpenRemoteTokenService;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class FeignConfig {

    @Bean
    public RequestInterceptor bearerAuthRequestInterceptor(OpenRemoteTokenService tokenService) {
        return requestTemplate -> {
            if (requestTemplate.url().contains("/api/master/asset")) {
                String token = tokenService.getAccessToken();
                log.debug("Adding Authorization header with token: {}", token);
                requestTemplate.header("Authorization", "Bearer " + token);
            }
        };
    }
}
