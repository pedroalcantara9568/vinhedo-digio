package com.example.vinhedo_digio.exception.handler;

import com.example.vinhedo_digio.exception.NaoEcontradoException;
import com.example.vinhedo_digio.exception.dto.ErroDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@RequiredArgsConstructor
public class NaoEcontradoExceptionHandler {


    @ExceptionHandler(NaoEcontradoException.class)
    public ResponseEntity<ErroDTO> rejectionAutomaticExceptionHandler(NaoEcontradoException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErroDTO.builder().message(exception.getMessage()).build());
    }
}
