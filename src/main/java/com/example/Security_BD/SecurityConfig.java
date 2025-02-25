package com.example.Security_BD;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.Security_BD.repository.UsuarioRepository;
import com.example.Security_BD.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(UsuarioRepository usuarioRepository){
        return new CustomUserDetailsService(usuarioRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider
        (UserDetailsService userDetailsService, PasswordEncoder passwordEncoder){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager (HttpSecurity http, UserDetailsService userdetailsService, PasswordEncoder passwordEncoder)throws
    Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
        .authenticationProvider(authenticationProvider(userdetailsService,passwordEncoder))
        .build();
    }


    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http)throws Exception {
        http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
        .requestMatchers("/login/**").permitAll()
        .requestMatchers("/home/**").authenticated()
        .requestMatchers("/cliente/**").hasRole("CLIENTE")
        .requestMatchers("/admin/**").hasRole("ADMIN")
        .anyRequest().authenticated()
        )
        .formLogin(form -> form
        .loginPage("/login")
        .defaultSuccessUrl("/redirect", true)
        .permitAll()
        );
        return http.build();
    }

}
