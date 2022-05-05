package com.example.revature.emailapi.models;

import lombok.*;

/**
 * DTO that is simply used to retrieve the reimbursement id from request body for email creation
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailDTO {

    private int reimbursementId;
}
