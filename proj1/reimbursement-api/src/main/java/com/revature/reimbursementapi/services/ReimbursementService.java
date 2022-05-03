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


//    public boolean saveReimbursement(Reimbursement reimbursement){
//        LocalDate date = LocalDate.now();
//        reimbursement.setDate(date);
//        Employee newEmployee = employeeRepository.findEmployeeById(1);
//        reimbursement.setEmployee(newEmployee);
//        reimbursementRepository.save(reimbursement);
//        return true;
//    }

    public void createReimbursement(ReimbursementDTO reimbursementDTO) {

        Reimbursement reimbursement = new Reimbursement();

        reimbursement.setItemName(reimbursementDTO.getItemName());
        reimbursement.setItemStatus(reimbursementDTO.getItemStatus());
        reimbursement.setExpenditure(reimbursementDTO.getExpenditure());
        reimbursement.setDate(reimbursementDTO.getDate());
        reimbursement.setItemDescriptor(reimbursementDTO.getItemDescriptor());

        int employeeId = reimbursementDTO.getEmployeeId();
        Employee employee = employeeRepository.findById(employeeId);
        reimbursement.setEmployee(employee);

//        int employeeId = reimbursementDTO.getEmployeeId();
//        Employee e = employeeRepository.findById(employeeId);
//        reimbursement.setEmployee(e);

        reimbursementRepository.save(reimbursement);
    }

    public void approveReimbursement(ApprovalDTO approvalDTO) {

        Reimbursement r = reimbursementRepository.getById(approvalDTO.getReimbursementId());

        switch(approvalDTO.getItemStatus()){
            case "PENDING":
                r.setItemStatus(Status.PENDING);
                break;
            case "APPROVED":
                r.setItemStatus(Status.APPROVED);
                break;
            case "DECLINED":
                r.setItemStatus(Status.DECLINED);
                break;
        }
    }

    public void reassignReimbursement(ReassignDTO reassignDTO) {

        Reimbursement r = reimbursementRepository.getById(reassignDTO.getReimbursementId());
        Employee e = employeeRepository.findById(reassignDTO.getNewEmployeeId());
        r.setEmployee(e); //need new employee id that you want to change to

    }

}