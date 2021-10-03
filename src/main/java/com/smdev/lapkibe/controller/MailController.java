package com.smdev.lapkibe.controller;

import com.smdev.lapkibe.model.dto.ExceptionResponseWrapperDTO;
import com.smdev.lapkibe.model.dto.JwtResponse;
import com.smdev.lapkibe.model.dto.SendMailDTO;
import com.smdev.lapkibe.service.MailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api("MailController")
@RestController
@RequestMapping("/api/mail")
public class MailController {

    private final MailService mailService;

    @Autowired
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @ApiOperation(value = "Send mail from 'Contact Us' from")
    @PostMapping(value = "/send", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void send(@Valid @RequestBody SendMailDTO sendMailDTO){
        mailService.sendMail(sendMailDTO);
    }

}
