package com.revature.reimbursementapi.models;

import lombok.*;

/**
 * DTO that is simply used to retrieve the request body for reimbursement reassignment to another employee
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReassignDTO {

    private int reimbursementId;
    private int newEmployeeId;

}
