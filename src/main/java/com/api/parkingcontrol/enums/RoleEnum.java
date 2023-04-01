package com.api.parkingcontrol.enums;

import static java.util.Objects.isNull;

public enum RoleEnum {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER");


    private final String role;

    public String getRole() {
        return this.role;
    }

    RoleEnum(String role) {
        this.role = role;
    }

    public static RoleEnum getRoleEnum(String role) {
        if (!isNull(role) && !role.isBlank()) {
            for (RoleEnum roleEnum : RoleEnum.values()) {
                if (roleEnum.getRole().equals(role.toUpperCase())) {
                    return  roleEnum;
                }
            }
        }
        return null;
    }
}
