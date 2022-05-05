package com.example.revature.emailapi.models;

import lombok.ToString;

/**
 * This enum states that the status of a reimbursement can only be one of three states: PENDING, APPROVED, or DECLINED
 */
@ToString
public enum Status {
    PENDING, APPROVED, DECLINED;
}
