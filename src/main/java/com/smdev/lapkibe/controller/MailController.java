package com.smdev.lapkibe.controller;

import com.smdev.lapkibe.model.dto.SendMailDTO;
import com.smdev.lapkibe.service.MailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api("MailController")
@RestController
@RequestMapping("/api/mail")
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.OPTIONS})
public class MailController {

    private final MailService mailService;

    @Autowired
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @ApiOperation(value = "Send mail from 'Contact Us' from")
    @ApiResponses({
            @ApiResponse(code = 200, message = "On success"),
            @ApiResponse(code = 400, message = "On invalid data", response = String.class)
    })
    @PostMapping(value = "/send", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void send(@Valid @RequestBody SendMailDTO sendMailDTO){
        mailService.sendContactUsMail(sendMailDTO);
    }

}
