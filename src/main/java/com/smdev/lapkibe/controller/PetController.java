package com.smdev.lapkibe.controller;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.smdev.lapkibe.model.dto.PetDetailsResponse;
import com.smdev.lapkibe.model.dto.PetRequestDTO;
import com.smdev.lapkibe.model.dto.PetRequestResponse;
import com.smdev.lapkibe.model.dto.PetResponse;
import com.smdev.lapkibe.service.PetService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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

    @ApiOperation("Get pet details by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully found pet", response = PetDetailsResponse.class),
            @ApiResponse(code = 404, message = "No pet found")
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getPetDetailsById(@PathParam("id") Long id){
        Optional<PetDetailsResponse> response = petService.getPetDetails(id);
        if(response.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response.get());
    }

    @PostMapping(value = "/request", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long createPetRequest(@RequestBody PetRequestDTO petRequestDTO){
        return petService.createRequest(petRequestDTO);
    }

    @GetMapping(value = "/waiting", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PetRequestResponse> getAllRequests(){
        return petService.getAllNotApprovedPetRequest();
    }

    @GetMapping(value = "/own", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PetRequestResponse> getAllForCurrent(){
        return petService.getAllForCurrentUser();
    }

    @PostMapping(value = "/upload/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void upload(@RequestParam MultipartFile image, @PathParam("id") Long id){
        petService.updateImage(image, id);
    }

    @GetMapping(value = "/image/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity image(@PathParam("id") Long id){
        byte[] image = petService.getImage(id);
        if(image == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment")
                .body(image);
    }

    @GetMapping(value = "/approved", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PetResponse> getAllApproved(){
        return petService.getAllApproved();
    }


    @PostMapping(value = "/take", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void take(@RequestParam Long id){
        petService.take(id);
    }

}
