package com.example.neighbour.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

import static com.example.neighbour.utils.GeneralStringConstants.FAILED;
import static com.example.neighbour.utils.GeneralStringConstants.SUCCESS;

@Data
@NoArgsConstructor
@JsonPropertyOrder({"timestamp", "success", "status", "message"})
public class ResponseDto<T> {

    private LocalDateTime timestamp = LocalDateTime.now();
    private boolean success;
    private String message;

    private T data;

    public ResponseDto(T dto, String message, boolean success) {
        data = dto;
        this.success = success;
        this.message = message;
    }

    public ResponseDto(T dto) {
        data = dto;
        this.success = true;
        this.message = SUCCESS;
    }

    public static <T> ResponseDto<T> success(T dto, String message) {
        ResponseDto<T> tResponseDto = new ResponseDto<>(dto);
        tResponseDto.setMessage(message);
        return tResponseDto;
    }

    public static <T> ResponseDto<T> failure(T dto) {
        ResponseDto<T> tResponseDto = new ResponseDto<>(dto);
        tResponseDto.setSuccess(false);
        tResponseDto.setMessage(FAILED);
        return tResponseDto;
    }
}
