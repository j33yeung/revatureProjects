package com.example.revature.emailapi.controllers;

import com.example.revature.emailapi.models.Email;
import com.example.revature.emailapi.models.EmailDTO;
import com.example.revature.emailapi.models.Reimbursement;
import com.example.revature.emailapi.repositories.EmailRepository;
import com.example.revature.emailapi.repositories.ReimbursementRepository;
import com.example.revature.emailapi.services.EmailService;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("emails")
public class EmailController {

    final Logger logger = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    EmailService emailService;

    @Setter(onMethod = @__({@Autowired}))
    private ReimbursementRepository reimbursementRepository;

    @Setter(onMethod = @__({@Autowired}))
    private EmailRepository emailRepository;

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping
    public ResponseEntity getAllEmails() {
        return ResponseEntity.ok(emailRepository.findAll());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity getEmailById(@PathVariable int id) {
        logger.debug("Get reimbursement with ID: {}", reimbursementRepository.findById(id));
        Email request = emailRepository.findById(id);

        if(request == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(request);
    }

    @GetMapping(path = "emailAddress/{emailAddress}")
    public ResponseEntity getAllEmailsByEmployeeEmailAddress(@PathVariable String emailAddress) {
        return ResponseEntity.ok(emailRepository.findAllByEmailAddress(emailAddress));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping
    public ResponseEntity generateEmailByItemStatus(@RequestBody EmailDTO emailDTO) {
        logger.debug("Get reimbursement with ID: {}", reimbursementRepository.findById(emailDTO.getReimbursementId()));
        Reimbursement request = reimbursementRepository.findById(emailDTO.getReimbursementId());

        if(request == null) {
            return ResponseEntity.notFound().build();
        }

        Email email = emailService.createEmail(emailDTO.getReimbursementId());
        return ResponseEntity.ok().body(email);
    }

    @DeleteMapping(path = "{emailId}")
    public ResponseEntity deleteReimbursement(@PathVariable int emailId){
        emailRepository.deleteById(emailId);
        return ResponseEntity.ok().body("Delete email successful");
    }

}
