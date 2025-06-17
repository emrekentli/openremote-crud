package com.emrekentli.openremotecrud.controller;

import com.emrekentli.openremotecrud.dto.*;
import com.emrekentli.openremotecrud.rest.Response;
import com.emrekentli.openremotecrud.rest.ResponseBuilder;
import com.emrekentli.openremotecrud.service.OpenRemoteDeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Device API", description = "Cihaz CRUD işlemleri")
@RestController
@RequestMapping("/api/device")
@RequiredArgsConstructor
public class DeviceController {

    private final OpenRemoteDeviceService deviceService;

    @Operation(summary = "Yeni cihaz ekle")
    @PostMapping
    public Response<DeviceResponse> createDevice(@Valid @RequestBody DeviceCreateRequest request) {
        DeviceResponse created = deviceService.createDevice(request);
        return ResponseBuilder.build(created);
    }

    @Operation(summary = "Tüm cihazları (isteğe göre filtrelenmiş) listele")
    @GetMapping
    public Response<List<DeviceResponse>> listDevices(@RequestBody DeviceQueryRequest query) {
        return ResponseBuilder.build(deviceService.listDevices(query));
    }

    @Operation(summary = "Cihaz bilgisi getir")
    @GetMapping("/{id}")
    public Response<DeviceResponse> getDevice(@PathVariable String id) {
        DeviceResponse device = deviceService.getDevice(id);
        return ResponseBuilder.build(device);
    }

    @Operation(summary = "Cihazı güncelle")
    @PutMapping("/{id}")
    public Response<DeviceResponse> updateDevice(
            @PathVariable String id,
            @Valid @RequestBody DeviceUpdateRequest request) {
        DeviceResponse updated = deviceService.updateDevice(id, request);
        return ResponseBuilder.build(updated);
    }

    @Operation(summary = "Cihazı sil")
    @DeleteMapping("/{id}")
    public Response<Void> deleteDevice(@PathVariable String id) {
        deviceService.deleteDevice(id);
        return ResponseBuilder.buildSuccessResponse();
    }
}
