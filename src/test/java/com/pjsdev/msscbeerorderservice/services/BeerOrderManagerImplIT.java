package com.pjsdev.msscbeerorderservice.services;

import com.pjsdev.msscbeerorderservice.domain.BeerOrder;
import com.pjsdev.msscbeerorderservice.domain.BeerOrderLine;
import com.pjsdev.msscbeerorderservice.domain.BeerOrderStatusEnum;
import com.pjsdev.msscbeerorderservice.domain.Customer;
import com.pjsdev.msscbeerorderservice.repositories.BeerOrderRepository;
import com.pjsdev.msscbeerorderservice.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class BeerOrderManagerImplIT {

    @Autowired
    BeerOrderManager beerOrderManager;

    @Autowired
    BeerOrderRepository beerOrderRepository;

    @Autowired
    CustomerRepository customerRepository;

    Customer testCustomer;

    UUID beerId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        testCustomer = customerRepository.save(Customer.builder().customerName("John The Tester").build());
    }

    @Test
    void testNewToAllocated() {
        BeerOrder beerOrder = createBeerOrder();

        BeerOrder savedBeerOrder = beerOrderManager.newBeerOrder(beerOrder);

        assertNotNull(savedBeerOrder);
        assertEquals(BeerOrderStatusEnum.ALLOCATED, savedBeerOrder.getOrderStatus());
    }

    public BeerOrder createBeerOrder() {
        BeerOrder beerOrder = BeerOrder.builder().customer(testCustomer).build();

        Set<BeerOrderLine> lines = new HashSet<>();
        lines.add(BeerOrderLine.builder().beerId(beerId).orderQuantity(1).beerOrder(beerOrder).build());

        beerOrder.setBeerOrderLines(lines);

        return beerOrder;
    }
}
