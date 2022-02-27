package com.smdev.lapkibe.model.dto;

import java.util.HashMap;
import java.util.Map;

import com.smdev.lapkibe.model.entity.PetRequestEntity;
import com.smdev.lapkibe.model.entity.PetRequestEntity.Type;

import lombok.Data;

@Data
public class PetRequestResponse {
    private String name;
    private String sex;
    private int age;
    private String email;
    private Map<String, String> properties;
    private Type type;
    private Long petId;

    public PetRequestResponse(PetRequestEntity petRequestEntity){
        this.name = petRequestEntity.getPetEntity().getName();
        this.sex = petRequestEntity.getPetEntity().getSex();
        this.age = petRequestEntity.getPetEntity().getAge();
        this.email = petRequestEntity.getOwner().getEmail();
        this.type = petRequestEntity.getType();
        this.properties = new HashMap<>();

        this.petId = petRequestEntity.getPetEntity().getId();

        petRequestEntity
                .getPetEntity()
                .getProperties()
                .forEach(p -> properties.put(p.getKey(), p.getValue()));
    }
}
