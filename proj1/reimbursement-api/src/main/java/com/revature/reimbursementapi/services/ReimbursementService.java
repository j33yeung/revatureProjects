package com.revature.reimbursementapi.services;

import com.revature.reimbursementapi.models.ReimbursementDTO;
import com.revature.reimbursementapi.models.Employee;
import com.revature.reimbursementapi.models.Reimbursement;
import com.revature.reimbursementapi.models.Status;
import com.revature.reimbursementapi.repositories.EmployeeRepository;
import com.revature.reimbursementapi.repositories.ReimbursementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;

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

    public void createReimbursement(ReimbursementDTO reimbursementDTO){

//        Reimbursement reimbursement = new Reimbursement();
//
//        reimbursement.setItemName(reimbursementDTO.getItemName());
//        reimbursement.setItemStatus(reimbursementDTO.getItemStatus());
//        reimbursement.setExpenditure(reimbursementDTO.getExpenditure());
//        reimbursement.setDate(reimbursementDTO.getDate());
//        reimbursement.setItemDescriptor(reimbursementDTO.getItemDescriptor());
//
//        int employeeId = reimbursementDTO.getEmployeeId();
//        Employee e = employeeRepository.findById(employeeId);
//        reimbursement.setEmployee(e);

        String itemName = reimbursementDTO.getItemName();
        Status itemStatus = reimbursementDTO.getItemStatus();
        BigDecimal expenditure = reimbursementDTO.getExpenditure();
        LocalDate date = reimbursementDTO.getDate();

        int employeeId = reimbursementDTO.getEmployeeId();
        logger.debug("Find employee: {}", employeeRepository.findById(employeeId));
        Employee employee = employeeRepository.findById(employeeId);

        String itemDescriptor = reimbursementDTO.getItemDescriptor();

        Reimbursement reimbursement = new Reimbursement(0, itemName, itemStatus, expenditure, date, employee, itemDescriptor);
        reimbursementRepository.save(reimbursement);

    }
}
