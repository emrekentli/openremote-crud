package com.emrekentli.openremotecrud.feign;

import com.emrekentli.openremotecrud.config.FeignConfig;
import com.emrekentli.openremotecrud.dto.DeviceCreateRequest;
import com.emrekentli.openremotecrud.dto.DeviceQueryRequest;
import com.emrekentli.openremotecrud.dto.DeviceResponse;
import com.emrekentli.openremotecrud.dto.DeviceUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "openremoteAssetClient",
        url = "${openremote.url}",
        configuration = FeignConfig.class
)
public interface OpenRemoteAssetClient {

    @PostMapping("/api/master/asset")
    DeviceResponse createDevice(@RequestBody DeviceCreateRequest payload);

    @GetMapping("/api/master/asset/{id}")
    DeviceResponse getDevice(@PathVariable("id") String id);

    @PutMapping("/api/master/asset/{id}")
    DeviceResponse updateDevice(@PathVariable("id") String id, @RequestBody DeviceUpdateRequest payload);

    @DeleteMapping("/api/master/asset")
    void deleteDevices(@RequestParam("assetId") List<String> assetIds);


    @PostMapping("/api/master/asset/query")
    List<DeviceResponse> queryAssets(@RequestBody DeviceQueryRequest query);}
