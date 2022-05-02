package com.revature.reimbursementapi.repositories;

import com.revature.reimbursementapi.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findById(@Param("id") int id);

}
