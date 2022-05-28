package com.devthiagofurtado.cardapioqrcode.controller;


import com.devthiagofurtado.cardapioqrcode.exception.InvalidJwtAuthenticationException;
import com.devthiagofurtado.cardapioqrcode.security.jwt.JwtTokenProvider;
import com.devthiagofurtado.cardapioqrcode.util.HeaderUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;


@Tag(name = "WelcomeEndpoint")
@RestController
@RequestMapping("/")
public class WelcomeController {

    @Autowired
    JwtTokenProvider tokenProvider;

    @Operation(summary = "Message Welcome")
    @GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity welcome() throws InvalidJwtAuthenticationException {

        String token = HeaderUtil.obterToken();
        if(StringUtils.hasText(token)){
            tokenProvider.validateToken(token.substring(7, token.length()));
        } else {
            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
        }


        Map<Object, Object> model = new HashMap<>();
        model.put("Message", "Fullstack Challenge \uD83C\uDFC5 - Dictionary");
        return ok(model);

    }

}

