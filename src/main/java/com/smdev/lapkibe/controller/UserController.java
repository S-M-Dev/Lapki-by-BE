package com.smdev.lapkibe.controller;

import com.smdev.lapkibe.model.dto.*;
import com.smdev.lapkibe.service.UserService;
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

@Api
@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.PATCH, RequestMethod.OPTIONS})
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Register a new user with unique email")
    @ApiResponses({
            @ApiResponse(code = 200, message = "JWT on success", response = JwtResponse.class),
            @ApiResponse(code = 400, message = "Invalid data", response = ExceptionResponseWrapperDTO.class),
            @ApiResponse(code = 401, message = "Bad credentials")
    })
    @PostMapping(value = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity register(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO){
        String jwt = userService.registerUser(userRegistrationDTO);

        if(!jwt.isEmpty()){
            return ResponseEntity.ok(new JwtResponse(jwt));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ApiOperation(value = "Login as existing user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "JWT on success", response = JwtResponse.class),
            @ApiResponse(code = 400, message = "Invalid data", response = ExceptionResponseWrapperDTO.class),
            @ApiResponse(code = 401, message = "Bad credentials")
    })
    @PostMapping(value = "/login", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity login(@Valid @RequestBody UserLoginDTO userLoginDTO){
        String jwt = userService.loginUser(userLoginDTO);

        if(!jwt.isEmpty()){
            return ResponseEntity.ok(new JwtResponse(jwt));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ApiOperation(value = "Change password of currently authenticated user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "JWT on success", response = JwtResponse.class),
            @ApiResponse(code = 400, message = "Invalid data", response = ExceptionResponseWrapperDTO.class),
            @ApiResponse(code = 401, message = "Bad credentials")
    })
    @PatchMapping(value = "/change", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity changePassword(@Valid @RequestBody PasswordChangeDTO passwordChangeDTO){
        String jwt = userService.changePassword(passwordChangeDTO);

        if(!jwt.isEmpty()){
            return ResponseEntity.ok(new JwtResponse(jwt));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponseDTO getCurrent(){
        return userService.getCurrent();
    }
}
