package com.emrekentli.openremotecrud.service;

import com.emrekentli.openremotecrud.dto.DeviceCreateRequest;
import com.emrekentli.openremotecrud.dto.DeviceQueryRequest;
import com.emrekentli.openremotecrud.dto.DeviceResponse;
import com.emrekentli.openremotecrud.dto.DeviceUpdateRequest;

import java.util.List;

public interface OpenRemoteDeviceService {
    DeviceResponse createDevice(DeviceCreateRequest request);

    DeviceResponse getDevice(String deviceId);

    DeviceResponse updateDevice(String deviceId, DeviceUpdateRequest request);

    void deleteDevice(String deviceId);

    List<DeviceResponse> listDevices(DeviceQueryRequest query);
}
