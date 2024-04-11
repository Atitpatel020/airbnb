package com.schedular.config;

import com.schedular.entity.UserRegistration;
import com.schedular.repository.UserRegistrationRepository;
import com.schedular.utill.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    
    private UserRegistrationRepository repository;

    private JwtService jwtService;

    public JwtRequestFilter(UserRegistrationRepository repository, JwtService jwtService) {
        this.repository = repository;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestHeader = request.getHeader("Authorization");
        if(requestHeader!=null && requestHeader.startsWith("Bearer ")){
            String token = requestHeader.substring(7);
            String userName = jwtService.getUserName(token);
            Optional<UserRegistration> opUser = repository.findByUserName(userName);
            if(opUser.isPresent()) {
                UserRegistration registration = opUser.get();

                UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(registration,"null", Collections.singleton(new SimpleGrantedAuthority(registration.getRoleType())));
                authenticationToken.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
