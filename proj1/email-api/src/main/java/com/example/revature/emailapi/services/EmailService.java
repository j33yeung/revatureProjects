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

    /**
     *     I don't need this constructor because I have @AllArgsConstructor already
     *     public EmailService(EmailRepository emailRepository) {
     *         this.emailRepository = emailRepository;
     *     }
     */

    /**
     * This service is used in order to create a new email and send it to the employee's email address
     * associated to reimbursement id
     * @param reimbursementId the id of the reimbursement is used to find the reimbursement, which will be used to find
     *                        the reimbursement's status and employee's email address. The status of the reimbursement
     *                        will determine the email message sent.
     * @return the new email
     */
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
