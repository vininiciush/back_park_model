package com.vinicius.parking_model.service;

import com.vinicius.parking_model.domain.dto.ChartLoadEntity;
import com.vinicius.parking_model.domain.dto.ReceiveDTO;
import com.vinicius.parking_model.domain.dto.SensorDTO;
import com.vinicius.parking_model.domain.dto.SensorLoadDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface SensorService {

    SensorDTO createSensor(SensorDTO sensorDTO);

    Page<SensorDTO> getSensor(Integer pageNumber, Integer pageSize);

    SensorDTO deleteSensor(Integer parkPosition);

    Page<SensorLoadDTO> getSensorsLoad(Integer pageNumber, Integer pageSize, LocalDate date);

    List<ChartLoadEntity> getLoadForParking(LocalDate date);

    void receiveSensorData(ReceiveDTO receiveDTO);

}
