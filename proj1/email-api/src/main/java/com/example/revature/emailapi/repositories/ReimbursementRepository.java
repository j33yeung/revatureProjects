package com.example.revature.emailapi.repositories;

import com.example.revature.emailapi.models.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Reimbursement Repository that access the reimbursements table in ReimbursementAPI Database
 * The JpaRepository allows us to create our own methods, and return whatever object type of our choosing
 * Ex. findById will take in an int id, find the Reimbursement associated to that id,
 * and return type Reimbursement
 */
@Repository
public interface ReimbursementRepository extends JpaRepository<Reimbursement, Integer> {

    Reimbursement findById(@Param("id") int id);

}
