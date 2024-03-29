package com.vinicius.parking_model.controller.v1;

import com.vinicius.parking_model.domain.dto.ReceiveDTO;
import com.vinicius.parking_model.domain.dto.SensorDTO;
import com.vinicius.parking_model.domain.dto.SensorLoadDTO;
import com.vinicius.parking_model.service.implementation.SensorServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

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

    @GetMapping("/load")
    public ResponseEntity<Page<SensorLoadDTO>> getLoadSensors(@RequestParam(name = "page", required = false) Integer page, @RequestParam(name = "size", required = false) Integer size, @RequestParam(name = "date") LocalDate date){
        Page<SensorLoadDTO> sensorsLoad = service.getSensorsLoad(page, size, date);

        return ResponseEntity.ok(sensorsLoad);
    }

    @DeleteMapping("/{parkPosition}")
    public ResponseEntity<SensorDTO> deleteSensor(@PathVariable Integer parkPosition){
        SensorDTO sensorDTO = service.deleteSensor(parkPosition);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(sensorDTO);
    }

    @PostMapping("/receive")
    public ResponseEntity<?> receiveSensorData(@Valid @RequestBody ReceiveDTO receiveDTO){
        service.receiveSensorData(receiveDTO);

        return ResponseEntity.ok().build();
    }

}
