package com.revature.reimbursementapi.models;

import lombok.*;
import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="reimbursement")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Check(constraints = "expenditure >= 0")
public class Reimbursement implements Serializable {

    @Id
    @Column(name="id", columnDefinition = "AUTO_INCREMENT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="item_name")
    private String itemName;

    @Column(name="item_status")
    private Status itemStatus; //(there are only three available status - pending, approved, declined)

    private BigDecimal expenditure;
    private Date date;

    @Column(name="item_descriptor")
    private String itemDescriptor;

}
