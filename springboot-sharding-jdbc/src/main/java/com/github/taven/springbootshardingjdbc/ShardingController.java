package com.github.taven.springbootshardingjdbc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sharding")
public class ShardingController {

    private final ShardingService shardingService;

    public ShardingController(ShardingService shardingService) {
        this.shardingService = shardingService;
    }

    @GetMapping("insertOrder")
    public String insertOrder(Long orderId, Long userId) {
        shardingService.insertOrder(orderId, userId);
        return "OK";
    }

}
