package com.medzaksdn.ridepriceestimator.service;

import com.medzaksdn.ridepriceestimator.dto.EstimateDTO;
import com.medzaksdn.ridepriceestimator.dto.PriceDTO;

import java.util.List;

/**
 * @author Mohamed-Zakaria SAIDANE
 */
public interface EstimateService {

    List<PriceDTO> estimate(EstimateDTO estimateDTO);
}
