package com.github.taven.controller;

import com.github.taven.service.OrderService;
import io.seata.core.context.RootContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("createOrder")
    public void createOrder(String xid, Integer goodsId, Integer quantity, boolean testRollback) {
        RootContext.bind(xid);
        orderService.createOrder(goodsId, quantity, testRollback);
    }

}
