package com.smdev.lapkibe.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.smdev.lapkibe.model.dto.PetDetailsResponse;
import com.smdev.lapkibe.model.dto.PetResponse;

@Service
public interface PetService {
    List<PetResponse> getAllPets();
    List<PetDetailsResponse> getAllPetsDetails();
}
