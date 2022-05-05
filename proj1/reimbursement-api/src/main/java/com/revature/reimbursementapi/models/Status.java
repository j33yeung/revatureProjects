package com.revature.reimbursementapi.models;

import lombok.ToString;

/**
 * This enum states that the status of a reimbursement can only be one of three states: PENDING, APPROVED, or DECLINED
 */
@ToString
public enum Status {
    PENDING, APPROVED, DECLINED;

    /**
     * This static method can be used in order to verify if a reimbursement's status contains one of the three states.
     * Therefore, if a reimbursement has a null Status for whatever reason, this method can be used for a null check.
     */
    public static boolean contains(Status enumValue) {

        for (Status c : Status.values()) {
            if (c.equals(enumValue)) {
                return true;
            }
        }
        return false;
    }
}
