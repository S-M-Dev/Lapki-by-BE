package com.smdev.lapkibe.model.entity;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.smdev.lapkibe.model.dto.PetRequestDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class PetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String sex;
    private int age;
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PetProperty> properties = new LinkedList<>();

    public PetEntity(PetRequestDTO petRequestDTO){
        this.name = petRequestDTO.getName();
        this.description = petRequestDTO.getProperties().get("description");
        this.sex = petRequestDTO.getSex();
        this.age = petRequestDTO.getAge();

        this.properties = petRequestDTO.getProperties()
                .entrySet()
                .stream()
                .filter(e -> !e.getKey().equals("description"))
                .map(PetProperty::new)
                .collect(Collectors.toList());
    }

}
