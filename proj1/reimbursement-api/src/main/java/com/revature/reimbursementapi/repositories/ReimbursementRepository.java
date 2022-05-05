package com.revature.reimbursementapi.repositories;

import com.revature.reimbursementapi.models.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Reimbursement Repository that access the Reimbursement table in ReimbursementAPI Database
 * The JpaRepository allows us to create our own methods, and return whatever object type of our choosing
 * Ex. findByItemName will take in a String itemName, find the Reimbursement associated to that id,
 * and return type Reimbursement
 */
@Repository
public interface ReimbursementRepository extends JpaRepository<Reimbursement, Integer> {

    /**
     * Finds all reimbursements associated with employee's ID in table "reimbursements"
     * @param id - the employee's ID
     * @return the list of reimbursements associated with this employee's id
     */
    List<Reimbursement> findAllReimbursementsByEmployeeId(@Param("id") int id);

    Reimbursement findById(@Param("id") int id);

    Reimbursement findByItemName(@Param("item_name") String itemName);

    @Modifying
    @Query(value = "DELETE FROM reimbursements WHERE id = ?1", nativeQuery = true)
    void deleteById(int id);

}
