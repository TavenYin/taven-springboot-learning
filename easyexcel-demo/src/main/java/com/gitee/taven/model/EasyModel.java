package com.gitee.taven.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.regex.Pattern;

public class EasyModel extends BaseRowModel {

    public EasyModel() {
        super();
    }

    @ExcelProperty(value  = {"姓名"}, index = 0)
    private String name;

    @ExcelProperty(value  = {"年龄"}, index = 1)
    private Integer age;

    @ExcelProperty(value  = {"出生日期"}, index = 2, format = "yyyy/MM/dd")
    private Date birthday;

    @ExcelProperty(value  = {"余额"}, index = 3)
    private BigDecimal balance;

    public EasyModel(String name, Integer age, Date birthday, BigDecimal balance) {
        this.name = name;
        this.age = age;
        this.birthday = birthday;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "EasyModel{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                ", balance=" + balance +
                '}';
    }

    public static void main(String[] args) {
        String str = "333";
        Pattern pattern = Pattern.compile("[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*");
        System.out.println( pattern.matcher(str).matches());
    }

}
