package com.example.revature.emailapi.models;

import lombok.*;

import javax.persistence.*;

/**
 * Email class that will have email address and email body message associated to it
 */
@Entity
@Table(name="emails")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Email {

    @Id
    @Column(name="id", columnDefinition = "AUTO_INCREMENT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int emailId;

    @Column(name="email_address")
    private String emailAddress;

    @Column(name="email_body")
    private String emailBody;

}
