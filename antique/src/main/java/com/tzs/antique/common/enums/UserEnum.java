package com.tzs.antique.common.enums;

import lombok.Getter;

@Getter
public enum UserEnum {

    STATUS_NORMAL(0, "正常"),
            ;
    private Integer code;

    private String message;

    UserEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
