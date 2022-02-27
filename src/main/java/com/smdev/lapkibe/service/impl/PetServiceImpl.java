package com.smdev.lapkibe.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import com.smdev.lapkibe.model.dto.PetDetailsResponse;
import com.smdev.lapkibe.model.dto.PetRequestDTO;
import com.smdev.lapkibe.model.dto.PetResponse;
import com.smdev.lapkibe.model.entity.PetEntity;
import com.smdev.lapkibe.model.entity.PetRequestEntity;
import com.smdev.lapkibe.model.entity.UserEntity;
import com.smdev.lapkibe.repository.PetPropertyRepository;
import com.smdev.lapkibe.repository.PetRepository;
import com.smdev.lapkibe.repository.PetRequestRepository;
import com.smdev.lapkibe.repository.UserRepository;
import com.smdev.lapkibe.service.PetService;
import com.smdev.lapkibe.service.UserService;

@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final PetRequestRepository petRequestRepository;
    private final UserRepository userRepository;
    private final PetPropertyRepository petPropertyRepository;

    @Autowired
    public PetServiceImpl(PetRepository petRepository,
            PetRequestRepository petRequestRepository, UserRepository userRepository,
            PetPropertyRepository petPropertyRepository) {
        this.petRepository = petRepository;
        this.petRequestRepository = petRequestRepository;
        this.userRepository = userRepository;
        this.petPropertyRepository = petPropertyRepository;
    }

    @Override
    public List<PetResponse> getAllPets() {
        return petRepository.findAll()
                .stream()
                .map(PetResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<PetDetailsResponse> getAllPetsDetails() {
        return petRepository.findAll()
                .stream()
                .map(PetDetailsResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PetDetailsResponse> getPetDetails(Long id) {
        return petRepository.findById(id).map(PetDetailsResponse::new);
    }

    @Override
    public void createRequest(PetRequestDTO petRequestDTO) {
        final String email = SecurityContextHolder.getContext().getAuthentication().getName();
        final UserEntity user = userRepository.findByEmail(email).get();

        PetEntity pet = new PetEntity(petRequestDTO);
        petPropertyRepository.saveAll(pet.getProperties());
        petRepository.save(pet);

        PetRequestEntity petRequestEntity = new PetRequestEntity();
        petRequestEntity.setPetEntity(pet);
        petRequestEntity.setApproved(false);
        petRequestEntity.setOwner(user);
        petRequestRepository.save(petRequestEntity);

        user.addRequest(petRequestEntity);
        userRepository.save(user);
    }
}
