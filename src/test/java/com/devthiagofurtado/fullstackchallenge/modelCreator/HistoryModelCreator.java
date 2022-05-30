package com.devthiagofurtado.fullstackchallenge.modelCreator;

import com.devthiagofurtado.fullstackchallenge.data.model.History;
import com.devthiagofurtado.fullstackchallenge.data.vo.PermissionVO;

import java.time.LocalDateTime;

public class HistoryModelCreator {

    public static History history() {
        return History.builder()
                .id(1L)
                .added(LocalDateTime.now())
                .user(UserModelCreator.cadastrado(null, UserModelCreator.permissions(PermissionVO.COMMON_USER), true))
                .word(WordModelCreator.word())
                .build();
    }


}
