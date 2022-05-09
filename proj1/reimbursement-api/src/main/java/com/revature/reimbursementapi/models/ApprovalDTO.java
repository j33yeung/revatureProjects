package com.revature.reimbursementapi.models;

import lombok.*;


/**
 * DTO that is simply used to retrieve the request body for reimbursement approval
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApprovalDTO {

    private int reimbursementId;
    private Status itemStatus;

}
