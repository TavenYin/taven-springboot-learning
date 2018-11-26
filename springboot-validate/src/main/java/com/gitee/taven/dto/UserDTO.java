package com.gitee.taven.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

public class UserDTO {

    @NotBlank
    private String userId;

    @NotBlank
    private String username;

    private String password;

    @Valid
    private List<CardDTO> cardList;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<CardDTO> getCardList() {
        return cardList;
    }

    public void setCardList(List<CardDTO> cardList) {
        this.cardList = cardList;
    }

}
