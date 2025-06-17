package com.emrekentli.openremotecrud.service;

import com.emrekentli.openremotecrud.dto.*;
import com.emrekentli.openremotecrud.feign.OpenRemoteAssetClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import static com.emrekentli.openremotecrud.util.MapUtils.putIfNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OpenRemoteDeviceServiceImpl implements  OpenRemoteDeviceService {

    private final OpenRemoteAssetClient assetClient;

    @Override
    public DeviceResponse createDevice(DeviceCreateRequest request) {
        return assetClient.createDevice(request);
    }
    @Override
    public DeviceResponse getDevice(String deviceId) {
        return assetClient.getDevice(deviceId);
    }
    @Override
    public DeviceResponse updateDevice(String deviceId, DeviceUpdateRequest request) {
        return assetClient.updateDevice(deviceId, request);
    }
    @Override
    public void deleteDevice(String deviceId) {
        assetClient.deleteDevices(List.of(deviceId));
    }

    @Override
    public List<DeviceResponse> listDevices(DeviceQueryRequest query) {
        return assetClient.queryAssets(query);
    }

}
