package com.pjsdev.msscbeerorderservice.services.listeners;

import com.pjsdev.brewery.model.events.AllocateOrderResult;
import com.pjsdev.msscbeerorderservice.config.JmsConfig;
import com.pjsdev.msscbeerorderservice.services.BeerOrderManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderAllocationResultListener {
    private BeerOrderManager beerOrderManager;

    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_RESPONSE_QUEUE)
    public void listen(AllocateOrderResult result) {
        if (!result.getAllocationError() && !result.getPendingInventory()) {
            //allocated normally
            beerOrderManager.beerOrderAllocationPassed(result.getBeerOrder());
        } else if (!result.getAllocationError() && result.getPendingInventory()) {
            //pending inventory
            beerOrderManager.beerOrderAllocationPendingInventory(result.getBeerOrder());
        } else if (result.getAllocationError()) {
            //allocation error
            beerOrderManager.beerOrderAllocationFailed(result.getBeerOrder());
        }
    }
}
