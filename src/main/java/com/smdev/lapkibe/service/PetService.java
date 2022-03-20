package com.smdev.lapkibe.service;

import java.util.List;
import java.util.Optional;

import com.smdev.lapkibe.model.entity.PetRequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smdev.lapkibe.model.dto.PetDetailsResponse;
import com.smdev.lapkibe.model.dto.PetRequestDTO;
import com.smdev.lapkibe.model.dto.PetRequestResponse;
import com.smdev.lapkibe.model.dto.PetResponse;

@Service
public interface PetService {
    List<PetResponse> getAllPets();
    List<PetDetailsResponse> getAllPetsDetails();
    Optional<PetDetailsResponse> getPetDetails(Long id);
    Long createRequest(PetRequestDTO petRequestDTO);
    List<PetRequestResponse> getAllNotApprovedPetRequest();
    List<PetRequestResponse> getAllForCurrentUser();
    void updateImage(final MultipartFile file, Long id);
    byte[] getImage(Long id);
    void take(Long id);
    List<PetResponse> getAllApprovedGiveRequests();
    List<PetRequestEntity> getAllApproved();
    void approve(Long id);
    void decline(Long id);
}
