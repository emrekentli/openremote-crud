package com.emrekentli.openremotecrud.dto;


import lombok.Data;
import java.util.Map;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@Data
@Schema(description = "Cihaz güncelleme isteği")
public class DeviceUpdateRequest {

    @NotBlank
    @Schema(example = "Ofis Sıcaklık Sensörü")
    private String name;

    private String type = "asset";

    @NotBlank
    @Schema(example = "master")
    private String realm;

    @NotEmpty
    @Schema(description = "Cihaz attribute'ları")
    private Map<String, DeviceAttributeRequest> attributes;

    @Schema(description = "Üst asset'in ID'si (opsiyonel)")
    private String parentId;

    @Schema(description = "Asset erişimi public mi (opsiyonel)")
    private Boolean accessPublicRead;

    @Schema(description = "Asset path'i (opsiyonel)")
    private String[] path;

    @Schema(description = "Delegate objesi (opsiyonel)")
    private Object delegate;

    @Schema(description = "Meta objesi (opsiyonel)")
    private Object meta;
}
