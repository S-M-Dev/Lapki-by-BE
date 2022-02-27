package com.smdev.lapkibe.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smdev.lapkibe.model.dto.PetDetailsResponse;
import com.smdev.lapkibe.model.dto.PetResponse;
import com.smdev.lapkibe.repository.PetRepository;
import com.smdev.lapkibe.service.PetService;

@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    @Autowired
    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
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
}
