package com.devthiagofurtado.fullstackchallenge.controller;

import com.devthiagofurtado.fullstackchallenge.data.vo.PermissionVO;
import com.devthiagofurtado.fullstackchallenge.data.vo.UsuarioVO;
import com.devthiagofurtado.fullstackchallenge.modelCreator.DictionaryModelCreator;
import com.devthiagofurtado.fullstackchallenge.modelCreator.HttpServletResponseModelCreater;
import com.devthiagofurtado.fullstackchallenge.modelCreator.PageModelCreator;
import com.devthiagofurtado.fullstackchallenge.modelCreator.UserModelCreator;
import com.devthiagofurtado.fullstackchallenge.security.jwt.JwtTokenProvider;
import com.devthiagofurtado.fullstackchallenge.service.PalavraService;
import com.devthiagofurtado.fullstackchallenge.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EntriesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private EntriesController entriesController;

    @Mock
    private UserService userService;

    @Mock
    private PalavraService palavraService;

    @Mock
    JwtTokenProvider jwtTokenProvider;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    private PagedResourcesAssembler<UsuarioVO> assembler;

    @Autowired
    private ObjectMapper mapper;

    private final String BASE_URL = "/entries/en";

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    private HttpHeaders headers;

    private HttpServletResponse response;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(entriesController).build();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        headers.add("Authorization", "blablabla");


        Map<Object, Object> model = new HashMap<>();
        model.put("results", "teste");

        MockHttpServletResponse response = new MockHttpServletResponse();
        response.addHeader("tempoInicial", "123451453");
        response.addHeader("x-response-time", "");

        BDDMockito.when(userService.findByUserName(ArgumentMatchers.anyString()))
                .thenReturn(UserModelCreator.cadastrado(null, UserModelCreator.permissions(PermissionVO.ADMIN), true));

        BDDMockito.when(userService.loadUserByUsername(ArgumentMatchers.anyString()))
                .thenReturn(UserModelCreator.userDetails(UserModelCreator.cadastrado(null, UserModelCreator.permissions(PermissionVO.ADMIN), true)));

        BDDMockito.when(jwtTokenProvider.getUsername(ArgumentMatchers.anyString()))
                .thenReturn("teste");

        BDDMockito.when(userService.signUp(ArgumentMatchers.any(UsuarioVO.class)))
                .thenReturn(UserModelCreator.cadastrado(null, UserModelCreator.permissions(PermissionVO.ADMIN), true));

        BDDMockito.when(palavraService.buscarPalavra(ArgumentMatchers.anyString(), ArgumentMatchers.any(Pageable.class)))
                .thenReturn(PageModelCreator.pagePalavraVO());

        BDDMockito.when(palavraService.buscarInfos(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.any(HttpServletResponse.class)))
                .thenReturn(DictionaryModelCreator.dictionaryVO());

    }


    @Test
    void findAllByWord() throws Exception {
        LinkedMultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("search", "tes");
        param.add("limit", "4");
        param.add("page", "0");

        mockMvc.perform(get(BASE_URL + "/").headers(headers).params(param)).andExpect(status().isOk());
    }

    @Test
    void favoriteWord() throws Exception {

        mockMvc.perform(post(BASE_URL + "/teste/favorite").headers(headers)).andExpect(status().isNoContent());
    }

    @Test
    void unFavoriteWord() throws Exception {

        mockMvc.perform(delete(BASE_URL + "/teste/unfavorite").headers(headers)).andExpect(status().isNoContent());
    }

}