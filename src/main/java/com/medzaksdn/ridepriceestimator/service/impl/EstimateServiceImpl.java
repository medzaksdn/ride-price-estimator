package com.medzaksdn.ridepriceestimator.service.impl;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.google.maps.GaeRequestHandler;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrixRow;
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

        for (RideOption rideOption : rideOptions) {
            priceDtos.add(priceCalculate(getDistance(estimateDTO), rideOption));
        }

        return priceDtos;
    }

    private Long getDistance(EstimateDTO estimateDTO) {
        GeoApiContext geoApiContext = new GeoApiContext.Builder(new GaeRequestHandler.Builder())
                .apiKey(this.googleAPIKey)
                .build();

        try {
            DistanceMatrixRow rows[] = DistanceMatrixApi
                    .getDistanceMatrix(geoApiContext, new String[]{estimateDTO.latitude1, estimateDTO.longitude1}, new String[]{estimateDTO.latitude2, estimateDTO.longitude2})
                    .units(Unit.METRIC)
                    .mode(TravelMode.DRIVING).await().rows;

            // We have only one element
            return rows[0].elements[0].distance.inMeters;

            // For more take a look at
            // the Google Maps Distance Matrix API documentation :
            // https://developers.google.com/maps/documentation/distance-matrix/overview
        } catch (ApiException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return null;
    }

    private PriceDTO priceCalculate(Long distance, RideOption rideOption) {
        List<Price> prices = priceJpaRepository.findByRideOption(rideOption, Sort.by(Sort.Direction.ASC, "distance"));

        Double totalPrice = new Double(0);
        Price before = prices.get(0);
        for (Price price : prices) {
            if (distance - price.getDistance() >= 0) {
                totalPrice = totalPrice + (price.getDistance() - before.getDistance()) * price.getPrice();
            } else {
                totalPrice = totalPrice + (distance - before.getDistance()) * price.getPrice();
            }
        }

        // minimum price
        Price minimumPrice = priceJpaRepository.findOneByRideOptionAndDistance(rideOption, new Double(0));

        totalPrice = totalPrice > minimumPrice.getPrice() ? totalPrice : minimumPrice.getPrice();

        return new PriceDTO(new RideOptionDTO(rideOption.getId().toString(), rideOption.getName()), totalPrice);
    }

}