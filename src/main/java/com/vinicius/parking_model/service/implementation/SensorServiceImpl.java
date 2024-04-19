package com.vinicius.parking_model.service.implementation;

import com.vinicius.parking_model.domain.dto.ChartLoadEntity;
import com.vinicius.parking_model.domain.dto.ReceiveDTO;
import com.vinicius.parking_model.domain.dto.SensorDTO;
import com.vinicius.parking_model.domain.dto.SensorLoadDTO;
import com.vinicius.parking_model.domain.entity.DataEntity;
import com.vinicius.parking_model.domain.entity.SensorEntity;
import com.vinicius.parking_model.exception.ParkPositionAlreadyExistException;
import com.vinicius.parking_model.exception.ParkPositionNotFoundException;
import com.vinicius.parking_model.mapper.SensorMapper;
import com.vinicius.parking_model.repository.DataRepository;
import com.vinicius.parking_model.repository.SensorRepository;
import com.vinicius.parking_model.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
        Pageable page = createDefaultPage(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "park"));

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
    public Page<SensorLoadDTO> getSensorsLoad(Integer pageNumber, Integer pageSize, LocalDate date) {

        List<SensorLoadDTO> sensorLoadDTOS = new ArrayList<>();

        Page<SensorDTO> sensor = getSensor(pageNumber, pageSize);
        List<DataEntity> dataByDate = dataRepository.findAllByDate(date.atStartOfDay(), date.plusDays(1).atStartOfDay());

        sensor.forEach(sensorDTO -> {

            List<DataEntity> dataEntityForSensor = dataByDate.stream()
                    .filter(dataEntity -> dataEntity.getSensor().getPark().equals(sensorDTO.getPark()))
                    .toList();

            Boolean isActive = dataEntityForSensor.stream()
                    .findFirst()
                    .map(dataEntity -> dataEntity.getDataValue() == 1)
                    .orElse(false);

            long totalTimeData = dataEntityForSensor.size();

            long activeTimeData = dataEntityForSensor.stream()
                    .filter(dataEntity -> dataEntity.getDataValue().equals(1))
                    .count();

            SensorLoadDTO sensorLoadDTO = SensorLoadDTO
                    .builder()
                    .id(sensorDTO.getId())
                    .park(sensorDTO.getPark())
                    .load(calculateLoad(totalTimeData, activeTimeData))
                    .isActive(isActive)
                    .build();

            sensorLoadDTOS.add(sensorLoadDTO);

        });

        return new PageImpl<>(sensorLoadDTOS, PageRequest.of(pageNumber, pageSize), sensor.getTotalElements());
    }

    @Override
    public List<ChartLoadEntity> getLoadForParking(LocalDate date) {

        List<ChartLoadEntity> chartLoadEntities = new ArrayList<>();
        List<DataEntity> dataByDate = dataRepository.findAllByDate(date.atStartOfDay(), date.plusDays(1).atStartOfDay());
        List<SensorEntity> sensors = sensorRepository.findAll();

        for (int i = 0; i < 24; i++) {

            ChartLoadEntity hourChart = ChartLoadEntity.builder()
                    .time(i)
                    .sensors(0)
                    .build();

            LocalDateTime startDateTime = date.atStartOfDay().plusHours(i);
            LocalDateTime endDateTime = date.atStartOfDay().plusHours(i).plusMinutes(59);

            List<DataEntity> hourData = dataByDate.stream()
                    .filter(dataEntity -> dataEntity.getInsertDate().isAfter(startDateTime) && dataEntity.getInsertDate().isBefore(endDateTime))
                    .toList();

            sensors.stream().forEach(sensorEntity -> {
                List<DataEntity> hourSensorData = hourData.stream()
                        .filter(dataEntity -> dataEntity.getSensor().getPark().equals(sensorEntity.getPark()))
                        .toList();

                long totalData = hourSensorData.size();
                long totalActiveData = hourSensorData.stream().filter(dataEntity -> dataEntity.getDataValue().equals(1)).count();

                Integer loadForHour = calculateLoad(totalData, totalActiveData);

                if(loadForHour >= 50){
                    hourChart.setSensors(hourChart.getSensors() + 1);
                }
            });

            chartLoadEntities.add(hourChart);

        }

        return chartLoadEntities;
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

    private Pageable createDefaultPage(Integer pageNumber, Integer pageSize, Sort sort){
        return PageRequest.of(pageNumber != null ? pageNumber : 0, pageSize != null ? pageSize : 10, sort);
    }

    private Integer calculateLoad(long total, long actives){
        return total != 0 ? Math.toIntExact((actives * 100) / total) : 0;
    }

}
