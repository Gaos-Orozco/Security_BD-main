package com.example.Security_BD.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String loginPage() {
    return "login";

    }

    @GetMapping("/home")
    public String homePage() {
    return "home";
    }

    @GetMapping ("/admin")
    public String adminPage() {
    return "admin";
    }

    @GetMapping ("/cliente")
    public String clientePage() {
    return "cliente";
    }


}
