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

import java.util.Optional;

@RestController
@RequestMapping("reimbursements")
public class ReimbursementController {

//    @Value("${EMAIL_URL}")
//    private final String emailApiUrl;

    final Logger logger = LoggerFactory.getLogger(ReimbursementController.class);

    @Autowired
    ReimbursementService reimbursementService;

    @Setter(onMethod = @__({@Autowired}))
    private ReimbursementRepository reimbursementRepository;

    @Setter(onMethod = @__({@Autowired}))
    private EmployeeRepository employeeRepository;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping
    public ResponseEntity getAllReimbursements() {
        return ResponseEntity.ok(reimbursementRepository.findAll());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity getReimbursementWithId(@PathVariable int id) {
        logger.debug("Get reimbursement with ID: {}", reimbursementRepository.findById(id));
        Reimbursement request = reimbursementRepository.findById(id);

        if(request == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(request);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Employee's Functionality

    @GetMapping(path = "employees/{id}")
    public ResponseEntity getAllReimbursementsByEmployeeId(@PathVariable Integer id) {
        return ResponseEntity.ok(reimbursementRepository.findAllReimbursementsByEmployeeId(id));
    }

    @PostMapping(path = "employees")
    public ResponseEntity addReimbursement(@RequestBody ReimbursementDTO reimbursementDTO) {

        Employee employee = employeeRepository.findById(reimbursementDTO.getEmployeeId());

        if(employee == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
        }

        reimbursementService.createReimbursement(reimbursementDTO);
        return ResponseEntity.ok().build();

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Manager's Functionality

    @PostMapping(path = "managers/approval")
    public ResponseEntity reimbursementApproval(@RequestBody ApprovalDTO approvalDTO) {

        Reimbursement reimbursement = reimbursementRepository.findById(approvalDTO.getReimbursementId());

        if(reimbursement != null){
            if(Status.contains(approvalDTO.getItemStatus())){
                reimbursementService.approveReimbursement(approvalDTO);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid status for reimbursement");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid reimbursement id provided");
    }

    @PostMapping(path = "managers/reassign")
    public ResponseEntity reimbursementReassign(@RequestBody ReassignDTO reassignDTO) {

        Reimbursement reimbursement = reimbursementRepository.findById(reassignDTO.getReimbursementId());
        Employee newEmployee = employeeRepository.findById(reassignDTO.getNewEmployeeId());

        if(reimbursement != null){
            if(newEmployee != null){
                reimbursementService.reassignReimbursement(reassignDTO);
                return ResponseEntity.ok().body("Reimbursement is now successfully reassigned to: "+newEmployee.getName());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid employee id provided");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid reimbursement id provided");
    }

    @DeleteMapping(path = "{reimbursementId}")
    public ResponseEntity deleteReimbursement(@PathVariable int reimbursementId){
        reimbursementRepository.deleteById(reimbursementId);
        return ResponseEntity.ok().body("Delete successful");
    }

}
