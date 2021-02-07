package com.medzaksdn.ridepriceestimator.service;

import java.util.List;

import com.medzaksdn.ridepriceestimator.dto.EstimateDTO;
import com.medzaksdn.ridepriceestimator.dto.PriceDTO;

/**
 * @author Mohamed-Zakaria SAIDANE
 */
public interface EstimateService {

    List<PriceDTO> estimate(EstimateDTO estimateDTO);

}