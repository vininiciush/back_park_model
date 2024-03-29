package com.vinicius.parking_model.domain.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReceiveDTO {

    @NotNull
    private Integer park;

    @NotNull
    @Max(1)
    @Min(0)
    private Integer value;

}
