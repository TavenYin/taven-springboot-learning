package com.github.taven.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SenderController {

    @Autowired
    private SenderService senderService;

    @GetMapping("send")
    public Object send(Integer receiverId, String msg) {
        senderService.send(receiverId, msg);
        return "OK";
    }

}
