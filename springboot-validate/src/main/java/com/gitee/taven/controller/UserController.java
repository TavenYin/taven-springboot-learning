package com.gitee.taven.controller;

import com.gitee.taven.dto.CardDTO;
import com.gitee.taven.dto.UserDTO;
import com.gitee.taven.validate.ValidateList;
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

    @PostMapping("list")
    public Object list(@RequestBody @Valid ValidateList<UserDTO> userDTOList) {
        return userDTOList;
    }

}
