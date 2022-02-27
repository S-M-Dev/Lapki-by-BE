package com.smdev.lapkibe.model.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class PetRequestDTO {
    private String name;
    private int age;
    private String sex;
    private Map<String, String> properties = new HashMap<>();
}
