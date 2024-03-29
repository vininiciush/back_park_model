package com.vinicius.parking_model.service;

import com.vinicius.parking_model.domain.dto.SensorDTO;
import org.springframework.data.domain.Page;

public interface SensorService {

    SensorDTO createSensor(SensorDTO sensorDTO);

    Page<SensorDTO> getSensor(Integer pageNumber, Integer pageSize);

}
