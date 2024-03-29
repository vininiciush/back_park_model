package com.vinicius.parking_model.repository;

import com.vinicius.parking_model.domain.entity.DataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepository extends JpaRepository<DataEntity, String> {

}
