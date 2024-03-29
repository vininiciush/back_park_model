package com.vinicius.parking_model.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ChartLoadEntity {

    private Integer time;
    private Integer sensors;

}
