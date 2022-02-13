package com.smdev.lapkibe.model.entity;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class PetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String sex;
    private String age;
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PetProperty> properties = new LinkedList<>();

}
