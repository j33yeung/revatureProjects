package com.example.revature.emailapi.models;

import lombok.ToString;

@ToString
public enum Status {
    PENDING, APPROVED, DECLINED;

    public static boolean contains(String enumValue) {

        for (Status c : Status.values()) {
            if ((c.name()).equals(enumValue)) {
                return true;
            }
        }

        return false;
    }
}
