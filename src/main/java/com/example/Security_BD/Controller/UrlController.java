package com.example.Security_BD.Controller;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class UrlController {

    @GetMapping ("/redirect")
    public String redirecionSegunRol(Authentication authentication){
        Collection <? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authorities.stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin";
        } else if (authorities.stream().anyMatch(role -> role.getAuthority().equals("ROLE_CLIENTE"))) {
            return "redirect:/cliente";
        } else {
            return "redirect:/home";
        }


    }

}


