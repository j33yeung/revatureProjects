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
    private ReimbursementService reimbursementService;

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;
    private Reimbursement reimbursement;
    private ReimbursementDTO reimbursementDTO;
    private ApprovalDTO approvedDTO;
    private ApprovalDTO declinedDTO;
    private ApprovalDTO pendingDTO;

    // This BeforeEach will update employee with id = 1, and reimbursement with id = 1 before each test
    @BeforeEach
    public void initEachTest() {

        employee = Employee.builder().id(1).name("Jason Yeung").email("j33yeung@gmail.com").build();

        reimbursement = Reimbursement.builder().id(1).itemName("keyboard").itemStatus(Status.PENDING)
                .expenditure(new BigDecimal("85.09")).date(LocalDate.of(2022, 5, 1))
                .employee(employee).itemDescriptor("keyboard for work").build();

//        employeeRepository.save(employee);
        reimbursementRepository.save(reimbursement);
    }

    // This test verifies that the employee found from the database by id, match the expected values we expect
    @Test
    public void shouldFindEmployeeById(){

        Employee e = employeeRepository.findById(1);
        Assertions.assertEquals("Jason Yeung", e.getName());
        Assertions.assertEquals("j33yeung@gmail.com", e.getEmail());
    }

    // This test verifies that the reimbursement found from the database, match the expected values we expect
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

    // When this test is run, a new (or updated) reimbursement is saved into the reimbursement repository. From here,
    // the new reimbursement is tested to verify the contents have indeed been updated.
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

    // When this test is run, a new reimbursement is created and tested. After, it is deleted at the end of the test.
    @Test
    public void shouldCreateReimbursement() {

        reimbursementDTO = ReimbursementDTO.builder().itemName("usb").itemStatus(Status.PENDING)
                .expenditure(new BigDecimal("10.87")).date(LocalDate.of(2022, 5, 2))
                .employeeId(1).itemDescriptor("usb for work").build();

        Reimbursement newReimbursement = reimbursementService.createReimbursement(reimbursementDTO);
        Assertions.assertEquals(new BigDecimal("10.87"), reimbursementRepository.findByItemName("usb").getExpenditure());
        reimbursementRepository.deleteById(newReimbursement.getId());
    }

    // When this test is run, reimbursementService will find the reimbursement by Id, change and assertEquals
    // against all types of item statuses
    @Test
    public void shouldApproveAllReimbursements(){
        approvedDTO = ApprovalDTO.builder().reimbursementId(1).itemStatus(Status.APPROVED).build();
        declinedDTO = ApprovalDTO.builder().reimbursementId(1).itemStatus(Status.DECLINED).build();
        pendingDTO = ApprovalDTO.builder().reimbursementId(1).itemStatus(Status.PENDING).build();

        Reimbursement alteredReimbursement = reimbursementService.approveReimbursement(approvedDTO, reimbursement);
        Assertions.assertEquals(Status.APPROVED, reimbursement.getItemStatus());
        alteredReimbursement = reimbursementService.approveReimbursement(declinedDTO, reimbursement);
        Assertions.assertEquals(Status.DECLINED, reimbursement.getItemStatus());
        alteredReimbursement = reimbursementService.approveReimbursement(pendingDTO, reimbursement);
        Assertions.assertEquals(Status.PENDING, reimbursement.getItemStatus());
    }

    // This test checks if the employee corresponding to the reimbursement has been updated to reflect the new
    // reassignment.
    @Test
    public void shouldReassignReimbursement() {

        Reimbursement reassignedReimbursement = reimbursementService.reassignReimbursement(reimbursement, employee);
        Assertions.assertEquals(employee, reassignedReimbursement.getEmployee());

    }

}