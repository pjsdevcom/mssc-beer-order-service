package com.pjsdev.msscbeerorderservice.web.mappers;

import com.pjsdev.msscbeerorderservice.domain.BeerOrder;
import com.pjsdev.msscbeerorderservice.web.model.BeerOrderDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class, BeerOrderLineMapper.class})
public interface BeerOrderMapper {

    BeerOrderDto beerOrderToDto(BeerOrder beerOrder);

    BeerOrder dtoToBeerOrder(BeerOrderDto dto);
}
