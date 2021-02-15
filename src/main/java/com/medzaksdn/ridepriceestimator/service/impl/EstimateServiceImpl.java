package com.medzaksdn.ridepriceestimator.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.errors.ApiException;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixRow;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.google.maps.model.Unit;

import com.medzaksdn.ridepriceestimator.dto.EstimateDTO;
import com.medzaksdn.ridepriceestimator.dto.PriceDTO;
import com.medzaksdn.ridepriceestimator.dto.RideOptionDTO;
import com.medzaksdn.ridepriceestimator.model.Price;
import com.medzaksdn.ridepriceestimator.model.RideOption;
import com.medzaksdn.ridepriceestimator.repository.PriceJpaRepository;
import com.medzaksdn.ridepriceestimator.repository.RideOptionJpaRepository;
import com.medzaksdn.ridepriceestimator.service.EstimateService;

/**
 * @author Mohamed-Zakaria SAIDANE
 */
@Service
public class EstimateServiceImpl implements EstimateService {

    @Value("${google.api.key}")
    private String googleAPIKey;

    @Resource
    private RideOptionJpaRepository rideOptionJpaRepository;

    @Resource
    private PriceJpaRepository priceJpaRepository;

    @Override
    public List<PriceDTO> estimate(EstimateDTO estimateDTO) {
        List<PriceDTO> priceDtos = new ArrayList<>();
        List<RideOption> rideOptions = rideOptionJpaRepository.findAll();

        Long distance = getDistance(estimateDTO);
        if (distance != null) {
            for (RideOption rideOption : rideOptions) {
                priceDtos.add(priceCalculate(distance, rideOption));
            }
        }

        return priceDtos;
    }

    private Long getDistance(EstimateDTO estimateDTO) {
        GeoApiContext geoApiContext = new GeoApiContext.Builder()
                .apiKey(this.googleAPIKey)
                .build();

        try {
            DistanceMatrix matrix = DistanceMatrixApi.newRequest(geoApiContext)
                    .origins(new LatLng(Double.parseDouble(estimateDTO.latitude1), Double.parseDouble(estimateDTO.longitude1)))
                    .destinations(new LatLng(Double.parseDouble(estimateDTO.latitude2), Double.parseDouble(estimateDTO.longitude2)))
                    .units(Unit.METRIC)
                    .mode(TravelMode.DRIVING).await();

            DistanceMatrixRow rows[] = matrix.rows;

            // We have only one element
            return rows[0].elements[0].distance.inMeters;

            // For more take a look at the Google Maps Distance Matrix API documentation :
            // https://developers.google.com/maps/documentation/distance-matrix/overview
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private PriceDTO priceCalculate(Long distance, RideOption rideOption) {
        List<Price> prices = priceJpaRepository.findByRideOption_Id(rideOption.getId(), Sort.by(Sort.Direction.ASC, "distance"));

        Double totalPrice = new Double(0);
        Price before = prices.get(0);
        for (Price price : prices) {
            if (distance >= price.getDistance()) {
                totalPrice = totalPrice + (price.getDistance() - before.getDistance()) * price.getPrice() / 1000;
            } else {
                totalPrice = totalPrice + (distance - before.getDistance()) * price.getPrice() / 1000;
                break;
            }
        }

        // get minimum price
        Price minimumPrice = priceJpaRepository.findByRideOption_IdAndDistance(rideOption.getId(), 0);

        totalPrice = totalPrice > minimumPrice.getPrice() ? totalPrice : minimumPrice.getPrice();

        return new PriceDTO(new RideOptionDTO(rideOption.getId().toString(), rideOption.getName()), totalPrice);
    }

}