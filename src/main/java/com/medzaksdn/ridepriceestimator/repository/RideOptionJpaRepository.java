package com.medzaksdn.ridepriceestimator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.medzaksdn.ridepriceestimator.model.RideOption;

import java.util.List;

/**
 * @author Mohamed-Zakaria SAIDANE
 */
@Component
public interface RideOptionJpaRepository extends JpaRepository<RideOption, Long> {

}
