package com.revature.reimbursementapi.models;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReimbursementDTO {

    private String itemName;
    private Status itemStatus;
    private BigDecimal expenditure;
    private LocalDate date;
    private int employeeId;
    private String itemDescriptor;

}
