package com.example.userservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/welcome")
    public String find(){
        return "Hello User Service ";
    }

    @GetMapping("/message")
    public String msg(@RequestHeader("gateway") String gateway){
        return "gateway: "+gateway;
    }


}
