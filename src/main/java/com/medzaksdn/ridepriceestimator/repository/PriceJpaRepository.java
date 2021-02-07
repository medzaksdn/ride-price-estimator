package com.medzaksdn.ridepriceestimator.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.medzaksdn.ridepriceestimator.model.Price;
import com.medzaksdn.ridepriceestimator.model.RideOption;

/**
 * @author Mohamed-Zakaria SAIDANE
 */
@Component
public interface PriceJpaRepository extends JpaRepository<Price, Long> {

    List<Price> findByRideOption(RideOption rideOption, Sort sort);

    Price findOneByRideOptionAndDistance(RideOption rideOption, Double distance);

}