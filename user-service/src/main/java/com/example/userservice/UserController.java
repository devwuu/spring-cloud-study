package com.example.userservice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final Environment env;

    @GetMapping("/welcome")
    public String find(){
        return "Hello User Service ";
    }

    @GetMapping("/message")
    public String msg(@RequestHeader("gateway") String gateway){
        return "gateway: "+gateway;
    }

    @GetMapping("/whois")
    public String whois(HttpServletRequest request){
        log.info("server port: {}", request.getServerPort());
        return String.format("hi, this is %s port", env.getProperty("local.server.port"));
    }


}
