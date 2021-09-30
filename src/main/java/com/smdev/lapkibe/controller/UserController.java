package com.smdev.lapkibe.controller;

import com.smdev.lapkibe.model.dto.ExceptionResponseWrapperDTO;
import com.smdev.lapkibe.model.dto.UserRegistrationDTO;
import com.smdev.lapkibe.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Register a new user with unique email")
    @ApiResponses({
            @ApiResponse(code = 200, message = "JWT on success", response = String.class),
            @ApiResponse(code = 400, message = "Invalid data", response = ExceptionResponseWrapperDTO.class),
            @ApiResponse(code = 401, message = "Bad credentials")
    })
    @PostMapping("/register")
    public ResponseEntity register(@Valid UserRegistrationDTO userRegistrationDTO){
        boolean isRegistered = userService.registerUser(userRegistrationDTO);

        if(isRegistered){
            //Todo Add jwt here
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
