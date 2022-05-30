package com.devthiagofurtado.fullstackchallenge.controller;


import com.devthiagofurtado.fullstackchallenge.data.vo.MensagemVO;
import com.devthiagofurtado.fullstackchallenge.exception.InvalidJwtAuthenticationException;
import com.devthiagofurtado.fullstackchallenge.security.jwt.JwtTokenProvider;
import com.devthiagofurtado.fullstackchallenge.util.HeaderUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "WelcomeEndpoint")
@RestController
@RequestMapping("/")
public class WelcomeController {

    @Autowired
    JwtTokenProvider tokenProvider;

    @Operation(summary = "Message Welcome")
    @GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<MensagemVO> welcome() throws InvalidJwtAuthenticationException {

        String token = HeaderUtil.obterToken();
        if (StringUtils.hasText(token)) {
            tokenProvider.validateToken(token.substring(7, token.length()));
        } else {
            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
        }

        var msg = MensagemVO.builder()
                .mensagem("Fullstack Challenge \uD83C\uDFC5 - Dictionary")
                .build();


        return new ResponseEntity<>(msg, HttpStatus.OK);

    }

}

