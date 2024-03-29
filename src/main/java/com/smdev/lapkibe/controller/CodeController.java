package com.smdev.lapkibe.controller;

import java.net.http.HttpResponse;

import com.smdev.lapkibe.model.dto.ExceptionResponseWrapperDTO;
import com.smdev.lapkibe.model.dto.JwtResponse;
import com.smdev.lapkibe.model.dto.PasswordResetRequestDTO;
import com.smdev.lapkibe.model.dto.ResetCodeValidationsRequest;
import com.smdev.lapkibe.service.CodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api("CodeController")
@RestController
@RequestMapping("/api/code")
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.OPTIONS})
public class CodeController {

    private final CodeService codeService;

    @Autowired
    public CodeController(CodeService codeService) {
        this.codeService = codeService;
    }

    @ApiOperation(value = "Send code to restore password")
    @ApiResponses({
            @ApiResponse(code = 200, message = "If code sent successfully", response = JwtResponse.class),
            @ApiResponse(code = 400, message = "Invalid data", response = ExceptionResponseWrapperDTO.class),
            @ApiResponse(code = 204, message = "No email found")
    })
    @PostMapping(value = "/generate", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity generate(@Valid @RequestBody PasswordResetRequestDTO passwordResetRequestDTO){
        boolean result = codeService.send(passwordResetRequestDTO);

        if(result){
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.noContent().build();
    }


    @ApiOperation("Change password by reset code")
    @ApiResponses({
            @ApiResponse(code = 200, message = "JWT token in HTTP response", response = JwtResponse.class),
            @ApiResponse(code = 202, message = "If code is invalid"),
            @ApiResponse(code = 204, message = "User not found or code doesn't exist")
    })
    @PostMapping(value = "/validate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity validate(@Valid @RequestBody final ResetCodeValidationsRequest codeValidationsRequest){
        return codeService.validate(codeValidationsRequest);
    }
}
