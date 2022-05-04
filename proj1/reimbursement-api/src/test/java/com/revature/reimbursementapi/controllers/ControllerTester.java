package com.revature.reimbursementapi.controllers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ReimbursementController.class)
@WebAppConfiguration
public abstract class ControllerTester {

    protected MockMvc mvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    protected void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

//    @Mock
//    private ReimbursementRepository reimbursementRepository;
//
//    @Autowired
//    ReimbursementService reimbursementService;
//
//    @Mock
//    private EmployeeRepository employeeRepository;
//
//    @BeforeEach
//    public void initEachTest() {
//        Employee employee = Employee.builder().id(1).name("Jason Yeung").email("j33yeung@gmail.com").build();
//
//        Reimbursement reimbursement = Reimbursement.builder().id(1).itemName("keyboard").itemStatus(Status.PENDING)
//                .expenditure(new BigDecimal("85.09")).date(LocalDate.of(2022, 5, 1))
//                .employee(employee).itemDescriptor("keyboard for work").build();
//    }
}