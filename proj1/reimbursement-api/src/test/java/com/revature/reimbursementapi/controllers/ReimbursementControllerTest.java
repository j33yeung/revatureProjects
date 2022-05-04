package com.revature.reimbursementapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.reimbursementapi.models.Employee;
import com.revature.reimbursementapi.models.Reimbursement;
import com.revature.reimbursementapi.models.ReimbursementDTO;
import com.revature.reimbursementapi.models.Status;
import com.revature.reimbursementapi.repositories.EmployeeRepository;
import com.revature.reimbursementapi.repositories.ReimbursementRepository;
import com.revature.reimbursementapi.services.ReimbursementService;
import javafx.application.Application;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = Application.class)
//@WebMvcTest(ReimbursementController.class)
@SpringBootTest
public class ReimbursementControllerTest extends ControllerTester {

    @Autowired
    private ReimbursementRepository reimbursementRepository;

    @Autowired
    ReimbursementService reimbursementService;

    @Autowired
    private EmployeeRepository employeeRepository;

//    @BeforeEach
//    public void initEachTest() {
//        Employee employee = Employee.builder().id(1).name("Jason Yeung").email("j33yeung@gmail.com").build();
//
//        Reimbursement reimbursement = Reimbursement.builder().id(1).itemName("keyboard").itemStatus(Status.PENDING)
//                .expenditure(new BigDecimal("85.09")).date(LocalDate.of(2022, 5, 1))
//                .employee(employee).itemDescriptor("keyboard for work").build();
//    }

//    @Test
//    void shouldAddReimbursement(){
//        ReimbursementDTO reimbursementDTO = ReimbursementDTO.builder().itemName("keyboard2").itemStatus(Status.PENDING)
//                .expenditure(new BigDecimal("99.10")).date(LocalDate.of(2022, 5, 1))
//                .employeeId(1).itemDescriptor("keyboard for work again").build();
//
//    }

}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private ReimbursementController reimbursementController;
//
//    @BeforeEach
//    public void initEachTest() {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
//    }
//
//    @Override
//    @BeforeEach
//    public void setUp() {
//        super.setUp();
//
//        ReimbursementController reimbursementController;
//
//        ReimbursementRepository reimbursementRepository;
//
//        ReimbursementService reimbursementService;
//
//        EmployeeRepository employeeRepository;
//    }
//
//    @Test
//    public void shouldGetAllReimbursements() throws Exception {
//        String uri = "";
//        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
//                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
//
//        int status = mvcResult.getResponse().getStatus();
//        assertEquals(200, status);
//        String content = mvcResult.getResponse().getContentAsString();
//        Reimbursement[] reimbursementList = super.mapFromJson(content, Reimbursement[].class);
//        assertTrue(reimbursementList.length > 0);
//    }
//}

//    @Test
//    void shouldGetAllReimbursements() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("").contentType("application/json"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
