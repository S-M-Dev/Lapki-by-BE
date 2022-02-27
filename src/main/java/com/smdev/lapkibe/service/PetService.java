package com.smdev.lapkibe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.smdev.lapkibe.model.dto.PetDetailsResponse;
import com.smdev.lapkibe.model.dto.PetRequestDTO;
import com.smdev.lapkibe.model.dto.PetResponse;

@Service
public interface PetService {
    List<PetResponse> getAllPets();
    List<PetDetailsResponse> getAllPetsDetails();
    Optional<PetDetailsResponse> getPetDetails(Long id);
    void createRequest(PetRequestDTO petRequestDTO);
}
