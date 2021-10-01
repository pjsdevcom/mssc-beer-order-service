package com.pjsdev.brewery.model.events;

import com.pjsdev.brewery.model.BeerOrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidateOrderRequest {
    private BeerOrderDto beerOrder;
}
