package com.devthiagofurtado.fullstackchallenge.controller;


import com.devthiagofurtado.fullstackchallenge.data.vo.PageVO;
import com.devthiagofurtado.fullstackchallenge.data.vo.UserVO;
import com.devthiagofurtado.fullstackchallenge.data.vo.UsuarioVO;
import com.devthiagofurtado.fullstackchallenge.exception.InvalidJwtAuthenticationException;
import com.devthiagofurtado.fullstackchallenge.security.jwt.JwtTokenProvider;
import com.devthiagofurtado.fullstackchallenge.service.UserService;
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
    public ResponseEntity<UserVO> findMe() throws InvalidJwtAuthenticationException {
        String token = HeaderUtil.obterToken();
        String userName = "";
        if (StringUtils.hasText(token)) {
            tokenProvider.validateToken(token.substring(7, token.length()));
            userName = tokenProvider.getUsername(token.substring(7, token.length()));
        } else {
            throw new InvalidJwtAuthenticationException(UtilMensagem.EXPIRED_OR_INVALID_JWT_TOKEN);
        }

        var results = userService.findMe(userName);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }


    @Operation(summary = "Find favorites Word of User")
    @GetMapping(value = "/favorites", produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<PageVO> findFavoritesMe(@RequestParam(value = "page", defaultValue = "0") int page,
                                                  @RequestParam(value = "limit", defaultValue = "0") int limit) throws InvalidJwtAuthenticationException {
        String token = HeaderUtil.obterToken();
        String userName = "";
        if (StringUtils.hasText(token)) {
            tokenProvider.validateToken(token.substring(7, token.length()));
            userName = tokenProvider.getUsername(token.substring(7, token.length()));
        } else {
            throw new InvalidJwtAuthenticationException(UtilMensagem.EXPIRED_OR_INVALID_JWT_TOKEN);
        }

        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "added"));
        var results = userService.returnFavoritesUser(userName, pageable);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }


    @Operation(summary = "Find histories Word of User")
    @GetMapping(value = "/history", produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<PageVO> findHistoryMe(@RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "limit", defaultValue = "4") int limit) throws InvalidJwtAuthenticationException {
        String token = HeaderUtil.obterToken();
        String userName = "";
        if (StringUtils.hasText(token)) {
            tokenProvider.validateToken(token.substring(7, token.length()));
            userName = tokenProvider.getUsername(token.substring(7, token.length()));
        } else {
            throw new InvalidJwtAuthenticationException(UtilMensagem.EXPIRED_OR_INVALID_JWT_TOKEN);
        }

        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "added"));
        var results = userService.returnHistoryUser(userName, pageable);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

}

