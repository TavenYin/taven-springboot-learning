package com.gitee.taven.uninumber.mapper;

import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface OrderMapper {

    int createOrderNum(Map<String, String> parameterMap);

}
