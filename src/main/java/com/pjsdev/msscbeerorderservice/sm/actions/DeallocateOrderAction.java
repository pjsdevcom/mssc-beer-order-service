package com.pjsdev.msscbeerorderservice.sm.actions;

import com.pjsdev.brewery.model.events.DeallocateOrderRequest;
import com.pjsdev.msscbeerorderservice.config.JmsConfig;
import com.pjsdev.msscbeerorderservice.domain.BeerOrder;
import com.pjsdev.msscbeerorderservice.domain.BeerOrderEventEnum;
import com.pjsdev.msscbeerorderservice.domain.BeerOrderStatusEnum;
import com.pjsdev.msscbeerorderservice.repositories.BeerOrderRepository;
import com.pjsdev.msscbeerorderservice.services.BeerOrderManagerImpl;
import com.pjsdev.msscbeerorderservice.web.mappers.BeerOrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class DeallocateOrderAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final JmsTemplate jmsTemplate;
    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderMapper beerOrderMapper;

    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> stateContext) {

        String beerOrderId = (String) stateContext.getMessage().getHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);
        BeerOrder beerOrder = beerOrderRepository.findOneById(UUID.fromString(beerOrderId));

        jmsTemplate.convertAndSend(JmsConfig.DEALLOCATE_ORDER_QUEUE,
                DeallocateOrderRequest.builder().beerOrderDto(beerOrderMapper.beerOrderToDto(beerOrder)).build());

        log.debug("Sent Deallocation Request for order ID: " + beerOrderId);
    }
}