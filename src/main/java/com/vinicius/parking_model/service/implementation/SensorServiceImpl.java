package com.vinicius.parking_model.service.implementation;

import com.vinicius.parking_model.domain.dto.SensorDTO;
import com.vinicius.parking_model.domain.entity.SensorEntity;
import com.vinicius.parking_model.exception.ParkPositionAlreadyExistException;
import com.vinicius.parking_model.mapper.SensorMapper;
import com.vinicius.parking_model.repository.SensorRepository;
import com.vinicius.parking_model.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SensorServiceImpl implements SensorService {

    private final SensorMapper sensorMapper;

    private final SensorRepository sensorRepository;

    public SensorDTO createSensor(SensorDTO sensorDTO){

        validateParkPositionIsValid(sensorDTO.getPark());

        SensorEntity sensorEntity = sensorMapper.toEntity(sensorDTO);

        sensorEntity = sensorEntity
                .toBuilder()
                .insertDate(LocalDateTime.now())
                .build();

        sensorEntity = sensorRepository.save(sensorEntity);

        return sensorMapper.toDTO(sensorEntity);
    }

    private void validateParkPositionIsValid(Integer parkPosition){
        Optional<SensorEntity> sensorOptional = sensorRepository.findAllByPark(parkPosition);
        if(sensorOptional.isPresent()){
            throw new ParkPositionAlreadyExistException(parkPosition, "Cannot create 2 sensor with same park position");
        }
    }

}
