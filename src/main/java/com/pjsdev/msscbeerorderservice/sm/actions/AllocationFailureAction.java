package com.pjsdev.msscbeerorderservice.sm.actions;

import com.pjsdev.brewery.model.events.AllocationFailureEvent;
import com.pjsdev.msscbeerorderservice.config.JmsConfig;
import com.pjsdev.msscbeerorderservice.domain.BeerOrderEventEnum;
import com.pjsdev.msscbeerorderservice.domain.BeerOrderStatusEnum;
import com.pjsdev.msscbeerorderservice.services.BeerOrderManagerImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AllocationFailureAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final JmsTemplate jmsTemplate;

    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {
        String beerOrderId = (String) context.getMessage().getHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);

        jmsTemplate.convertAndSend(JmsConfig.ALLOCATION_FAILURE_QUEUE,
                AllocationFailureEvent.builder().orderId(UUID.fromString(beerOrderId)).build());

        log.debug("Sent allocation failure message to queue for order id " + beerOrderId);
    }
}
