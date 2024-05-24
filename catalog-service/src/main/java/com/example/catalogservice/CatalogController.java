package com.example.catalogservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalogs")
public class CatalogController {

    @GetMapping("/welcome")
    public String find(){
        return "Hello Catalog Service ";
    }

    @GetMapping("/message")
    public String msg(@RequestHeader("gateway") String gateway){
        return "gateway: "+gateway;
    }

}
