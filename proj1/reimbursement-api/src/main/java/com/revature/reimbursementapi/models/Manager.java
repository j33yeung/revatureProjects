//package com.revature.reimbursementapi.models;
//
//import lombok.*;
//
//import javax.persistence.*;
//
/**
 * Manager class was never implemented into this code. But in theory, employees will only be able to submit and see
 * their personal reimbursements, whereas managers will be able to view all reimbursements and reassign reimbursements
 * to another employee. In order to implement this theory, a login system needs to be added to differentiate
 * the two users.
 */
//@Entity
//@Table(name="managers")
//@Getter
//@Setter
//@ToString
//@EqualsAndHashCode
//@NoArgsConstructor
//@AllArgsConstructor
//public class Manager {
//
//    @Id
//    @Column(name="id", columnDefinition = "AUTO_INCREMENT")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    private String name;
////    private boolean isEmployee; // true if employee, false if manager
//
//    @Column(name="email", unique = true)
//    private String email;
//
//}
