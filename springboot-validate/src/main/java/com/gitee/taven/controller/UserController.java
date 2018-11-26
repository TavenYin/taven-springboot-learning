package com.gitee.taven.controller;

import com.gitee.taven.dto.CardDTO;
import com.gitee.taven.dto.CustomDTO;
import com.gitee.taven.dto.GroupCardDTO;
import com.gitee.taven.dto.UserDTO;
import com.gitee.taven.validate.group.Insert;
import com.gitee.taven.validate.group.Update;
import com.gitee.taven.validate.ValidList;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    @PostMapping("simple")
    public Object simple(@RequestBody @Valid CardDTO cardDTO) {
        return cardDTO;
    }

    @PostMapping("nested")
    public Object nested(@RequestBody @Valid UserDTO userDTO) {
        return userDTO;
    }

    @PostMapping("card_list")
    public Object card_list(@RequestBody @Valid ValidList<CardDTO> cardList) {
        return cardList;
    }

    @PostMapping("user_card_list")
    public Object user_card_list(@RequestBody @Valid ValidList<UserDTO> cardList) {
        return cardList;
    }

    @PostMapping("insert_card")
    public Object insert_card(@RequestBody @Validated(Insert.class) GroupCardDTO card){
        return card;
    }

    @PostMapping("custom")
    public Object custom(@RequestBody @Validated CustomDTO customDTO){
        return customDTO;
    }

}
