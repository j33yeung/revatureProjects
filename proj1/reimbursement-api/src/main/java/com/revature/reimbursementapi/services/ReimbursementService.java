package com.revature.reimbursementapi.services;

import com.revature.reimbursementapi.models.*;
import com.revature.reimbursementapi.repositories.EmployeeRepository;
import com.revature.reimbursementapi.repositories.ReimbursementRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * This service is used in order to create a new reimbursement, adhering to the information provided from the
     * reimbursementDTO and employee information.
     * @param reimbursementDTO DTO that contains necessary information to create a new reimbursement
     * @return the new reimbursement
     */
    public Reimbursement createReimbursement(ReimbursementDTO reimbursementDTO) {

        Reimbursement reimbursement = new Reimbursement();

        reimbursement.setItemName(reimbursementDTO.getItemName());
        reimbursement.setItemStatus(reimbursementDTO.getItemStatus());
        reimbursement.setExpenditure(reimbursementDTO.getExpenditure());
        reimbursement.setDate(reimbursementDTO.getDate());
        reimbursement.setItemDescriptor(reimbursementDTO.getItemDescriptor());

        /*
        Lines 49-51 are commented out because we are finding the id of Employee in the controller. Even
              though this is bad practice, we only need call findById once, so we avoid repeating it here. Therefore,
              we will pass two parameters for this service: reimbursementDTO and employee.

        Bug found: We cannot pass employee as a parameter because of reimbursementRepository.save(reimbursement).
        This service is simply setting values to the new reimbursement, and saving to the database. However, save() is
        a persist operation and can therefore only accept transient objects. In other words, if employee already exists
        and is not brand new, the save will fail. As a result, we need to call findById here before setting employee.
         */
        int employeeId = reimbursementDTO.getEmployeeId();
        Employee employee = employeeRepository.findById(employeeId);
        reimbursement.setEmployee(employee);

        reimbursementRepository.save(reimbursement);

        return reimbursement;
    }

    /**
     * This service is used in order to approve a reimbursement. The approvalDTO contains the reimbursementId,
     * which will be used to fetch the corresponding reimbursement from the database. The approvalDTO also contains the
     * new reimbursement status that the manager wants to change to. For example, if the manager wants to change the
     * reimbursement from "PENDING" to "APPROVED", he will send "APPROVED" to the request body.
     * @param approvalDTO DTO that contains necessary information to approve a reimbursement
     * @param reimbursement the reimbursement status that will be changed by this service
     * @return the reimbursement with a changed status (or same status if the manager put in the same status in the
     * request body)
     */
    public Reimbursement approveReimbursement(ApprovalDTO approvalDTO, Reimbursement reimbursement) {

//        Same as createReimbursement, we do not want to recall the findById method, so we just pass
//        a second argument, Reimbursement
//        Reimbursement reimbursement = reimbursementRepository.findById(approvalDTO.getReimbursementId());

        switch(approvalDTO.getItemStatus()){
            case PENDING:
                reimbursement.setItemStatus(Status.PENDING);
                break;
            case APPROVED:
                reimbursement.setItemStatus(Status.APPROVED);
                break;
            case DECLINED:
                reimbursement.setItemStatus(Status.DECLINED);
                break;
        }

        return reimbursement;
    }

    /**
     * This service is simply used to reassign a reimbursement to another employee by setting the new employee details
     * @param reimbursement the reimbursement that will be reassigned to another (or same) employee
     * @param newEmployee the new employee that will be reimbursed for this request
     * @return the reimbursement that is now reassigned to a new employee (or same employee if it is identical to
     * the employee the manager puts into the request body)
     */
    public Reimbursement reassignReimbursement(Reimbursement reimbursement, Employee newEmployee) {

        reimbursement.setEmployee(newEmployee); //need new employee id that you want to change to
        return reimbursement;
    }
}
