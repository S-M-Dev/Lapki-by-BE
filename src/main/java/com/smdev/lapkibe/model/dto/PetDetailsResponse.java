package com.smdev.lapkibe.model.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.smdev.lapkibe.model.entity.PetEntity;

import lombok.Data;

@Data
public class PetDetailsResponse {
    private Long id;
    private String name;
    private String description;
    private String sex;
    private String age;
    private List<PropertyDTO> properties;

    public PetDetailsResponse(PetEntity petEntity){
        this.id = petEntity.getId();
        this.name = petEntity.getName();
        this.description = petEntity.getDescription();
        this.sex = petEntity.getSex();
        this.age = petEntity.getAge();
        this.properties = petEntity
                .getProperties()
                .stream()
                .map(PropertyDTO::new)
                .collect(Collectors.toList());
    }

}
