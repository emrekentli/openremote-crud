package com.emrekentli.openremotecrud.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceResponse {
    private String id;
    private Long version;
    private String createdOn;
    private String name;
    private Boolean accessPublicRead;
    private String parentId;
    private String realm;
    private String type;
    private List<String> path;
    private Map<String, Object> attributes;
}
