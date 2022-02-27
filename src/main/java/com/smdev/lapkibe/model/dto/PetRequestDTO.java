package com.smdev.lapkibe.model.dto;

import java.util.HashMap;
import java.util.Map;

import com.smdev.lapkibe.model.entity.PetRequestEntity.Type;

import lombok.Data;

@Data
public class PetRequestDTO {
    private String name;
    private int age;
    private String sex;
    private Map<String, String> properties = new HashMap<>();
}
