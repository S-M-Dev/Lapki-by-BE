package com.smdev.lapkibe.model.entity;

import java.util.Map.Entry;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class PetProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String propKey;
    private String value;

    public PetProperty(Entry<String, String> entry){
        this.propKey = entry.getKey();
        this.value = entry.getValue();
    }

}
