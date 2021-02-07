package com.medzaksdn.ridepriceestimator.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


/**
 * @author Mohamed-Zakaria SAIDANE
 */
@Entity
public class Price {

    private Long id;
    private Float distance;
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rideoption_id", nullable = false)
    private RideOption rideOption;

    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public RideOption getRideOption() {
        return rideOption;
    }

    public void setRideOption(RideOption rideOption) {
        this.rideOption = rideOption;
    }

}