package com.revature.reimbursementapi.repositories;

import com.revature.reimbursementapi.models.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReimbursementRepository extends JpaRepository<Reimbursement, Integer> {

    /**
     * Finds all reimbursements associated with employee's ID in table "reimbursements"
     * @param id - the employee's ID
     * @return the list of reimbursements associated with this employee's id
     */
    List<Reimbursement> findAllReimbursementsByEmployeeId(@Param("id") int id);

}
