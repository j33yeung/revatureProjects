package com.revature.reimbursementapi.controllers;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URI;
import java.net.URISyntaxException;

// @Controller // is a Spring bean that receives web requests and generates data that is rendered to a template
// @ResponseBody // writes data from the controller directly to the response body

@RestController
@RequestMapping("/test")  // /<context>/test ... context found in application.yaml
public class TestController implements BeanNameAware {

    public TestController() {
        //System.out.println("Creating controller instance");
    }

    @GetMapping
    public String getTestMessage(HttpServletRequest req, HttpServletResponse response, HttpSession session) {
        System.out.println("Request for test message");
        return "Hello, World";
    }

    @GetMapping(path="{id}") // /<context/test/{id}
    public ResponseEntity getTestMessageWithId(@PathVariable("id") Integer messageId) {
        System.out.println("Request for message " + messageId);
        return ResponseEntity.ok("You request message " + messageId);
    }

    @GetMapping("search") // /<context>/test/search?startsWith=x
    public ResponseEntity searchForMessage(@RequestParam(required = false) String startsWith) {
        System.out.println("Searching for message that starts with " + startsWith);
        return ResponseEntity.ok("Filtering messages that start with " + startsWith);
    }

    @PostMapping("new")
    public ResponseEntity newMessage(@RequestBody String body) throws URISyntaxException {
        System.out.println("Creating new message  " + body);
        return ResponseEntity.created(new URI("http://localhost/bears/test/1")).build();
    }

    @Override
    public void setBeanName(String s) {
        System.out.println("My bean name is " + s);
    }

}
