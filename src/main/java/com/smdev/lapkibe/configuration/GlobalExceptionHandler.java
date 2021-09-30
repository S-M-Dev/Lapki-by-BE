package com.smdev.lapkibe.configuration;

import com.smdev.lapkibe.model.dto.ExceptionResponseWrapperDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponseWrapperDTO> notValidArgsException(BindingResult bindingResult){
        return ResponseEntity
                .badRequest()
                .body(new ExceptionResponseWrapperDTO(
                        bindingResult
                                .getFieldError()
                                .getDefaultMessage()
                ));
    }

}
