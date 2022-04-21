package com.pjsdev.msscbeerorderservice.services.testcomponents;

import com.pjsdev.brewery.model.events.AllocateOrderRequest;
import com.pjsdev.brewery.model.events.AllocateOrderResult;
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
public class BeerOrderAllocationListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_QUEUE)
    public void listen(Message msg) {
        AllocateOrderRequest request = (AllocateOrderRequest) msg.getPayload();
        boolean pendingInventory = false;
        boolean allocationError = false;

        if (Objects.equals(request.getBeerOrder().getCustomerRef(), "partial-allocation")) {
            pendingInventory = true;
        }

        if (Objects.equals(request.getBeerOrder().getCustomerRef(), "fail-allocation")) {
            allocationError = true;
        }

        boolean finalPendingInventory = pendingInventory;
        request.getBeerOrder().getBeerOrderLines().forEach(beerOrderLineDto -> {
            if (finalPendingInventory) {
                beerOrderLineDto.setQuantityAllocated(beerOrderLineDto.getOrderQuantity() - 1);
            } else {
                beerOrderLineDto.setQuantityAllocated(beerOrderLineDto.getOrderQuantity());
            }
        });

        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_RESPONSE_QUEUE,
                AllocateOrderResult.builder().beerOrder(request.getBeerOrder())
                        .pendingInventory(pendingInventory).allocationError(allocationError).build());
    }
}
