package com.revature.reimbursementapi.services;

import com.revature.reimbursementapi.models.*;
import com.revature.reimbursementapi.repositories.EmployeeRepository;
import com.revature.reimbursementapi.repositories.ReimbursementRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
class ReimbursementServiceTest {

    @Autowired
    private ReimbursementRepository reimbursementRepository;

    @Autowired
    ReimbursementService reimbursementService;

    @Autowired
    private EmployeeRepository employeeRepository;

    Employee employee;
    Reimbursement reimbursement;
    ReimbursementDTO reimbursementDTO;
    ApprovalDTO approvedDTO;
    ApprovalDTO declinedDTO;
    ApprovalDTO pendingDTO;
    ReassignDTO reassignDTO;

    @BeforeEach
    public void initEachTest() {

        employee = Employee.builder().id(1).name("Jason Yeung").email("j33yeung@gmail.com").build();

        reimbursement = Reimbursement.builder().id(1).itemName("keyboard").itemStatus(Status.PENDING)
                .expenditure(new BigDecimal("85.09")).date(LocalDate.of(2022, 5, 1))
                .employee(employee).itemDescriptor("keyboard for work").build();

        reimbursementRepository.save(reimbursement);
    }

//    @AfterEach
//    public void afterAllTest() {
//        employee = Employee.builder().id(1).name("Jason Yeung").email("j33yeung@gmail.com").build();
//
//        reimbursement = Reimbursement.builder().id(1).itemName("keyboard").itemStatus(Status.PENDING)
//                .expenditure(new BigDecimal("85.09")).date(LocalDate.of(2022, 5, 1))
//                .employee(employee).itemDescriptor("keyboard for work").build();
//
//        reimbursementRepository.save(reimbursement);
//    }

    @Test
    public void shouldFindEmployeeById(){

        Employee e = employeeRepository.findById(1);
        Assertions.assertEquals("Jason Yeung", e.getName());
        Assertions.assertEquals("j33yeung@gmail.com", e.getEmail());
    }

    @Test
    public void shouldFindReimbursementById(){

        Reimbursement r = reimbursementRepository.findById(1);
        Assertions.assertEquals("keyboard", r.getItemName());
        Assertions.assertEquals(Status.PENDING, reimbursementRepository.findById(1).getItemStatus());
        Assertions.assertEquals(new BigDecimal("85.09"), reimbursementRepository.findById(1).getExpenditure());
        Assertions.assertEquals(LocalDate.of(2022,5,1), reimbursementRepository.findById(1).getDate());
        Assertions.assertEquals(employee, reimbursementRepository.findById(1).getEmployee());
        Assertions.assertEquals("keyboard for work", reimbursementRepository.findById(1).getItemDescriptor());
    }

    @Test
    public void shouldSaveReimbursement(){

        Reimbursement newReimbursement = Reimbursement.builder().id(1).itemName("mouse").itemStatus(Status.PENDING)
            .expenditure(new BigDecimal("25.09")).date(LocalDate.of(2022, 7, 1))
            .employee(employee).itemDescriptor("mouse for work").build();

        reimbursementRepository.save(newReimbursement);

        Assertions.assertEquals("mouse", reimbursementRepository.findById(1).getItemName());
        Assertions.assertEquals(Status.PENDING, reimbursementRepository.findById(1).getItemStatus());
        Assertions.assertEquals(new BigDecimal("25.09"), reimbursementRepository.findById(1).getExpenditure());
        Assertions.assertEquals(LocalDate.of(2022,7,1), reimbursementRepository.findById(1).getDate());
        Assertions.assertEquals(employee, reimbursementRepository.findById(1).getEmployee());
        Assertions.assertEquals("mouse for work", reimbursementRepository.findById(1).getItemDescriptor());
    }

    // When this test is run, a new reimbursement is created and tested. After which, it is deleted at the end of the test.
    @Test
    public void shouldCreateReimbursement() {

        reimbursementDTO = ReimbursementDTO.builder().itemName("usb").itemStatus(Status.PENDING)
                .expenditure(new BigDecimal("10.87")).date(LocalDate.of(2022, 5, 2))
                .employeeId(1).itemDescriptor("usb for work").build();

        Reimbursement reimbursement = reimbursementService.createReimbursement(reimbursementDTO);
        Assertions.assertEquals(new BigDecimal("10.87"), reimbursementRepository.findByItemName("usb").getExpenditure());
        reimbursementRepository.deleteById(reimbursement.getId());
    }

    // When this test is run, reimbursementService will find the reimbursement by Id, change and assertEquals against all types of item statuses
    @Test
    public void shouldApproveAllReimbursements(){
        approvedDTO = ApprovalDTO.builder().reimbursementId(1).itemStatus(Status.APPROVED).build();
        declinedDTO = ApprovalDTO.builder().reimbursementId(1).itemStatus(Status.DECLINED).build();
        pendingDTO = ApprovalDTO.builder().reimbursementId(1).itemStatus(Status.PENDING).build();

        Reimbursement reimbursement = reimbursementService.approveReimbursement(approvedDTO);
        Assertions.assertEquals(Status.APPROVED, reimbursement.getItemStatus());
        reimbursement = reimbursementService.approveReimbursement(declinedDTO);
        Assertions.assertEquals(Status.DECLINED, reimbursement.getItemStatus());
        reimbursement = reimbursementService.approveReimbursement(pendingDTO);
        Assertions.assertEquals(Status.PENDING, reimbursement.getItemStatus());
    }

    @Test
    public void shouldReassignReimbursement() {
        reassignDTO = ReassignDTO.builder().reimbursementId(1).newEmployeeId(2).build();

        Reimbursement reimbursement = reimbursementService.reassignReimbursement(reassignDTO);

    }

}