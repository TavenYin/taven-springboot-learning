package com.github.taven.controller;

import com.github.taven.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    @RequestMapping("buy")
    public String buy(Integer goodsId, Integer quantity, boolean testRollback, boolean isSleep) {
        businessService.buy(goodsId, quantity, testRollback, isSleep);
        return "success";
    }

}
