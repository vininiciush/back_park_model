package com.vinicius.parking_model.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SensorDTO {

    private String id;

    @NotNull
    private Integer park;

    @NotNull
    private Integer deviceId;

    private Boolean enable = Boolean.TRUE;

    private String details;

    private LocalDateTime insertDate;

}
