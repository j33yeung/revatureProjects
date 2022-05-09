package com.revature.reimbursementapi.models;

import lombok.*;

import javax.persistence.*;

/**
 * Employee class that will have a name and email associated to it
 */
@Entity
@Table(name="employees")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @Column(name="id", columnDefinition = "AUTO_INCREMENT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="_name")
    private String name;
//    private boolean isEmployee; // true if employee, false if manager

    @Column(name="email", unique = true)
    private String email;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name="reimbursement_id", updatable = false, insertable = false)
//    private int reimbursement_id;

//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name="manager_id", updatable = false, insertable = false)
//    private int manager_id;

}
