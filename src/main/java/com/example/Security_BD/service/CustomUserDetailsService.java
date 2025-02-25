package com.example.Security_BD.service;

import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Security_BD.model.Usuario;
import com.example.Security_BD.repository.UsuarioRepository;

@Service
@Primary
public class CustomUserDetailsService implements UserDetailsService {
    private final UsuarioRepository UsuarioRepository;

    
    public CustomUserDetailsService (UsuarioRepository usuarioRepository){
        this.UsuarioRepository = usuarioRepository;
}

@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        System.out.println("INTENTANDO AUTENTICAR USUARIO" + username);
        
        Usuario usuario = UsuarioRepository.findByUsername(username).orElseThrow(()->{
            System.out.println("Usuario no encontrado en la DB");
            return new UsernameNotFoundException("Usuario no encontrado");
        });

        System.out.println("usuario encontrado: " + usuario.getUsername());

        return new User (
            usuario.getUsername(),
            usuario.getPassword(),

            usuario.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
        );
    }




}
