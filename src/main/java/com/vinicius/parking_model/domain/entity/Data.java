package com.vinicius.parking_model.domain.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@lombok.Data
@Table(name = "DATA")
public class Data {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private Integer dataValue;

    private LocalDateTime insertDate;

    @ManyToOne
    private SensorEntity sensor;
}
