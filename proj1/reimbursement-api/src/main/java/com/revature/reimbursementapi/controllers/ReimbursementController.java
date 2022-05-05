package com.revature.reimbursementapi.controllers;

import com.revature.reimbursementapi.models.*;
import com.revature.reimbursementapi.repositories.EmployeeRepository;
import com.revature.reimbursementapi.repositories.ReimbursementRepository;
import com.revature.reimbursementapi.services.ReimbursementService;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("reimbursements")
public class ReimbursementController {

//    The following 2 lines are used for the restTemplate in order to accurately grab the request from the
//    reimbursement-api, and send back a response in the form of an email
//    @Value("${EMAIL_URL}")
//    private final String emailApiUrl;

    final Logger logger = LoggerFactory.getLogger(ReimbursementController.class);

    @Autowired
    ReimbursementService reimbursementService;

    /**
     * Setter injection but why? Look at the below block of code for explanation.
     */
    @Setter(onMethod = @__({@Autowired}))
    private ReimbursementRepository reimbursementRepository;

    /**
     * The following setter method is equivalent to the above block of code. First, the class recognizes all
     * the autowired classes and creates a bean for each reference variable. It will recognize this
     * reimbursementRepository reference variable, and when we create a setter for it, it will inject the
     * ReimbursementRepository interface into it (which will also inject the JpaRepository, and allow us to
     * access the database in our ReimbursementController).
     */
//    @Autowired
//    public void setReimbursementRepository(ReimbursementRepository reimbursementRepository) {
//        System.out.println("Setting Reimbursement Repository");
//        this.reimbursementRepository = reimbursementRepository;
//    }

    @Setter(onMethod = @__({@Autowired}))
    private EmployeeRepository employeeRepository;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Get all reimbursements that are stored in the reimbursements table
     * @return ResponseEntity - Ok when all reimbursements in database are found
     */
    @GetMapping
    public ResponseEntity getAllReimbursements() {
        logger.debug("Get all reimbursements: {}", reimbursementRepository.findAll());
        return ResponseEntity.ok(reimbursementRepository.findAll());
    }

    /**
     * Get reimbursement that are stored in the reimbursements table based on id provided in URI
     * @return ResponseEntity - Ok if reimbursement id exist in database; 404 not found if id does not exist
     */
    @GetMapping(path = "{id}")
    public ResponseEntity getReimbursementWithId(@PathVariable int id) {
        Reimbursement request = reimbursementRepository.findById(id);
        logger.debug("Get reimbursement with ID: {}", reimbursementRepository.findById(id));

        if(request == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(request);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Employee's Functionality

    /**
     * Get all reimbursements that are stored in the reimbursements table that has the employee id provided
     * @return ResponseEntity - Ok when all reimbursements with employee id are found
     */
    @GetMapping(path = "employees/{id}")
    public ResponseEntity getAllReimbursementsByEmployeeId(@PathVariable Integer id) {
        return ResponseEntity.ok(reimbursementRepository.findAllReimbursementsByEmployeeId(id));
    }

    /**
     * Create a reimbursement based on request body, and saves it to the database
     * @return ResponseEntity - Ok if reimbursement is created; 404 not found if employee id does not exist, and
     * no reimbursement will be created
     */
    @PostMapping(path = "employees")
    public ResponseEntity addReimbursement(@RequestBody ReimbursementDTO reimbursementDTO) {

        Employee employee = employeeRepository.findById(reimbursementDTO.getEmployeeId());

        if(employee == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
        }

        Reimbursement reimbursement = reimbursementService.createReimbursement(reimbursementDTO);
        logger.debug("Get new reimbursement: {}", reimbursement.toString());
        return ResponseEntity.ok().build();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Manager's Functionality

    /**
     * Approve a reimbursement and allow the user to switch the reimbursement's status to one of the three statuses
     * available in enum Status: PENDING, APPROVED, DECLINED
     * @return ResponseEntity - Ok if reimbursement's status is changed successfully; 400 bad request if
     * status or reimbursement id are invalid
     */
    @PostMapping(path = "managers/approval")
    public ResponseEntity reimbursementApproval(@RequestBody ApprovalDTO approvalDTO) {

        Reimbursement reimbursement = reimbursementRepository.findById(approvalDTO.getReimbursementId());

        if(reimbursement != null){
            if(Status.contains(approvalDTO.getItemStatus())){
                reimbursementService.approveReimbursement(approvalDTO, reimbursement);
                logger.debug("Get reimbursement with changed status: {}", reimbursement.getItemStatus());
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid status for reimbursement");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid reimbursement id provided");
    }

    /**
     * Reassign reimbursement that are stored in the reimbursement tables
     * @return ResponseEntity - Ok if reimbursement is successfully reassigned to another employee;
     * 400 bad request if employee id or reimbursement id are invalid
     */
    @PostMapping(path = "managers/reassign")
    public ResponseEntity reimbursementReassign(@RequestBody ReassignDTO reassignDTO) {

        Reimbursement reimbursement = reimbursementRepository.findById(reassignDTO.getReimbursementId());
        Employee newEmployee = employeeRepository.findById(reassignDTO.getNewEmployeeId());

        if(reimbursement != null){
            if(newEmployee != null){
                reimbursementService.reassignReimbursement(reimbursement, newEmployee);
                logger.debug("Get new reimbursement: {}", reimbursement.getEmployee());
                return ResponseEntity.ok().body("Reimbursement is now successfully reassigned to: "+newEmployee.getName());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid employee id provided");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid reimbursement id provided");
    }

    /**
     * Delete reimbursement that are stored in the reimbursements tables
     * @return ResponseEntity - Ok if reimbursement is successfully deleted based on reimbursement id
     */
    @DeleteMapping(path = "{reimbursementId}")
    public ResponseEntity deleteReimbursement(@PathVariable int reimbursementId){
        reimbursementRepository.deleteById(reimbursementId);
        return ResponseEntity.ok().body("Delete successful");
    }
}
