package com.pjsdev.msscbeerorderservice.services;

import com.pjsdev.brewery.model.CustomerPagedList;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    CustomerPagedList listCustomers(Pageable pageable);
}
