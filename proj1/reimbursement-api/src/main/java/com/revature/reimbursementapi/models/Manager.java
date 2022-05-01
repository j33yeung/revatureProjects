package com.revature.reimbursementapi.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="managers")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Manager {

    @Id
    @Column(name="id", columnDefinition = "AUTO_INCREMENT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
//    private boolean isEmployee; // true if employee, false if manager

    @Column(name="email", unique = true)
    private String email;

}
