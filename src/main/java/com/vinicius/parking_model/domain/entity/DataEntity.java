package com.vinicius.parking_model.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Builder
@lombok.Data
@Table(name = "DATA")

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
