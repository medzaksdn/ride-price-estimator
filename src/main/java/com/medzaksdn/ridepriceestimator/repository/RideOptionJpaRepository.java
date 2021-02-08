package com.medzaksdn.ridepriceestimator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medzaksdn.ridepriceestimator.model.RideOption;

/**
 * @author Mohamed-Zakaria SAIDANE
 */
@Repository
public interface RideOptionJpaRepository extends JpaRepository<RideOption, Long> {

}
