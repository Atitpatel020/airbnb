package com.schedular.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "bathroom", nullable = false)
    private String bathroom;

    @Column(name = "guests", nullable = false)
    private Integer guests;

    @Column(name = "bed", nullable = false)
    private Integer bed;

    @Column(name = "bedroom", nullable = false)
    private Integer bedroom;

    @Column(name = "nightly_price", nullable = false)
    private Integer nightlyPrice;

    @Column(name = "property_name", nullable = false, unique = true)
    private String propertyName;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

}