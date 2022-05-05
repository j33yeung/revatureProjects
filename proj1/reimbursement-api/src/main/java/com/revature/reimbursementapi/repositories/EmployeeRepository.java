package com.revature.reimbursementapi.repositories;

import com.revature.reimbursementapi.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Employee Repository that access the Employee table in ReimbursementAPI Database
 * The JpaRepository allows us to create our own methods, and return whatever object type of our choosing
 * Ex. findById will take in an int id, find the Employee associated to that id, and return type Employee
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findById(@Param("id") int id);

}
