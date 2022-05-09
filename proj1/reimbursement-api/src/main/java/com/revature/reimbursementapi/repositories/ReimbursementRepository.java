package com.revature.reimbursementapi.repositories;

import com.revature.reimbursementapi.models.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Reimbursement Repository that access the reimbursements table in ReimbursementAPI Database
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

    /*
    @Modifying -- this annotation is used so @Query can use not only select, but insert, update, and delete as well
    @Query(value = "DELETE FROM reimbursements WHERE id = ?1", nativeQuery = true)
    void deleteById(int id);

    Bug fixed: No longer need this query because the problem was the way employee_id from reimbursements table is attached
    to id from employees table by a foreign key. This Many-to-One relationship was set with cascade = CascadeType.ALL,
    and fetch = fetch.EAGER. The cascade type was causing not only the reimbursement to be deleted, but also the employee.
    The cascade was deleted, and the problem was fixed.
     */





}
