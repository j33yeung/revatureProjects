package com.revature.reimbursementapi.models;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalDTO {

    private int reimbursementId;
    private String itemStatus;

}
