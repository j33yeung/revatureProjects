package com.revature.reimbursementapi.models;

import lombok.ToString;

@ToString
public enum Status {
    PENDING, APPROVED, DECLINED;

    public static boolean contains(Status enumValue) {

        for (Status c : Status.values()) {
            if (c.equals(enumValue)) {
                return true;
            }
        }

        return false;
    }
}
