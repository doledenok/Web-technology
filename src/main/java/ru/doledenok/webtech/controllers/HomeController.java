package ru.doledenok.webtech.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping(value = { "/", "/index"})
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/allEmployees" )
    public String allEmployees() {
        return "employees";
    }
/*
    @RequestMapping(value = "/generateTree")
    public String generateTree() {
        return "generateTree";
    }
*/
}
