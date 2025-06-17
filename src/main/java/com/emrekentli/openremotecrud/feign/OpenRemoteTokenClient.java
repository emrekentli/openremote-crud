package com.emrekentli.openremotecrud.feign;

import com.emrekentli.openremotecrud.dto.TokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "openremote-token", url = "${openremote.url}")
public interface OpenRemoteTokenClient {
    @PostMapping(value = "/auth/realms/master/protocol/openid-connect/token",
            consumes = "application/x-www-form-urlencoded")
    TokenResponse getToken(@RequestHeader("Content-Type") String contentType,
                           @RequestBody MultiValueMap<String, String> form);
}
