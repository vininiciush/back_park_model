package com.vinicius.parking_model.service.implementation;

import com.vinicius.parking_model.domain.dto.ReceiveDTO;
import com.vinicius.parking_model.domain.dto.SensorDTO;
import com.vinicius.parking_model.domain.entity.DataEntity;
import com.vinicius.parking_model.domain.entity.SensorEntity;
import com.vinicius.parking_model.exception.ParkPositionAlreadyExistException;
import com.vinicius.parking_model.exception.ParkPositionNotFoundException;
import com.vinicius.parking_model.mapper.SensorMapper;
import com.vinicius.parking_model.repository.DataRepository;
import com.vinicius.parking_model.repository.SensorRepository;
import com.vinicius.parking_model.service.SensorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SensorServiceImpl implements SensorService {

    private final SensorMapper sensorMapper;

    private final SensorRepository sensorRepository;

    private final DataRepository dataRepository;

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

    @Override
    public Page<SensorDTO> getSensor(Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber != null ? pageNumber : 0, pageSize != null ? pageSize : 10);

        return sensorRepository.findAll(page).map(sensorMapper::toDTO);
    }

    @Override
    public SensorDTO deleteSensor(Integer parkPosition) {
        Optional<SensorEntity> sensorOptional = sensorRepository.findAllByPark(parkPosition);

        sensorOptional
                .ifPresentOrElse(sensorRepository::delete, () -> {
                    throw new ParkPositionNotFoundException(parkPosition, "Park position not found");
                });

        return sensorMapper.toDTO(sensorOptional.get());
    }

    @Override
    public void receiveSensorData(ReceiveDTO receiveDTO) {
        Optional<SensorEntity> sensorOptional = sensorRepository.findAllByPark(receiveDTO.getPark());

        if(sensorOptional.isPresent()){
            DataEntity dataEntity = DataEntity.builder()
                    .sensor(sensorOptional.get())
                    .insertDate(LocalDateTime.now())
                    .dataValue(receiveDTO.getValue())
                    .build();

            dataRepository.save(dataEntity);
        }else {
            throw new ParkPositionNotFoundException(receiveDTO.getPark(), "Park position not found");
        }
    }

    private void validateParkPositionIsValid(Integer parkPosition){
        sensorRepository.findAllByPark(parkPosition).ifPresent(sensorEntity -> {
            throw new ParkPositionAlreadyExistException(parkPosition, "Cannot create 2 sensor with same park position");
        });
    }

}
