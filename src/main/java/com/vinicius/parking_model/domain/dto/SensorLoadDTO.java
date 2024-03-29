package com.vinicius.parking_model.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SensorLoadDTO {

    private String id;

    private Integer park;

    private Integer load;

}
