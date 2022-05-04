package com.revature.reimbursementapi.models;

import lombok.*;

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
