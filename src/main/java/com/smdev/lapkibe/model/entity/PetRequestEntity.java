package com.smdev.lapkibe.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class PetRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean approved;
    @OneToOne
    private PetEntity petEntity;
    @ManyToOne
    private UserEntity owner;
    private Type type;

    public enum Type{
        TAKE, GIVE, TAKEN
    }

}
