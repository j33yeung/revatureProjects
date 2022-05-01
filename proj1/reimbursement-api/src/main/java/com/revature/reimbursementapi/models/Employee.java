package com.revature.reimbursementapi.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="employees")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @Column(name="id", columnDefinition = "AUTO_INCREMENT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
//    private boolean isEmployee; // true if employee, false if manager

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Column(name="email", unique = true)
    private String email;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="manager_id", updatable = false, insertable = false)
    private int manager_id;

}
