package com.example.jwtdemo.domain.model.enums;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author liuyiqian
 */
public enum Role implements BaseEnum {
    ADMIN(1, "admin"),
    USER(2, "user");

    private int value;

    private String label;

    Role(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public static Role valueOf(Integer value) {
        Role[] enums = values();
        for (Role role : enums) {
            if (role.value == value) {
                return role;
            }
        }
        throw new IllegalArgumentException("Illegal value");
    }

    @Override
    public int getEnumValue() {
        return this.value;
    }

    public String getLabel() {
        return this.label;
    }

    /**
     * get ordered values by order
     *
     * @return to generate select input
     */
    public static Role[] getRoleValues() {
        Role[] enums = values();
        Arrays.sort(enums, Comparator.comparingInt(Role::getEnumValue));
        return enums;
    }
}
