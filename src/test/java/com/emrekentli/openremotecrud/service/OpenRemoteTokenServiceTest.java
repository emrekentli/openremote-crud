package com.emrekentli.openremotecrud.service;

import com.emrekentli.openremotecrud.dto.TokenResponse;
import com.emrekentli.openremotecrud.exception.OpenRemoteAuthException;
import com.emrekentli.openremotecrud.feign.OpenRemoteTokenClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class OpenRemoteTokenServiceTest {

    @Mock
    private OpenRemoteTokenClient tokenClient;

    @InjectMocks
    private OpenRemoteTokenService tokenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        tokenService = new OpenRemoteTokenService(tokenClient);
        TestUtils.setField(tokenService, "clientId", "test-client");
        TestUtils.setField(tokenService, "clientSecret", "test-secret");
    }

    @Test
    @DisplayName("should get new token and cache it")
    void getAccessToken_success_and_cache() {
        // Setup token response
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken("the-token");
        tokenResponse.setExpiresIn(60); // 1 min expiry

        when(tokenClient.getToken(eq("application/x-www-form-urlencoded"), any(MultiValueMap.class)))
                .thenReturn(tokenResponse);

        String token1 = tokenService.getAccessToken();
        assertEquals("the-token", token1);
        verify(tokenClient, times(1)).getToken(any(), any());

        String token2 = tokenService.getAccessToken();
        assertEquals("the-token", token2);
        verify(tokenClient, times(1)).getToken(any(), any());
    }

    @Test
    @DisplayName("should fetch new token if cache is expired")
    void getAccessToken_expired_fetchesNewToken() {
        TokenResponse firstToken = new TokenResponse();
        firstToken.setAccessToken("expired-token");
        firstToken.setExpiresIn(1); // 1 second expiry

        TokenResponse secondToken = new TokenResponse();
        secondToken.setAccessToken("fresh-token");
        secondToken.setExpiresIn(60);

        when(tokenClient.getToken(eq("application/x-www-form-urlencoded"), any(MultiValueMap.class)))
                .thenReturn(firstToken)
                .thenReturn(secondToken);

        String token1 = tokenService.getAccessToken();
        assertEquals("expired-token", token1);

        TestUtils.setField(tokenService, "expiresAt", Instant.now().minusSeconds(2));

        String token2 = tokenService.getAccessToken();
        assertEquals("fresh-token", token2);
        verify(tokenClient, times(2)).getToken(any(), any());
    }

    @Test
    @DisplayName("should throw OpenRemoteAuthException if token fetch fails")
    void getAccessToken_failure_throwsException() {
        when(tokenClient.getToken(anyString(), any(MultiValueMap.class)))
                .thenThrow(new RuntimeException("connection error"));

        OpenRemoteAuthException ex = assertThrows(
                OpenRemoteAuthException.class,
                () -> tokenService.getAccessToken()
        );
        assertTrue(ex.getMessage().contains("Error fetching OpenRemote token"));
        verify(tokenClient, times(1)).getToken(any(), any());
    }
}

class TestUtils {
    public static void setField(Object target, String fieldName, Object value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
