package com.smdev.lapkibe.model.dto;

import com.smdev.lapkibe.model.entity.PetProperty;

import lombok.Data;

@Data
public class PropertyDTO {
    private String key;
    private String value;

    public PropertyDTO(PetProperty petProperty){
        this.key = petProperty.getPropKey();
        this.value = petProperty.getValue();
    }

}
