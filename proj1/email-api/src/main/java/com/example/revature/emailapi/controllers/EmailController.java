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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /**
     * Get all emails that are stored in the emails table
     * @return ResponseEntity - Ok when all emails in database are found
     */
    @GetMapping
    public ResponseEntity getAllEmails() {
        return ResponseEntity.ok(emailRepository.findAll());
    }

    /**
     * Get email that are stored in the emails table based on id provided in URI
     * @return ResponseEntity - Ok if email id exist in database; 404 not found if id does not exist
     */
    @GetMapping(path = "{id}")
    public ResponseEntity getEmailById(@PathVariable int id) {

        Email request = emailRepository.findById(id);
        logger.debug("Get email with ID: {}", emailRepository.findById(id));

        if(request == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(request);
    }

    /**
     * Get all emails that are stored in the emails table that has the employee's email address provided
     * @return ResponseEntity - Ok when all emails with the corresponding employee's email address are found
     */
    @GetMapping(path = "emailAddress/{emailAddress}")
    public ResponseEntity getAllEmailsByEmployeeEmailAddress(@PathVariable String emailAddress) {
        return ResponseEntity.ok(emailRepository.findAllByEmailAddress(emailAddress));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Create an email based on request body, and saves an email message to the database. The context of the email
     * message depends on the status of the reimbursement.
     * @return ResponseEntity - Ok if email is created successfully; 404 not found if email does not exist, and
     * no email will be created
     */
    @PostMapping
    public ResponseEntity generateEmailByItemStatus(@RequestBody EmailDTO emailDTO) {

        Reimbursement request = reimbursementRepository.findById(emailDTO.getReimbursementId());
        logger.debug("Get reimbursement with ID: {}", reimbursementRepository.findById(emailDTO.getReimbursementId()));

        if(request == null) {
            return ResponseEntity.notFound().build();
        }

        Email email = emailService.createEmail(emailDTO.getReimbursementId());
        logger.debug("Get new email: {}", emailRepository.findById(email.getEmailId()));
        return ResponseEntity.ok().body(email);
    }

    /**
     * Delete email that are stored in the emails tables
     * @return ResponseEntity - Ok if email is successfully deleted based on email id
     */
    @DeleteMapping(path = "{emailId}")
    public ResponseEntity deleteReimbursement(@PathVariable int emailId){
        emailRepository.deleteById(emailId);
        return ResponseEntity.ok().body("Delete email successful");
    }

}
