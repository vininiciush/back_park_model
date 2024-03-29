package com.vinicius.parking_model.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ParkPositionAlreadyExistException extends RuntimeException{

    private final Integer parkPosition;

    private final String message;

}
