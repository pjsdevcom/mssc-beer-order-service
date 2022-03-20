package com.pjsdev.msscbeerorderservice.services;

import com.pjsdev.msscbeerorderservice.domain.BeerOrder;

import java.util.UUID;

public interface BeerOrderManager {

    BeerOrder newBeerOrder(BeerOrder beerOrder);

    void processValidationResult(UUID beerOrderId, Boolean isValid);
}
