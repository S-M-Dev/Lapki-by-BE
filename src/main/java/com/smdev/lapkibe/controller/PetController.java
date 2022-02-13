package com.smdev.lapkibe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.smdev.lapkibe.model.dto.PetDetailsResponse;
import com.smdev.lapkibe.model.dto.PetResponse;
import com.smdev.lapkibe.service.PetService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("PetController")
@RestController
@RequestMapping("/api/pet")
@CrossOrigin(origins = "*", methods = {RequestMethod.OPTIONS, RequestMethod.GET})
public class PetController {

    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @ApiOperation("Get all pets without properties")
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PetResponse> all(){
        return petService.getAllPets();
    }

    @ApiOperation("Get all pet's details")
    @GetMapping(value = "/allDetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PetDetailsResponse> allDetails(){
        return petService.getAllPetsDetails();
    }

}
