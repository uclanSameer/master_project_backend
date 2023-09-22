package com.example.neighbour.configuration;


import com.example.neighbour.dto.ResponseDto;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ExceptionHandlerConfig {

    @ExceptionHandler({ResponseStatusException.class, ErrorResponseException.class})
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex) {
        ResponseDto<String> responseDto = ResponseDto.failure(ex.getMessage());
        return new ResponseEntity<>(responseDto, ex.getStatusCode());
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleResponseStatusException(BadCredentialsException ex) {
        ResponseDto<String> responseDto = ResponseDto.failure(ex.getMessage());
        return new ResponseEntity<>(responseDto, HttpStatusCode.valueOf(401));
    }
}
