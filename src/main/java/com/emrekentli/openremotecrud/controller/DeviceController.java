package com.emrekentli.openremotecrud.controller;

import com.emrekentli.openremotecrud.dto.DeviceCreateRequest;
import com.emrekentli.openremotecrud.dto.DeviceQueryRequest;
import com.emrekentli.openremotecrud.dto.DeviceResponse;
import com.emrekentli.openremotecrud.dto.DeviceUpdateRequest;
import com.emrekentli.openremotecrud.rest.Response;
import com.emrekentli.openremotecrud.rest.ResponseBuilder;
import com.emrekentli.openremotecrud.service.OpenRemoteDeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Device API Controller
 * Provides endpoints for device CRUD operations.
 */
@Tag(name = "Device API", description = "Device CRUD operations")
@RestController
@RequestMapping("/api/device")
@RequiredArgsConstructor
public class DeviceController {

    private final OpenRemoteDeviceService deviceService;

    @Operation(summary = "Add a new device")
    @PostMapping
    public Response<DeviceResponse> createDevice(@Valid @RequestBody DeviceCreateRequest request) {
        DeviceResponse created = deviceService.createDevice(request);
        return ResponseBuilder.build(created);
    }

    @Operation(summary = "List devices using advanced query")
    @PostMapping("/query")
    public Response<List<DeviceResponse>> listDevices(@RequestBody DeviceQueryRequest query) {
        List<DeviceResponse> result = deviceService.listDevices(query);
        return ResponseBuilder.build(result);
    }

    @Operation(summary = "Get device details by ID")
    @GetMapping("/{id}")
    public Response<DeviceResponse> getDevice(@PathVariable String id) {
        DeviceResponse device = deviceService.getDevice(id);
        return ResponseBuilder.build(device);
    }

    @Operation(summary = "Update a device")
    @PutMapping("/{id}")
    public Response<DeviceResponse> updateDevice(
            @PathVariable String id,
            @Valid @RequestBody DeviceUpdateRequest request) {
        DeviceResponse updated = deviceService.updateDevice(id, request);
        return ResponseBuilder.build(updated);
    }

    @Operation(summary = "Delete a device by ID")
    @DeleteMapping("/{id}")
    public Response<Void> deleteDevice(@PathVariable String id) {
        deviceService.deleteDevice(id);
        return ResponseBuilder.buildSuccessResponse();
    }
}
