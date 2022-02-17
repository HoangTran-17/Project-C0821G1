package com.codegym.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "detail_motor")
public class DetailMotor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_motor_id", nullable = false)
    private Long detailMotorId;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private BrandMotor brandMotor;

    @ManyToOne
    @JoinColumn(name = "series_id", nullable = false)
    private SeriesMotor seriesMotor;

    @ManyToOne
    @JoinColumn(name = "type_motor_id", nullable = false)
    private TypeMotor typeMotor;

    @ManyToOne
    @JoinColumn(name = "model_year_id", nullable = false)
    private ModelYear modelYear;

    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
    private ColorMotor colorMotor;

}
