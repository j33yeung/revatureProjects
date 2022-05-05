package com.example.revature.emailapi.repositories;

import com.example.revature.emailapi.models.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Email Repository that access the emails table in ReimbursementAPI Database
 * The JpaRepository allows us to create our own methods, and return whatever object type of our choosing
 * Ex. findAllByEmailAddress will take in a string emailAddress, find the Employee associated to that email address,
 * and return type Email
 */
@Repository
public interface EmailRepository extends JpaRepository<Email, Integer> {

    Email findById(@Param("id") int id);

    List<Email> findAllByEmailAddress(@Param("email_address") String emailAddress);
}
