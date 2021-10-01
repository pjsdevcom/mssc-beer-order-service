package com.pjsdev.msscbeerorderservice.sm.actions;

import com.pjsdev.brewery.model.events.ValidateOrderRequest;
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
@Component
@RequiredArgsConstructor
public class ValidateOrderAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderMapper beerOrderMapper;
    private final JmsTemplate jmsTemplate;

    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {
        String beerOrderId = (String) context.getMessage().getHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);
        BeerOrder beerOrder = beerOrderRepository.findOneById(UUID.fromString(beerOrderId));

        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_QUEUE,
                ValidateOrderRequest.builder().beerOrder(beerOrderMapper.beerOrderToDto(beerOrder)).build());

        log.debug("Sent validation request to queue for order id " + beerOrderId);
    }
}
