package com.example.revature.emailapi.models;

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
@Builder
public class Employee {

    @Id
    @Column(name="id", columnDefinition = "AUTO_INCREMENT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="_name")
    private String name;

    @Column(name="email", unique = true)
    private String email;
}
