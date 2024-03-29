package com.vinicius.parking_model.exception.handler;

import com.vinicius.parking_model.domain.dto.ErrorDTO;
import com.vinicius.parking_model.exception.ParkPositionAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ParkPositionAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDTO> handleParkPositionAlreadyExistException(ParkPositionAlreadyExistException e){
        ErrorDTO error = ErrorDTO.builder()
                .message(e.getMessage())
                .details("park positon " + e.getParkPosition() + " already exist")
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}
