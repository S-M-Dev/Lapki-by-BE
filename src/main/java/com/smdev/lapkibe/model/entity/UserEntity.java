package com.smdev.lapkibe.model.entity;

import java.util.LinkedList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

@Entity
@NoArgsConstructor
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String password;
    private UserRole role;
    @Lob
    private byte[] image;
    @OneToMany(mappedBy = "owner", orphanRemoval = true)
    private List<PetRequestEntity> petRequestEntities = new LinkedList<>();

    public void addRequest(PetRequestEntity petRequestEntity){
        petRequestEntities.add(petRequestEntity);
    }

    public void removeRequest(PetRequestEntity petRequestEntity){
        petRequestEntities.remove(petRequestEntity);
    }

}
