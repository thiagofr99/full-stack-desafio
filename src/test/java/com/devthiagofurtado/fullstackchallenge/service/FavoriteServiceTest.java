package com.devthiagofurtado.fullstackchallenge.service;

import com.devthiagofurtado.fullstackchallenge.data.model.Favorite;
import com.devthiagofurtado.fullstackchallenge.data.model.Palavra;
import com.devthiagofurtado.fullstackchallenge.data.model.User;
import com.devthiagofurtado.fullstackchallenge.data.vo.PermissionVO;
import com.devthiagofurtado.fullstackchallenge.modelCreator.FavoriteModelCreator;
import com.devthiagofurtado.fullstackchallenge.modelCreator.UserModelCreator;
import com.devthiagofurtado.fullstackchallenge.modelCreator.WordModelCreator;
import com.devthiagofurtado.fullstackchallenge.repository.FavoriteRepository;
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
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
class FavoriteServiceTest {

    @InjectMocks
    private FavoriteService favoriteService;

    @Mock
    private FavoriteRepository favoriteRepository;

    @Mock
    private PalavraService palavraService;

    private final User user = UserModelCreator.cadastrado(null, UserModelCreator.permissions(PermissionVO.COMMON_USER), true);

    @BeforeEach
    void setUp_sucesso() {
        BDDMockito.when(favoriteRepository.save(ArgumentMatchers.any(Favorite.class)))
                .thenReturn(FavoriteModelCreator.favorite());

        BDDMockito.when(favoriteRepository.findByPalavraAndUser(ArgumentMatchers.any(Palavra.class), ArgumentMatchers.any(User.class)))
                .thenReturn(Optional.of(FavoriteModelCreator.favorite()));

        BDDMockito.when(favoriteRepository.findByUser(ArgumentMatchers.any(User.class), ArgumentMatchers.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(FavoriteModelCreator.favorite())));

        BDDMockito.when(favoriteRepository.save(ArgumentMatchers.any(Favorite.class)))
                .thenReturn(FavoriteModelCreator.favorite());

        BDDMockito.when(palavraService.findByWordExactly(ArgumentMatchers.anyString()))
                .thenReturn(WordModelCreator.word());

    }

    @Test
    void addFavorite_sucesso() {

        var palavra = "teste";

        favoriteService.addFavorite(palavra, user);

        var word = palavraService.findByWordExactly("teste");

        var favorite = favoriteRepository.findByPalavraAndUser(word, user).get();

        Assertions.assertThat(favorite.getWord().getPalavra()).isEqualTo(palavra);

    }

    @Test
    void unFavorite_sucesso() {

        var palavra = "teste";

        favoriteService.unFavorite("teste", user);

        var word = palavraService.findByWordExactly("teste");

        var favorite = favoriteRepository.findByPalavraAndUser(word, user).get();

        Assertions.assertThat(favorite.getWord().getPalavra()).isEqualTo(palavra);

    }

    @Test
    void findByUser() {
        Pageable pageable = PageRequest.of(1, 12);
        var user = UserModelCreator.cadastrado(null, UserModelCreator.permissions(PermissionVO.COMMON_USER), true);

        var teste = favoriteService.findByUser(user, pageable);

        Assertions.assertThat(teste).isNotNull();
        Assertions.assertThat(teste.get().collect(Collectors.toList()).get(0).getId()).isNotNull();
        Assertions.assertThat(teste.get().collect(Collectors.toList()).get(0).getUser()).isNotNull();

    }
}