package com.vinicius.parking_model.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDTO {

    private String message;

    private String details;

}
