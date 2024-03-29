package com.vinicius.parking_model.mapper;

import com.vinicius.parking_model.domain.dto.SensorDTO;
import com.vinicius.parking_model.domain.entity.SensorEntity;
import org.mapstruct.Mapper;

@Mapper
public interface SensorMapper {

    public SensorEntity toEntity(SensorDTO sensorDTO);

    public SensorDTO toDTO(SensorEntity sensorEntity);

}
