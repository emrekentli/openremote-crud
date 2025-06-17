package com.emrekentli.openremotecrud.service;

import com.emrekentli.openremotecrud.dto.DeviceCreateRequest;
import com.emrekentli.openremotecrud.dto.DeviceQueryRequest;
import com.emrekentli.openremotecrud.dto.DeviceResponse;
import com.emrekentli.openremotecrud.dto.DeviceUpdateRequest;
import com.emrekentli.openremotecrud.exception.OpenRemoteAuthException;
import com.emrekentli.openremotecrud.feign.OpenRemoteAssetClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OpenRemoteDeviceServiceImplTest {

    @Mock
    private OpenRemoteAssetClient assetClient;

    @InjectMocks
    private OpenRemoteDeviceServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("createDevice - should return DeviceResponse")
    void createDevice_success() {
        DeviceCreateRequest request = new DeviceCreateRequest();
        DeviceResponse expected = new DeviceResponse();
        expected.setId("device123");
        when(assetClient.createDevice(request)).thenReturn(expected);

        DeviceResponse actual = service.createDevice(request);

        assertEquals(expected, actual);
        verify(assetClient).createDevice(request);
    }

    @Test
    @DisplayName("getDevice - should return DeviceResponse")
    void getDevice_success() {
        String id = "deviceABC";
        DeviceResponse expected = new DeviceResponse();
        expected.setId(id);

        when(assetClient.getDevice(id)).thenReturn(expected);

        DeviceResponse actual = service.getDevice(id);

        assertEquals(expected, actual);
        verify(assetClient).getDevice(id);
    }

    @Test
    @DisplayName("updateDevice - should return DeviceResponse")
    void updateDevice_success() {
        String id = "deviceXYZ";
        DeviceUpdateRequest updateRequest = new DeviceUpdateRequest();
        DeviceResponse expected = new DeviceResponse();
        expected.setId(id);

        when(assetClient.updateDevice(id, updateRequest)).thenReturn(expected);

        DeviceResponse actual = service.updateDevice(id, updateRequest);

        assertEquals(expected, actual);
        verify(assetClient).updateDevice(id, updateRequest);
    }

    @Test
    @DisplayName("deleteDevice - should call assetClient.deleteDevices")
    void deleteDevice_success() {
        String id = "deleteMe";
        doNothing().when(assetClient).deleteDevices(List.of(id));

        service.deleteDevice(id);

        verify(assetClient).deleteDevices(List.of(id));
    }

    @Test
    @DisplayName("listDevices - should return list of DeviceResponse")
    void listDevices_success() {
        DeviceQueryRequest query = new DeviceQueryRequest();
        List<DeviceResponse> expected = List.of(new DeviceResponse());
        when(assetClient.queryAssets(query)).thenReturn(expected);

        List<DeviceResponse> actual = service.listDevices(query);

        assertEquals(expected, actual);
        verify(assetClient).queryAssets(query);
    }

    // --- Failure cases (Exception handling) ---

    @Test
    @DisplayName("createDevice - should throw RuntimeException if assetClient fails")
    void createDevice_clientThrows() {
        DeviceCreateRequest request = new DeviceCreateRequest();
        when(assetClient.createDevice(request)).thenThrow(new RuntimeException("Client error"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.createDevice(request));
        assertEquals("Client error", ex.getMessage());
        verify(assetClient).createDevice(request);
    }

    @Test
    @DisplayName("getDevice - should throw RuntimeException if assetClient fails")
    void getDevice_clientThrows() {
        when(assetClient.getDevice("xid")).thenThrow(new RuntimeException("Not found"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.getDevice("xid"));
        assertEquals("Not found", ex.getMessage());
        verify(assetClient).getDevice("xid");
    }

    @Test
    @DisplayName("updateDevice - should throw RuntimeException if assetClient fails")
    void updateDevice_clientThrows() {
        DeviceUpdateRequest req = new DeviceUpdateRequest();
        when(assetClient.updateDevice("yid", req)).thenThrow(new RuntimeException("Update failed"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.updateDevice("yid", req));
        assertEquals("Update failed", ex.getMessage());
        verify(assetClient).updateDevice("yid", req);
    }

    @Test
    @DisplayName("deleteDevice - should throw RuntimeException if assetClient fails")
    void deleteDevice_clientThrows() {
        doThrow(new RuntimeException("Delete failed")).when(assetClient).deleteDevices(List.of("failId"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.deleteDevice("failId"));
        assertEquals("Delete failed", ex.getMessage());
        verify(assetClient).deleteDevices(List.of("failId"));
    }

    @Test
    @DisplayName("listDevices - should throw RuntimeException if assetClient fails")
    void listDevices_clientThrows() {
        DeviceQueryRequest query = new DeviceQueryRequest();
        when(assetClient.queryAssets(query)).thenThrow(new RuntimeException("Query failed"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.listDevices(query));
        assertEquals("Query failed", ex.getMessage());
        verify(assetClient).queryAssets(query);
    }
}
