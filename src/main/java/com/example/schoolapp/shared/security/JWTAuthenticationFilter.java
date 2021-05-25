package com.example.schoolapp.shared.security;

import com.example.schoolapp.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static com.example.schoolapp.shared.security.SecurityConstants.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private UserService userService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager,
                                   UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            com.example.schoolapp.domain.User creds = new ObjectMapper()
                    .readValue(req.getInputStream(), com.example.schoolapp.domain.User.class);
            com.example.schoolapp.domain.User user = userService.findByLogin(creds.getLogin());
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getLogin(),
                            creds.getPassword(),
                            user != null ? Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getName())) : new ArrayList<>()
                            )
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        Claims claims = Jwts.claims().setSubject(((User) auth.getPrincipal()).getUsername());
        claims.put("scopes", auth.getAuthorities().iterator().next());
        String token = Jwts.builder()
                .setSubject(((User) auth.getPrincipal()).getUsername())
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);

        String responseToClient = token;
        res.setStatus(HttpServletResponse.SC_OK);
        res.setHeader(HttpHeaders.CONTENT_TYPE, "text");
        res.getWriter().print(responseToClient);
        res.getWriter().flush();
        res.getWriter().close();
    }
}
