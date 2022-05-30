package com.devthiagofurtado.fullstackchallenge.controller;


import com.devthiagofurtado.fullstackchallenge.data.vo.DictionaryVO;
import com.devthiagofurtado.fullstackchallenge.data.vo.PagePalavraVO;
import com.devthiagofurtado.fullstackchallenge.data.vo.UsuarioVO;
import com.devthiagofurtado.fullstackchallenge.exception.InvalidJwtAuthenticationException;
import com.devthiagofurtado.fullstackchallenge.security.jwt.JwtTokenProvider;
import com.devthiagofurtado.fullstackchallenge.service.PalavraService;
import com.devthiagofurtado.fullstackchallenge.util.HeaderUtil;
import com.devthiagofurtado.fullstackchallenge.util.UtilMensagem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Slf4j
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
    public ResponseEntity<PagePalavraVO> findAllByWord(@RequestParam(value = "search", defaultValue = "") String palavra,
                                                       @RequestParam(value = "limit", defaultValue = "4") int limit,
                                                       @RequestParam(value = "page", defaultValue = "0") int page) throws InvalidJwtAuthenticationException {

        String token = HeaderUtil.obterToken();
        if (StringUtils.hasText(token)) {
            tokenProvider.validateToken(token.substring(7, token.length()));
        } else {
            throw new InvalidJwtAuthenticationException(UtilMensagem.EXPIRED_OR_INVALID_JWT_TOKEN);
        }

        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "palavra"));
        var results = palavraService.buscarPalavra(palavra, pageable);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @Operation(summary = "Find infos for Word")
    @GetMapping(value = "/{word}", produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<DictionaryVO> findInfos(@PathVariable(value = "word") String word,
                                                  HttpServletResponse response) throws InvalidJwtAuthenticationException {
        String token = HeaderUtil.obterToken();
        String userName = "";
        if (StringUtils.hasText(token)) {
            tokenProvider.validateToken(token.substring(7, token.length()));
            userName = tokenProvider.getUsername(token.substring(7, token.length()));
        } else {
            throw new InvalidJwtAuthenticationException(UtilMensagem.EXPIRED_OR_INVALID_JWT_TOKEN);
        }

        var results = palavraService.buscarInfos(word, userName, response);

        long tempoFinal = System.currentTimeMillis() - Long.parseLong(response.getHeader("tempoInicial"));
        response.setHeader("x-response-time", Long.toString(tempoFinal));
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @Operation(summary = "Add Word in favorites")
    @PostMapping(value = "/{word}/favorite")
    public ResponseEntity<HttpStatus> favoriteWord(@PathVariable(value = "word") String word) throws InvalidJwtAuthenticationException {
        String token = HeaderUtil.obterToken();
        String userName = "";
        if (StringUtils.hasText(token)) {
            tokenProvider.validateToken(token.substring(7, token.length()));
            userName = tokenProvider.getUsername(token.substring(7, token.length()));
        } else {
            throw new InvalidJwtAuthenticationException(UtilMensagem.EXPIRED_OR_INVALID_JWT_TOKEN);
        }

        palavraService.favoriteWord(word, userName);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Add Word in favorites")
    @DeleteMapping(value = "/{word}/unfavorite")
    public ResponseEntity<HttpStatus> unFavoriteWord(@PathVariable(value = "word") String word) throws InvalidJwtAuthenticationException {
        String token = HeaderUtil.obterToken();
        String userName = "";
        if (StringUtils.hasText(token)) {
            tokenProvider.validateToken(token.substring(7, token.length()));
            userName = tokenProvider.getUsername(token.substring(7, token.length()));
        } else {
            throw new InvalidJwtAuthenticationException(UtilMensagem.EXPIRED_OR_INVALID_JWT_TOKEN);
        }

        palavraService.unFavoriteWord(word, userName);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}

