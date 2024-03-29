package com.vinicius.parking_model.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Builder
@lombok.Data
@Table(name = "DATA")
@RequiredArgsConstructor
@AllArgsConstructor
public class DataEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "DATA_VALUE")
    private Integer dataValue;

    @Column(name = "INSERT_DATE")
    private LocalDateTime insertDate;

    @ManyToOne
    private SensorEntity sensor;
}
