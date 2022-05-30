package com.devthiagofurtado.fullstackchallenge.controller;


import com.devthiagofurtado.fullstackchallenge.data.vo.AuthVO;
import com.devthiagofurtado.fullstackchallenge.data.vo.UsuarioVO;
import com.devthiagofurtado.fullstackchallenge.exception.ResourceBadRequestException;
import com.devthiagofurtado.fullstackchallenge.security.AccountCredentialsVO;
import com.devthiagofurtado.fullstackchallenge.security.jwt.JwtTokenProvider;
import com.devthiagofurtado.fullstackchallenge.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            token = tokenProvider.createToken(username, user.getRoles());

        } else {
            throw new ResourceBadRequestException("Erro ao tentar conexão.");
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
    public ResponseEntity<AuthVO> signup(@RequestBody UsuarioVO user) {

        var userSaved = userService.signUp(user);

        var token = tokenProvider.createToken(userSaved.getUserName(), userSaved.getRoles());

        var auth = AuthVO.builder()
                .id(userSaved.getId())
                .name(userSaved.getFullName())
                .token(token)
                .build();

        return new ResponseEntity<>(auth, HttpStatus.CREATED);

    }

}

