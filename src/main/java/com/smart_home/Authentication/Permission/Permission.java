package com.smart_home.Authentication.Permission;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    USER_READ("changer:get"),
    USER_UPDATE("changer:update"),
    USER_CREATE("changer:create"),
    USER_DELETE("changer:delete"),

    ;
    @Getter
    private final String permission;
}
