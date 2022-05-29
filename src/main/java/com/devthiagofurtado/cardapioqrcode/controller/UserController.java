package com.devthiagofurtado.cardapioqrcode.controller;


import com.devthiagofurtado.cardapioqrcode.data.vo.UsuarioVO;
import com.devthiagofurtado.cardapioqrcode.exception.InvalidJwtAuthenticationException;
import com.devthiagofurtado.cardapioqrcode.security.jwt.JwtTokenProvider;
import com.devthiagofurtado.cardapioqrcode.service.UserService;
import com.devthiagofurtado.cardapioqrcode.util.HeaderUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "UsersEndpoint")
@RestController
@RequestMapping("/user/me")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    private PagedResourcesAssembler<UsuarioVO> assembler;

    @Operation(summary = "Find infos for Word")
    @GetMapping(value = "/", produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<?> findMe() throws InvalidJwtAuthenticationException {
        String token = HeaderUtil.obterToken();
        String userName = "";
        if (StringUtils.hasText(token)) {
            tokenProvider.validateToken(token.substring(7, token.length()));
            userName = tokenProvider.getUsername(token.substring(7, token.length()));
        } else {
            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
        }

        var results = userService.findMe(userName);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }


    @Operation(summary = "Find favorites Word of User")
    @GetMapping(value = "/favorites", produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<?> findFavoritesMe(@RequestParam(value = "page", defaultValue = "0") int page) throws InvalidJwtAuthenticationException {
        String token = HeaderUtil.obterToken();
        String userName = "";
        if (StringUtils.hasText(token)) {
            tokenProvider.validateToken(token.substring(7, token.length()));
            userName = tokenProvider.getUsername(token.substring(7, token.length()));
        } else {
            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
        }

        Pageable pageable = PageRequest.of(page, 4, Sort.by(Sort.Direction.ASC, "added"));
        var results = userService.returnFavoritesUser(userName, pageable);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }


    @Operation(summary = "Find histories Word of User")
    @GetMapping(value = "/history", produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<?> findHistoryMe(@RequestParam(value = "page", defaultValue = "0") int page) throws InvalidJwtAuthenticationException {
        String token = HeaderUtil.obterToken();
        String userName = "";
        if (StringUtils.hasText(token)) {
            tokenProvider.validateToken(token.substring(7, token.length()));
            userName = tokenProvider.getUsername(token.substring(7, token.length()));
        } else {
            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
        }

        Pageable pageable = PageRequest.of(page, 4, Sort.by(Sort.Direction.ASC, "added"));
        var results = userService.returnHistoryUser(userName, pageable);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

}

