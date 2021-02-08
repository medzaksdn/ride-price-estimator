package com.medzaksdn.ridepriceestimator.dto;

/**
 * @author Mohamed-Zakaria SAIDANE
 */
public class PriceDTO {

    public RideOptionDTO rideOptionDTO;
    public Double price;

    public PriceDTO(RideOptionDTO rideOptionDTO, Double price) {
        this.rideOptionDTO = rideOptionDTO;
        this.price = price;
    }

}