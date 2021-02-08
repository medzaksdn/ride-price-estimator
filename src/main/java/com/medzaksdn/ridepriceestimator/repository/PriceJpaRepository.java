package com.medzaksdn.ridepriceestimator.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medzaksdn.ridepriceestimator.model.Price;

/**
 * @author Mohamed-Zakaria SAIDANE
 */
@Repository
public interface PriceJpaRepository extends JpaRepository<Price, Long> {

    List<Price> findByRideOption_Id(long rideOptionId, Sort sort);

    Price findByRideOption_IdAndDistance(long rideOptionId, float distance);

}