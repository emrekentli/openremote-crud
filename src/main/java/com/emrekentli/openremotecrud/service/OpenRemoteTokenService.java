package com.emrekentli.openremotecrud.service;

import com.emrekentli.openremotecrud.dto.TokenResponse;
import com.emrekentli.openremotecrud.exception.OpenRemoteAuthException;
import com.emrekentli.openremotecrud.feign.OpenRemoteTokenClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenRemoteTokenService {
    private final OpenRemoteTokenClient tokenClient;

    @Value("${openremote.client-id}")
    private String clientId;

    @Value("${openremote.client-secret}")
    private String clientSecret;

    private TokenResponse cachedToken;
    private Instant expiresAt;

    public String getAccessToken() {
        if (cachedToken != null && expiresAt != null && Instant.now().isBefore(expiresAt)) {
            log.info("Cached token : {}", cachedToken.getAccessToken());
            return cachedToken.getAccessToken();
        }
        try {
            MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
            form.add("client_id", clientId);
            form.add("client_secret", clientSecret);
            form.add("grant_type", "client_credentials");

            TokenResponse response = tokenClient.getToken("application/x-www-form-urlencoded", form);

            cachedToken = response;
            expiresAt = Instant.now().plusSeconds(response.getExpiresIn() - 20);

            return response.getAccessToken();
        } catch (Exception ex) {
            log.error("Error fetching OpenRemote token", ex);
            throw new OpenRemoteAuthException("Error fetching OpenRemote token: " + ex.getMessage(), ex);
        }
    }
}
