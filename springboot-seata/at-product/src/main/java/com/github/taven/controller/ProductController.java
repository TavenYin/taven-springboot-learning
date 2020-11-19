package com.github.taven.controller;

import com.github.taven.service.ProductService;
import io.seata.core.context.RootContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product/deduct")
    public void deduct(String xid, Integer goodsId, Integer quantity) {
        RootContext.bind(xid);
        productService.deduct(goodsId, quantity);
    }

}
