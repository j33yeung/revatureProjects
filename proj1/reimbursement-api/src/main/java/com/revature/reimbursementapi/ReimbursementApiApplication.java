package com.revature.reimbursementapi;

import com.revature.reimbursementapi.repositories.ReimbursementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReimbursementApiApplication {

	@Autowired
	ReimbursementRepository reimbursementRepository;

	public static void main(String[] args) {
		SpringApplication.run(ReimbursementApiApplication.class, args);
	}

}
