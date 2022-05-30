package com.devthiagofurtado.fullstackchallenge.service;

import com.devthiagofurtado.fullstackchallenge.data.model.Palavra;
import com.devthiagofurtado.fullstackchallenge.data.model.User;
import com.devthiagofurtado.fullstackchallenge.data.vo.PermissionVO;
import com.devthiagofurtado.fullstackchallenge.feign.FreeDictionaryFeign;
import com.devthiagofurtado.fullstackchallenge.modelCreator.*;
import com.devthiagofurtado.fullstackchallenge.repository.FavoriteRepository;
import com.devthiagofurtado.fullstackchallenge.repository.PalavraRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class PalavraServiceTest {

    @InjectMocks
    private PalavraService palavraService;

    @Mock
    private PalavraRepository palavraRepository;

    @Mock
    private FreeDictionaryFeign freeDictionaryFeign;

    @Mock
    private UserService userService;

    @Mock
    private HistoryService historyService;

    @Mock
    private FavoriteService favoriteService;

    @Mock
    private FavoriteRepository favoriteRepository;

    private final User user = UserModelCreator.cadastrado(null,UserModelCreator.permissions(PermissionVO.COMMON_USER),true);

    @BeforeEach
    void setUp() {

        BDDMockito.when(palavraRepository.save(ArgumentMatchers.any()))
                .thenReturn(WordModelCreator.word());

        BDDMockito.when(palavraRepository.findByWord(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(WordModelCreator.word()));

        BDDMockito.when(palavraRepository.findAllByWord(ArgumentMatchers.anyString(), ArgumentMatchers.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(WordModelCreator.word())));

        BDDMockito.when(freeDictionaryFeign.buscarPalavra(ArgumentMatchers.anyString()))
                .thenReturn(Collections.singletonList(DictionaryModelCreator.dictionaryVO()));

        BDDMockito.when(userService.findByUserName(ArgumentMatchers.anyString()))
                .thenReturn(user);

        BDDMockito.doNothing().when(historyService).salvarHistory(ArgumentMatchers.any(Palavra.class),ArgumentMatchers.any(User.class));

        BDDMockito.doNothing().when(favoriteService).addFavorite("teste", user);

        BDDMockito.doNothing().when(favoriteService).unFavorite("teste", user);

        BDDMockito.when(favoriteRepository.findByPalavraAndUser(ArgumentMatchers.any(Palavra.class),ArgumentMatchers.any(User.class)))
                .thenReturn(Optional.of(FavoriteModelCreator.favorite()));
    }

    @Test
    void buscarPalavra_sucesso() {
        Pageable pageable = PageRequest.of(1, 12);

        var teste = palavraService.buscarPalavra("teste", pageable);

        Assertions.assertThat(teste).isNotNull();
        Assertions.assertThat(teste.getPage()).isNotZero();
        Assertions.assertThat(teste.getResults()).isNotNull();

    }

    @Test
    void buscarInfos_sucesso() {

        var teste = palavraService.buscarInfos("teste", "teste",HttpServletResponseModelCreater.httpServletResponse());

        Assertions.assertThat(teste).isNotNull();

    }

    @Test
    void findByWordExactly_sucesso() {

        var teste = palavraService.findByWordExactly("teste");

        Assertions.assertThat(teste).isNotNull();

    }

    @Test
    void favoriteWord_sucesso() {

        palavraService.favoriteWord("teste","teste");

        var fav = favoriteRepository.findByPalavraAndUser(WordModelCreator.word(),user).get();

        Assertions.assertThat(fav.getWord().getPalavra()).isEqualTo("teste");

    }

    @Test
    void unFavoriteWord_sucesso() {
        BDDMockito.when(favoriteRepository.findByPalavraAndUser(ArgumentMatchers.any(Palavra.class),ArgumentMatchers.any(User.class)))
                .thenReturn(Optional.empty());

        palavraService.unFavoriteWord("teste","teste");

        var fav = favoriteRepository.findByPalavraAndUser(WordModelCreator.word(),user);

        Assertions.assertThat(fav).isNotPresent();
    }
}