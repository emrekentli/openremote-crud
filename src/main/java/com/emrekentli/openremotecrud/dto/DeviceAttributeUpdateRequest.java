package com.emrekentli.openremotecrud.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Map;


@Data
@Schema(description = "Cihaz attribute güncelleme isteği")
public class DeviceAttributeUpdateRequest {
    @NotEmpty
    @Schema(description = "Güncellenecek attribute'lar")
    private Map<String, DeviceAttributeRequest> attributes;
}
