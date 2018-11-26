package com.gitee.taven.dto;

import com.gitee.taven.validate.annotation.CaseMode;
import com.gitee.taven.validate.annotation.CheckCase;

public class CustomDTO {

    @CheckCase(value = CaseMode.UPPER)
    private String word;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
