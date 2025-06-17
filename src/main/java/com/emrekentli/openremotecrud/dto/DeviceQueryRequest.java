package com.emrekentli.openremotecrud.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DeviceQueryRequest {
    private Map<String, Boolean> select;
    private Object realm;
    private List<String> types;
    private List<Map<String, Object>> names;
    private Map<String, Object> attributes;
    private Integer limit;
    private Map<String, String> orderBy;
}
