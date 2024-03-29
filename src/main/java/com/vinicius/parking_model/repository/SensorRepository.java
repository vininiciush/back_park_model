package com.vinicius.parking_model.repository;

import com.vinicius.parking_model.domain.entity.SensorEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SensorRepository extends JpaRepository<SensorEntity, String> {

    Optional<SensorEntity> findAllByPark(Integer park);

}
