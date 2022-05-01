package com.revature.reimbursementapi.repositories;

import com.revature.reimbursementapi.models.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReimbursementRepository extends JpaRepository<Reimbursement, Integer> {
}
