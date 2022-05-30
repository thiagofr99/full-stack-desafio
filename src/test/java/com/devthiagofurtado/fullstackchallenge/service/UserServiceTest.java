package com.devthiagofurtado.fullstackchallenge.service;

import com.devthiagofurtado.fullstackchallenge.data.model.User;
import com.devthiagofurtado.fullstackchallenge.data.vo.PermissionVO;
import com.devthiagofurtado.fullstackchallenge.modelCreator.HistoryModelCreator;
import com.devthiagofurtado.fullstackchallenge.modelCreator.UserModelCreator;
import com.devthiagofurtado.fullstackchallenge.modelCreator.FavoriteModelCreator;
import com.devthiagofurtado.fullstackchallenge.repository.UserRepository;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private FavoriteService favoriteService;

    @Mock
    private HistoryService historyService;

    @BeforeEach
    void setUp() {

        BDDMockito.when(userRepository.save(ArgumentMatchers.any(User.class)))
                .thenReturn(UserModelCreator.cadastrado(null, UserModelCreator.permissions(PermissionVO.ADMIN), true));

        BDDMockito.when(userRepository.findByUsername(ArgumentMatchers.anyString()))
                .thenReturn(UserModelCreator.cadastrado(null, UserModelCreator.permissions(PermissionVO.ADMIN), true));

        BDDMockito.when(userRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(UserModelCreator.cadastrado(null, UserModelCreator.permissions(PermissionVO.ADMIN), true)));

        BDDMockito.when(userDetailsService.loadUserByUsername(ArgumentMatchers.anyString()))
                .thenReturn(UserModelCreator.userDetails(UserModelCreator.cadastrado(null, UserModelCreator.permissions(PermissionVO.ADMIN), true)));

        BDDMockito.when(favoriteService.findByUser(ArgumentMatchers.any(User.class),ArgumentMatchers.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(FavoriteModelCreator.favorite())));

        BDDMockito.when(historyService.findByUser(ArgumentMatchers.any(User.class),ArgumentMatchers.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(HistoryModelCreator.history())));
    }

    @Test
    void loadUserByUsername_retornaUserDetails_sucesso() {

        var teste = userService.loadUserByUsername("teste");
        Assertions.assertThat(teste).isNotNull();
        Assertions.assertThat(teste.getAuthorities()).isNotNull().isNotEmpty();
    }

    @Test
    void loadUserByUsername_retornaUserNameNotFoundException_erro() {

        BDDMockito.when(userRepository.findByUsername(ArgumentMatchers.anyString()))
                .thenReturn(null);

        Assertions.assertThatThrownBy(() -> userService.loadUserByUsername("teste"))
                .isInstanceOf(UsernameNotFoundException.class);
    }

    @Test
    void signUp_retornaUsuarioVO_sucesso() {
        var permissions = Collections.singletonList(PermissionVO.ADMIN);
        var teste = userService.signUp(UserModelCreator.vo(permissions, true, false));

        Assertions.assertThat(teste).isNotNull();
        Assertions.assertThat(teste.getId()).isNotNull();

    }

    @Test
    void findByUserName_retornaUser_sucesso() {

        var teste = userService.findByUserName("teste");

        Assertions.assertThat(teste).isNotNull();
        Assertions.assertThat(teste.getId()).isNotNull();

    }

    @Test
    void findMe_retornaUserVO_sucesso() {
        var teste = userService.findMe("teste");

        Assertions.assertThat(teste).isNotNull();
        Assertions.assertThat(teste.getFullName()).isNotNull();
    }

    @Test
    void returnFavoritesUser_retornaPageVO_sucesso() {

        Pageable pageable = PageRequest.of(1, 12);

        var teste = userService.returnFavoritesUser("teste",pageable);

        Assertions.assertThat(teste).isNotNull();
        Assertions.assertThat(teste.getResults()).isNotNull();

    }

    @Test
    void returnHistoryUser_retornaPageVO_sucesso() {

        Pageable pageable = PageRequest.of(1, 12);

        var teste = userService.returnHistoryUser("teste",pageable);

        Assertions.assertThat(teste).isNotNull();
        Assertions.assertThat(teste.getResults()).isNotNull();

    }

}