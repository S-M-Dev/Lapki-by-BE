package com.smdev.lapkibe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smdev.lapkibe.model.entity.PetEntity;

@Repository
public interface PetRepository extends JpaRepository<PetEntity, Long> {

}
