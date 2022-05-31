package com.pjsdev.msscbeerorderservice.web.mappers;

import com.pjsdev.brewery.model.CustomerDto;
import com.pjsdev.msscbeerorderservice.domain.Customer;
import org.mapstruct.Mapper;

@Mapper(uses = DateMapper.class)
public interface CustomerMapper {
    CustomerDto customerToDto(Customer customer);

    Customer dtoToCustomer(CustomerDto customerDto);
}
