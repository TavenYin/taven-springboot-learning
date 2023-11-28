package com.github.taven.springboot3ebean.domain;

import io.ebean.annotation.DbEnumValue;

public enum UserStatus {

    ACTIVE(1),
    INACTIVE(2);

    Integer dbValue;
    UserStatus(Integer dbValue) {
        this.dbValue = dbValue;
    }

    // annotate a method that returns the value
    // in the DB that the enum element maps to
    @DbEnumValue
    public Integer getValue() {
        return dbValue;
    }

}
