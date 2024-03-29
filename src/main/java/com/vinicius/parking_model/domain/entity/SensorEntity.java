package com.vinicius.parking_model.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@Entity(name = "SENSOR")
@NoArgsConstructor
@AllArgsConstructor
public class SensorEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "PARK")
    private Integer park;

    @Column(name = "DEVICE_ID")
    private Integer deviceId;

    @Column(name = "ENABLE")
    private Boolean enable;

    @Column(name = "DETAILS")
    private String details;

    @Column(name = "INSERT_DATE")
    private LocalDateTime insertDate;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

}
