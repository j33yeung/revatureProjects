package com.example.revature.emailapi.repositories;

import com.example.revature.emailapi.models.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailRepository extends JpaRepository<Email, Integer> {

    Email findById(@Param("id") int id);

    List<Email> findAllByEmailAddress(@Param("email_address") String emailAddress);
}
