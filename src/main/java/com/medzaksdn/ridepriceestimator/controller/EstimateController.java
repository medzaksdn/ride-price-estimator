package com.medzaksdn.ridepriceestimator.controller;

import java.util.List;

import javax.annotation.Resource;

import com.medzaksdn.ridepriceestimator.service.EstimateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medzaksdn.ridepriceestimator.dto.EstimateDTO;
import com.medzaksdn.ridepriceestimator.dto.PriceDTO;

/**
 * @author Mohamed-Zakaria SAIDANE
 */
@RestController
@RequestMapping("/estimate")
public class EstimateController {

    @Resource
    EstimateService estimateService;

    @PostMapping(value="")
    public ResponseEntity<List<PriceDTO>> estimate(@RequestBody final EstimateDTO estimateDTO) {
        return new ResponseEntity<List<PriceDTO>>(estimateService.estimate(estimateDTO), HttpStatus.OK);
    }
}
