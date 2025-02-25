package com.example.Security_BD;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "cliente";
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("Contraseña Encriptada  " + encodedPassword); 
    }

}
