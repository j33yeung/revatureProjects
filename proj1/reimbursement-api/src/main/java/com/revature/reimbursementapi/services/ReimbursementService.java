package com.revature.reimbursementapi.services;

import com.revature.reimbursementapi.models.*;
import com.revature.reimbursementapi.repositories.EmployeeRepository;
import com.revature.reimbursementapi.repositories.ReimbursementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class ReimbursementService {

    private ReimbursementRepository reimbursementRepository;
    private EmployeeRepository employeeRepository;

    public ReimbursementService(ReimbursementRepository reimbursementRepository, EmployeeRepository employeeRepository){
        super();
        this.reimbursementRepository = reimbursementRepository;
        this.employeeRepository = employeeRepository;

    }

    final Logger logger = LoggerFactory.getLogger(ReimbursementService.class);

    public Reimbursement createReimbursement(ReimbursementDTO reimbursementDTO) {

        Reimbursement reimbursement = new Reimbursement();

        reimbursement.setItemName(reimbursementDTO.getItemName());
        reimbursement.setItemStatus(reimbursementDTO.getItemStatus());
        reimbursement.setExpenditure(reimbursementDTO.getExpenditure());
        reimbursement.setDate(reimbursementDTO.getDate());
        reimbursement.setItemDescriptor(reimbursementDTO.getItemDescriptor());

        int employeeId = reimbursementDTO.getEmployeeId();
        Employee employee = employeeRepository.findById(employeeId);
        reimbursement.setEmployee(employee);

        reimbursementRepository.save(reimbursement);

        return reimbursement;
    }

    public Reimbursement approveReimbursement(ApprovalDTO approvalDTO) {

        Reimbursement reimbursement = reimbursementRepository.findById(approvalDTO.getReimbursementId());

        switch(approvalDTO.getItemStatus()){
            case "PENDING":
                reimbursement.setItemStatus(Status.PENDING);
                break;
            case "APPROVED":
                reimbursement.setItemStatus(Status.APPROVED);
                break;
            case "DECLINED":
                reimbursement.setItemStatus(Status.DECLINED);
                break;
        }

        return reimbursement;
    }

    public Reimbursement reassignReimbursement(ReassignDTO reassignDTO) {

        Reimbursement reimbursement = reimbursementRepository.findById(reassignDTO.getReimbursementId());
        Employee employee = employeeRepository.findById(reassignDTO.getNewEmployeeId());
        reimbursement.setEmployee(employee); //need new employee id that you want to change to

        return reimbursement;

    }
}
