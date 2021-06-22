package com.pjsdev.msscbeerorderservice.web.mappers;

import com.pjsdev.msscbeerorderservice.domain.BeerOrderLine;
import com.pjsdev.msscbeerorderservice.web.model.BeerOrderLineDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerOrderLineMapper {

    BeerOrderLineDto beerOrderLineToDto(BeerOrderLine line);

    BeerOrderLine dtoToBeerOrderLine(BeerOrderLineDto dto);
}