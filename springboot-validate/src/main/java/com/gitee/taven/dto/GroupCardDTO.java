package com.gitee.taven.dto;

import com.gitee.taven.validate.group.Insert;
import com.gitee.taven.validate.group.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class GroupCardDTO {

    @NotBlank(groups = {Update.class})
    private String id;

    @NotBlank(groups = {Insert.class})
    private String cardNum;

    @NotNull(groups = {Insert.class, Update.class})
    private Integer cardType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }
}
