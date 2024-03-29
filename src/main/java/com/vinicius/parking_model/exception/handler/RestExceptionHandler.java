package com.vinicius.parking_model.exception.handler;

import com.vinicius.parking_model.domain.dto.ErrorDTO;
import com.vinicius.parking_model.exception.ParkPositionAlreadyExistException;
import com.vinicius.parking_model.exception.ParkPositionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDTO> handleParkPositionAlreadyExistException(MethodArgumentNotValidException e){
        ErrorDTO error = ErrorDTO.builder()
                .message("Invalid value on field '" + e.getFieldError().getField() + "'")
                .details(e.getBindingResult().getFieldError().getDefaultMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ParkPositionAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDTO> handleParkPositionAlreadyExistException(ParkPositionAlreadyExistException e){
        ErrorDTO error = ErrorDTO.builder()
                .message(e.getMessage())
                .details("park positon " + e.getParkPosition() + " already exist")
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ParkPositionNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDTO> handleParkSensorNotFoundException(ParkPositionNotFoundException e){
        ErrorDTO error = ErrorDTO.builder()
                .message(e.getMessage())
                .details("park positon " + e.getParkPosition() + " not found")
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}
