package com.revature.reimbursementapi.controllers;

import com.revature.reimbursementapi.models.ReimbursementDTO;
import com.revature.reimbursementapi.models.Reimbursement;
import com.revature.reimbursementapi.repositories.EmployeeRepository;
import com.revature.reimbursementapi.repositories.ReimbursementRepository;
import com.revature.reimbursementapi.services.ReimbursementService;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("reimbursements")
public class ReimbursementController {

    final Logger logger = LoggerFactory.getLogger(ReimbursementController.class);

    @Setter(onMethod = @__({@Autowired}))
    private ReimbursementRepository reimbursementRepository;

    @Autowired
    ReimbursementService reimbursementService;

    @Setter(onMethod = @__({@Autowired}))
    private EmployeeRepository employeeRepository;

    @GetMapping
    public ResponseEntity getAllReimbursements() {
        return ResponseEntity.ok(reimbursementRepository.findAll());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity getReimbursementWithId(@PathVariable Integer id) {
        logger.debug("Get reimbursement with ID: {}", reimbursementRepository.findById(id));
        Optional<Reimbursement> request = reimbursementRepository.findById(id);

        if(request.isPresent()) {
            return ResponseEntity.ok(request);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(path = "/employees/{id}")
    public ResponseEntity getAllReimbursementsByEmployeeId(@PathVariable Integer id) {
        return ResponseEntity.ok(reimbursementRepository.findAllReimbursementsByEmployeeId(id));
    }

//    @PostMapping
//    public ResponseEntity addReimbursement(@RequestBody Reimbursement request) {
//        try {
//            if(reimbursementService.saveReimbursement(request)){
//                return ResponseEntity.created(new URI("http://localhost:8080/reimbursements/"+request.getId())).build();
//            }
//            else{
//                throw new Exception();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.internalServerError().body("Error saving new reimbursement request");
//        }
//    }

    @PostMapping
    public void addReimbursement(@RequestBody ReimbursementDTO reimbursementDTO) {
        reimbursementService.createReimbursement(reimbursementDTO);
    }
}
