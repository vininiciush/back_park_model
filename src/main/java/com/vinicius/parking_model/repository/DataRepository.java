package com.vinicius.parking_model.repository;

import com.vinicius.parking_model.domain.entity.DataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DataRepository extends JpaRepository<DataEntity, String> {

    @Query("SELECT de FROM DataEntity de WHERE de.insertDate >= :startDate and de.insertDate <= :endDate")
    List<DataEntity> findAllByDate(@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate);

}
