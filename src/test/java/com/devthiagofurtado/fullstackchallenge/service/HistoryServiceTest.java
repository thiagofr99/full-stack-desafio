package com.devthiagofurtado.fullstackchallenge.service;

import com.devthiagofurtado.fullstackchallenge.data.model.User;
import com.devthiagofurtado.fullstackchallenge.data.vo.PermissionVO;
import com.devthiagofurtado.fullstackchallenge.modelCreator.HistoryModelCreator;
import com.devthiagofurtado.fullstackchallenge.modelCreator.UserModelCreator;
import com.devthiagofurtado.fullstackchallenge.modelCreator.WordModelCreator;
import com.devthiagofurtado.fullstackchallenge.repository.HistoryRepository;
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
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
class HistoryServiceTest {

    @InjectMocks
    private HistoryService historyService;

    @Mock
    private HistoryRepository historyRepository;

    private final User user = UserModelCreator.cadastrado(null, UserModelCreator.permissions(PermissionVO.COMMON_USER), true);

    @BeforeEach
    void setUp() {

        BDDMockito.when(historyRepository.findByUser(ArgumentMatchers.any(User.class), ArgumentMatchers.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(HistoryModelCreator.history())));

    }

    @Test
    void salvarHistory_sucesso() {

        var palavra = WordModelCreator.word();

        historyService.salvarHistory(palavra, user);

        Assertions.assertThat(palavra.getPalavra()).isNotNull();
        Assertions.assertThat(palavra.getId()).isNotNull();

    }

    @Test
    void findByUser_sucesso() {
        Pageable pageable = PageRequest.of(1, 12);

        var page = historyService.findByUser(user, pageable);

        Assertions.assertThat(page).isNotNull().isNotEmpty();
        Assertions.assertThat(page.get().collect(Collectors.toList()).get(0).getId()).isNotNull();

    }
}