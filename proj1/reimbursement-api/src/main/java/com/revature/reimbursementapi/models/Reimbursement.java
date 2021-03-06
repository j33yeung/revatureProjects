package com.revature.reimbursementapi.models;

import lombok.*;
import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Reimbursement class that will have a name, status, expenditure, date, and an employee associated to it. It also
 * includes an item description of what the reimbursement item is.
 */
@Entity
@Table(name="reimbursements")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Check(constraints = "expenditure >= 0")
@Builder
public class Reimbursement {

    @Id
    @Column(name="id", columnDefinition = "AUTO_INCREMENT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="item_name")
    private String itemName;

    @Enumerated(EnumType.STRING)
    @Column(name="item_status")
    private Status itemStatus; //(there are only three available status - pending, approved, declined)

    private BigDecimal expenditure;

    @Column(name="_date")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="employee_id", referencedColumnName = "id")
    private Employee employee;

    @Column(name="item_descriptor")
    private String itemDescriptor;

}
