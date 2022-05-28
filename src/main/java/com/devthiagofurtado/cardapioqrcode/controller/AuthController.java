package com.devthiagofurtado.cardapioqrcode.controller;


import com.devthiagofurtado.cardapioqrcode.data.vo.UsuarioVO;
import com.devthiagofurtado.cardapioqrcode.exception.ResourceBadRequestException;
import com.devthiagofurtado.cardapioqrcode.security.AccountCredentialsVO;
import com.devthiagofurtado.cardapioqrcode.security.jwt.JwtTokenProvider;
import com.devthiagofurtado.cardapioqrcode.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;


@Tag(name = "AuthenticationEndpoint")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    private PagedResourcesAssembler<UsuarioVO> assembler;

    @Operation(summary = "Authenticates a user and returns a token")
    @SuppressWarnings("rawtypes")
    @PostMapping(value = "/signin", produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity signin(@RequestBody AccountCredentialsVO data) {

        var username = data.getUsername();
        var pasword = data.getPassword();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, pasword));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied!");
        }


        var user = userService.findByUserName(username);

        var token = "";

        if (user != null) {
            if (user.getDateLicense() == null || user.getDateLicense().isAfter(LocalDate.now())) {
                token = tokenProvider.createToken(username, user.getRoles());
            } else {
                throw new ResourceBadRequestException("Data de licença expirou em " + user.getDateLicense() + ", entre em  contato com o administrador do sistema.");
            }

        } else {
            throw new UsernameNotFoundException("Username " + username + " not found!");
        }

        Map<Object, Object> model = new HashMap<>();
        model.put("id", user.getId());
        model.put("name", user.getFullName());
        model.put("token", token);
        return ok(model);

    }

    @Operation(summary = "Saves user and returns a VO if user's permission is admin.")
    @PostMapping(value = "/signup", produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity signin(@RequestBody UsuarioVO user) {

        var userSaved = userService.signin(user);

        var token = tokenProvider.createToken(userSaved.getUserName(), userSaved.getRoles());

        Map<Object, Object> model = new HashMap<>();
        model.put("id", userSaved.getId());
        model.put("name", userSaved.getFullName());
        model.put("token", token);
        return ok(model);

    }

}

