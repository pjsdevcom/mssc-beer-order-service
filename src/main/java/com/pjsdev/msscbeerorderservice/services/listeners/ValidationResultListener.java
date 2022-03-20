package com.pjsdev.msscbeerorderservice.services.listeners;

import com.pjsdev.brewery.model.events.ValidateOrderResult;
import com.pjsdev.msscbeerorderservice.config.JmsConfig;
import com.pjsdev.msscbeerorderservice.services.BeerOrderManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class ValidationResultListener {

    private final BeerOrderManager beerOrderManager;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE)
    public void listen(ValidateOrderResult result) {
        final UUID beerOrderId = result.getOrderId();

        log.debug("Validation result for order ID: " + beerOrderId);

        beerOrderManager.processValidationResult(beerOrderId, result.getIsValid());
    }
}
