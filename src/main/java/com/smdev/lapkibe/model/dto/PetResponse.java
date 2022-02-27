package com.smdev.lapkibe.model.dto;

import com.smdev.lapkibe.model.entity.PetEntity;

import lombok.Data;

@Data
public class PetResponse {
    private Long id;
    private String name;
    private String description;
    private String sex;
    private int age;

    public PetResponse(PetEntity petEntity){
        this.id = petEntity.getId();
        this.name = petEntity.getName();
        this.description = petEntity.getDescription();
        this.sex = petEntity.getSex();
        this.age = petEntity.getAge();
    }

}
