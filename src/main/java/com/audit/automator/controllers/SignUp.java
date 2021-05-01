package com.audit.automator.controllers;

import com.audit.automator.pojo.AddClientRequest;
import com.audit.automator.pojo.UserCreationRequest;
import com.audit.automator.response.CreationRequestResponse;
import com.audit.automator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class SignUp {

    private UserService service;

    @Autowired
    public SignUp(UserService userService) {
        this.service = userService;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public CreationRequestResponse createUser(@RequestBody UserCreationRequest request){
        return service.createUser(request);
    }

    @PostMapping("/add-client")
    public CreationRequestResponse createUser(@RequestBody AddClientRequest request){
        return service.addClient(request);
    }
}
