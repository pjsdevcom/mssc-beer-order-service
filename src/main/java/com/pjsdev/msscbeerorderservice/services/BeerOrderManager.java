package com.pjsdev.msscbeerorderservice.services;

import com.pjsdev.msscbeerorderservice.domain.BeerOrder;

public interface BeerOrderManager {

    BeerOrder newBeerOrder(BeerOrder beerOrder);
}
