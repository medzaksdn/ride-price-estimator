package com.medzaksdn.ridepriceestimator.service.impl;

import com.google.maps.GaeRequestHandler;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrixRow;
import com.google.maps.model.TravelMode;
import com.google.maps.model.Unit;
import com.medzaksdn.ridepriceestimator.dto.EstimateDTO;
import com.medzaksdn.ridepriceestimator.dto.PriceDTO;
import com.medzaksdn.ridepriceestimator.service.EstimateService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author Mohamed-Zakaria SAIDANE
 */
@Service
public class EstimateServiceImpl implements EstimateService {

    @Value("${google.api.key}")
    private String googleAPIKey;

    @Override
    public List<PriceDTO> estimate(EstimateDTO estimateDTO) {
        Long distance = getDistances(estimateDTO);

        return null;
    }

    private Long getDistances(EstimateDTO estimateDTO) {
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

    //private Double priceCalculate(Long distance, )
}
