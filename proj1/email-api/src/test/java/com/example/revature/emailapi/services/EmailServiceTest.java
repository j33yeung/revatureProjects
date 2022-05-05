package com.example.revature.emailapi.services;

import com.example.revature.emailapi.models.*;
import com.example.revature.emailapi.repositories.EmailRepository;
import com.example.revature.emailapi.repositories.ReimbursementRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmailServiceTest {

    @Autowired
    private ReimbursementRepository reimbursementRepository;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private EmailService emailService;

    private Employee employee;
    private Reimbursement reimbursement;
    private Email email;
    private EmailDTO emailDTO;


    @BeforeEach
    public void initEachTest(){
        employee = Employee.builder().id(1).name("Jason Yeung").email("j33yeung@gmail.com").build();

        reimbursement = Reimbursement.builder().id(1).itemName("keyboard").itemStatus(Status.PENDING)
                .expenditure(new BigDecimal("85.09")).date(LocalDate.of(2022, 5, 1))
                .employee(employee).itemDescriptor("keyboard for work").build();

        emailDTO = EmailDTO.builder().reimbursementId(1).build();

        reimbursementRepository.save(reimbursement);

        email = Email.builder().emailId(1).emailAddress("j33yeung@gmail.com").emailBody("").build();

        emailRepository.save(email);
    }

    @Test
    void shouldCreateEmailforPendingStatus() {

        Email email = emailService.createEmail(emailDTO.getReimbursementId());

        Assertions.assertEquals("j33yeung@gmail.com", email.getEmailAddress());
        Assertions.assertEquals("Your reimbursement is currently still pending", email.getEmailBody());

        emailRepository.deleteById(email.getEmailId());
    }

    @Test
    void shouldCreateEmailForApprovedStatus() {

        reimbursement.setItemStatus(Status.APPROVED);
        reimbursementRepository.save(reimbursement);

        Email email = emailService.createEmail(emailDTO.getReimbursementId());

        Assertions.assertEquals("j33yeung@gmail.com", email.getEmailAddress());
        Assertions.assertEquals("Your reimbursement has been approved", email.getEmailBody());

        emailRepository.deleteById(email.getEmailId());
    }

    @Test
    void shouldCreateEmailForDeclinedStatus() {

        reimbursement.setItemStatus(Status.DECLINED);
        reimbursementRepository.save(reimbursement);

        Email email = emailService.createEmail(emailDTO.getReimbursementId());

        Assertions.assertEquals("j33yeung@gmail.com", email.getEmailAddress());
        Assertions.assertEquals("Your reimbursement has been declined", email.getEmailBody());

        emailRepository.deleteById(email.getEmailId());
    }
}