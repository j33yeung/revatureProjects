package com.example.revature.emailapi.repositories;

import com.example.revature.emailapi.models.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReimbursementRepository extends JpaRepository<Reimbursement, Integer> {

    Reimbursement findById(@Param("id") int id);

}
