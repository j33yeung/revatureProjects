package com.example.revature.emailapi.services;

import com.example.revature.emailapi.models.*;
import com.example.revature.emailapi.repositories.EmailRepository;
import com.example.revature.emailapi.repositories.ReimbursementRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {

    private EmailRepository emailRepository;
    private ReimbursementRepository reimbursementRepository;
    final Logger logger = LoggerFactory.getLogger(EmailService.class);

//    I don't need this constructor because I have @AllArgsConstructor already
//    public EmailService(EmailRepository emailRepository) {
//        this.emailRepository = emailRepository;
//    }

    public Email createEmail(int reimbursementId) {

        Email newEmail = new Email();
        Reimbursement reimbursement = reimbursementRepository.findById(reimbursementId);
        Employee employee = reimbursement.getEmployee();

        newEmail.setEmailAddress(employee.getEmail());

        switch(reimbursement.getItemStatus()){
            case PENDING:
                newEmail.setEmailBody("Your reimbursement is currently still pending");
                break;
            case APPROVED:
                newEmail.setEmailBody("Your reimbursement has been approved");
                break;
            case DECLINED:
                newEmail.setEmailBody("Your reimbursement has been declined");
                break;
        }

        emailRepository.save(newEmail);

        return newEmail;
    }

}
