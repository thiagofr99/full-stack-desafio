package com.devthiagofurtado.cardapioqrcode.controller;


import com.devthiagofurtado.cardapioqrcode.data.vo.UsuarioVO;
import com.devthiagofurtado.cardapioqrcode.exception.InvalidJwtAuthenticationException;
import com.devthiagofurtado.cardapioqrcode.security.jwt.JwtTokenProvider;
import com.devthiagofurtado.cardapioqrcode.service.PalavraService;
import com.devthiagofurtado.cardapioqrcode.util.HeaderUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


@Tag(name = "EntriesEndpoint")
@RestController
@RequestMapping("/entries/en")
public class EntriesController {

    @Autowired
    PalavraService palavraService;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    private PagedResourcesAssembler<UsuarioVO> assembler;

    @Operation(summary = "Find Word's for part of word")
    @GetMapping(value = "", produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<?> findAllByWord(@RequestParam(value = "search", defaultValue = "") String palavra,
                                           @RequestParam(value = "limit", defaultValue = "4") int limit,
                                           @RequestParam(value = "page", defaultValue = "0") int page) throws InvalidJwtAuthenticationException {

        String token = HeaderUtil.obterToken();
        if (StringUtils.hasText(token)) {
            tokenProvider.validateToken(token.substring(7, token.length()));
        } else {
            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
        }

        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "palavra"));
        var results = palavraService.buscarPalavra(palavra, pageable);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @Operation(summary = "Find infos for Word")
    @GetMapping(value = "/{word}", produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<?> findInfos(@PathVariable(value = "word") String word) throws InvalidJwtAuthenticationException {
        String token = HeaderUtil.obterToken();
        if (StringUtils.hasText(token)) {
            tokenProvider.validateToken(token.substring(7, token.length()));
        } else {
            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
        }

        var results = palavraService.buscarInfos(word);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }


}

