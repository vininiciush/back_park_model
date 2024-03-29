package com.vinicius.parking_model.controller.v1;

import com.vinicius.parking_model.domain.dto.ReceiveDTO;
import com.vinicius.parking_model.domain.dto.SensorDTO;
import com.vinicius.parking_model.service.implementation.SensorServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<Page<SensorDTO>> getSensors(@RequestParam(name = "page", required = false) Integer page, @RequestParam(name = "size", required = false) Integer size){
        Page<SensorDTO> sensor = service.getSensor(page, size);

        return ResponseEntity.ok(sensor);
    }

    @PostMapping("/receive")
    public ResponseEntity<?> receiveSensorData(@Valid @RequestBody ReceiveDTO receiveDTO){
        service.receiveSensorData(receiveDTO);

        return ResponseEntity.ok().build();
    }

}
