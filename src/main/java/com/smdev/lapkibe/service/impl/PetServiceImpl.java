package com.smdev.lapkibe.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smdev.lapkibe.model.dto.PetDetailsResponse;
import com.smdev.lapkibe.model.dto.PetRequestDTO;
import com.smdev.lapkibe.model.dto.PetRequestResponse;
import com.smdev.lapkibe.model.dto.PetResponse;
import com.smdev.lapkibe.model.entity.PetEntity;
import com.smdev.lapkibe.model.entity.PetRequestEntity;
import com.smdev.lapkibe.model.entity.PetRequestEntity.Type;
import com.smdev.lapkibe.model.entity.UserEntity;
import com.smdev.lapkibe.repository.PetPropertyRepository;
import com.smdev.lapkibe.repository.PetRepository;
import com.smdev.lapkibe.repository.PetRequestRepository;
import com.smdev.lapkibe.repository.UserRepository;
import com.smdev.lapkibe.service.PetService;

import lombok.SneakyThrows;

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
    public Long createRequest(PetRequestDTO petRequestDTO) {
        final String email = SecurityContextHolder.getContext().getAuthentication().getName();
        final UserEntity user = userRepository.findByEmail(email).get();

        PetEntity pet = new PetEntity(petRequestDTO);
        petPropertyRepository.saveAll(pet.getProperties());
        petRepository.save(pet);

        PetRequestEntity petRequestEntity = new PetRequestEntity();
        petRequestEntity.setPetEntity(pet);
        petRequestEntity.setApproved(false);
        petRequestEntity.setOwner(user);
        petRequestEntity.setType(Type.GIVE);
        petRequestRepository.save(petRequestEntity);

        user.addRequest(petRequestEntity);
        userRepository.save(user);
        return petRequestEntity.getId();
    }

    @Override
    public List<PetRequestResponse> getAllNotApprovedPetRequest() {
        return petRequestRepository.findAll()
                .stream()
                .filter(r -> !r.isApproved())
                .map(PetRequestResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<PetRequestResponse> getAllForCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return getAllNotApprovedPetRequest()
                .stream()
                .filter(p -> p.getEmail().equals(email))
                .collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    public void updateImage(MultipartFile file, Long id) {
        PetEntity petEntity = petRepository.findById(id).get();
        petEntity.setImage(file.getBytes());
        petRepository.save(petEntity);
    }

    @Override
    public byte[] getImage(Long id) {
        return petRepository.findById(id).get().getImage();
    }

    @Override
    public void take(Long id) {
        final String email = SecurityContextHolder.getContext().getAuthentication().getName();
        final UserEntity user = userRepository.findByEmail(email).get();

        PetRequestEntity petRequestEntity = new PetRequestEntity();
        petRequestEntity.setPetEntity(petRepository.getById(id));
        petRequestEntity.setType(Type.TAKE);
        petRequestEntity.setApproved(false);
        petRequestEntity.setOwner(user);
        petRequestRepository.save(petRequestEntity);
    }

    @Override
    public List<PetResponse> getAllApprovedGiveRequests() {
        return petRequestRepository.findAll()
                .stream()
                .filter(r -> r.isApproved() && r.getType() == Type.GIVE)
                .map(PetRequestEntity::getPetEntity)
                .map(PetResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<PetRequestResponse> getAllApproved() {
        return petRequestRepository.findAll()
                .stream()
                .filter(PetRequestEntity::isApproved)
                .map(PetRequestResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public void approve(Long id) {
        PetRequestEntity request = petRequestRepository.getById(id);

        request.setApproved(true);

        petRequestRepository.save(request);

        if (request.getType() == Type.TAKE) {
            PetRequestEntity giveRequest =  petRequestRepository.findAll()
                    .stream()
                    .filter(r -> r.getType() == Type.GIVE)
                    .filter(r -> r.getPetEntity().getId() == request.getPetEntity().getId())
                    .findAny()
                    .get();

            petRequestRepository.delete(giveRequest);

            List<PetRequestEntity> restTakeRequests = petRequestRepository.findAll()
                    .stream()
                    .filter(r -> r.getType() == Type.TAKE)
                    .filter(r -> r.getPetEntity().getId() == request.getPetEntity().getId())
                    .filter(r -> r.getId() != request.getId())
                    .collect(Collectors.toList());

            petRequestRepository.deleteAll(restTakeRequests);
        }
    }

    @Override
    public void decline(Long id) {
        PetRequestEntity request = petRequestRepository.getById(id);

        if(request.getType() == Type.TAKE){
            request.setPetEntity(null);
            request.getOwner().removeRequest(request);
            petRequestRepository.delete(request);
            return;
        }

        PetEntity petEntity = request.getPetEntity();
        request.setPetEntity(null);
        petRepository.delete(petEntity);
        UserEntity owner = request.getOwner();
        owner.removeRequest(request);
        request.setOwner(null);
        userRepository.save(owner);
        petRequestRepository.delete(request);
    }
}
