package com.gitee.taven.dto;

import com.gitee.taven.validate.annotation.CaseMode;
import com.gitee.taven.validate.annotation.CheckCase;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.util.Date;


public class CardDTO {

    @NotBlank
    private String cardId;

    @Size(min = 10, max = 10)
    @NotNull
    private String cardNum; // 卡号

    @Past
    @NotNull
    private Date createDate;

    @Range(max = 3)
    private String cardType;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
}
