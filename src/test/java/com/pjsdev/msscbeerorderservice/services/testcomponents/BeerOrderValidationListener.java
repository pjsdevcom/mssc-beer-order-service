package com.pjsdev.msscbeerorderservice.services.testcomponents;

import com.pjsdev.brewery.model.events.ValidateOrderRequest;
import com.pjsdev.brewery.model.events.ValidateOrderResult;
import com.pjsdev.msscbeerorderservice.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderValidationListener {
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE)
    public void list(Message msg) {

        boolean isValid = true;

        ValidateOrderRequest request = (ValidateOrderRequest) msg.getPayload();

        // Condition to fail validation
        if (Objects.equals(request.getBeerOrder().getCustomerRef(), "fail-validation")) {
            isValid = false;
        }

        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE,
                ValidateOrderResult.builder().isValid(isValid).orderId(request.getBeerOrder().getId()).build());
    }
}
