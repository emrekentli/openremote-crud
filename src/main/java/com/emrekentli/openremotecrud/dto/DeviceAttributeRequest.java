package com.emrekentli.openremotecrud.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Bir cihaz attribute'u")
public class DeviceAttributeRequest {
    @NotBlank
    @Schema(example = "number")
    private String type;

    @Schema(example = "22.5")
    private Object value;

    @NotBlank
    @Schema(example = "temperature")
    private String name;
}