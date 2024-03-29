package com.vinicius.parking_model.controller.v1;

import com.vinicius.parking_model.domain.dto.SensorDTO;
import com.vinicius.parking_model.service.implementation.SensorServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/sensors")
@RequiredArgsConstructor
public class SensorController {

    private final SensorServiceImpl service;

    @PostMapping
    public ResponseEntity<SensorDTO> createSensor(@Valid @RequestBody SensorDTO sensorDTO){
        SensorDTO sensor = service.createSensor(sensorDTO);

        return ResponseEntity.ok(sensor);
    }

}
