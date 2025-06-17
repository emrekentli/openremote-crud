package com.emrekentli.openremotecrud.controller;

import com.emrekentli.openremotecrud.dto.*;
import com.emrekentli.openremotecrud.service.OpenRemoteDeviceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DeviceController.class)
class DeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OpenRemoteDeviceService deviceService;

    @Autowired
    private ObjectMapper objectMapper;

    private Map<String, DeviceAttributeRequest> validAttributes() {
        Map<String, DeviceAttributeRequest> attributes = new HashMap<>();
        DeviceAttributeRequest attr = new DeviceAttributeRequest();
        attr.setType("number");
        attr.setValue(22.5);
        attr.setName("temperature");
        attributes.put("temperature", attr);
        return attributes;
    }

    @Nested
    @DisplayName("POST /api/device (createDevice)")
    class CreateDevice {

        @Test
        @DisplayName("should create device successfully with valid input")
        void createDevice_success() throws Exception {
            DeviceCreateRequest request = new DeviceCreateRequest();
            request.setType("asset");
            request.setName("Test Device");
            request.setRealm("master");
            request.setAttributes(validAttributes());

            DeviceResponse response = new DeviceResponse();
            response.setId("someid");

            when(deviceService.createDevice(any(DeviceCreateRequest.class))).thenReturn(response);

            mockMvc.perform(post("/api/device")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.id").value("someid"));

            verify(deviceService).createDevice(any(DeviceCreateRequest.class));
        }

        @Test
        @DisplayName("should fail with 400 when attributes are empty")
        void createDevice_emptyAttributes() throws Exception {
            DeviceCreateRequest request = new DeviceCreateRequest();
            request.setType("asset");
            request.setName("Test Device");
            request.setRealm("master");
            request.setAttributes(Collections.emptyMap());

            mockMvc.perform(post("/api/device")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.meta.code").value("400"));
        }

        @Test
        @DisplayName("should fail with 400 when name is missing")
        void createDevice_missingName() throws Exception {
            DeviceCreateRequest request = new DeviceCreateRequest();
            request.setType("asset");
            request.setRealm("master");
            request.setAttributes(validAttributes());

            mockMvc.perform(post("/api/device")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.meta.code").value("400"));
        }
    }

    @Nested
    @DisplayName("POST /api/device/query (listDevices)")
    class ListDevices {

        @Test
        @DisplayName("should return devices with valid query")
        void listDevices_success() throws Exception {
            DeviceQueryRequest query = new DeviceQueryRequest();
            DeviceResponse device = new DeviceResponse();
            device.setId("id1");

            when(deviceService.listDevices(any(DeviceQueryRequest.class))).thenReturn(List.of(device));

            mockMvc.perform(post("/api/device/query")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(query)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data[0].id").value("id1"));

            verify(deviceService).listDevices(any(DeviceQueryRequest.class));
        }
    }

    @Nested
    @DisplayName("GET /api/device/{id} (getDevice)")
    class GetDevice {

        @Test
        @DisplayName("should get device successfully")
        void getDevice_success() throws Exception {
            DeviceResponse response = new DeviceResponse();
            response.setId("cid");

            when(deviceService.getDevice("cid")).thenReturn(response);

            mockMvc.perform(get("/api/device/cid"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.id").value("cid"));

            verify(deviceService).getDevice("cid");
        }

        @Test
        @DisplayName("should return 404 if device is not found")
        void getDevice_notFound() throws Exception {
            when(deviceService.getDevice("notfound")).thenReturn(null);

            mockMvc.perform(get("/api/device/notfound"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data").doesNotExist())
                    .andExpect(jsonPath("$.meta.code").exists());
        }
    }

    @Nested
    @DisplayName("PUT /api/device/{id} (updateDevice)")
    class UpdateDevice {

        @Test
        @DisplayName("should update device successfully with valid input")
        void updateDevice_success() throws Exception {
            DeviceUpdateRequest update = new DeviceUpdateRequest();
            update.setType("asset");
            update.setName("Updated Device");
            update.setRealm("master");
            update.setAttributes(validAttributes());

            DeviceResponse response = new DeviceResponse();
            response.setId("uid");

            when(deviceService.updateDevice(eq("uid"), any(DeviceUpdateRequest.class))).thenReturn(response);

            mockMvc.perform(put("/api/device/uid")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(update)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.id").value("uid"));

            verify(deviceService).updateDevice(eq("uid"), any(DeviceUpdateRequest.class));
        }

        @Test
        @DisplayName("should fail with 400 when attributes are empty")
        void updateDevice_emptyAttributes() throws Exception {
            DeviceUpdateRequest update = new DeviceUpdateRequest();
            update.setType("asset");
            update.setName("Updated Device");
            update.setRealm("master");
            update.setAttributes(Collections.emptyMap());

            mockMvc.perform(put("/api/device/uid")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(update)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.meta.code").value("400"));
        }
    }

    @Nested
    @DisplayName("DELETE /api/device/{id} (deleteDevice)")
    class DeleteDevice {

        @Test
        @DisplayName("should delete device successfully")
        void deleteDevice_success() throws Exception {
            mockMvc.perform(delete("/api/device/xid"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.meta.code").value("200"));

            verify(deviceService).deleteDevice("xid");
        }
    }
}
