package com.devthiagofurtado.fullstackchallenge.modelCreator;

import com.devthiagofurtado.fullstackchallenge.data.model.Favorite;
import com.devthiagofurtado.fullstackchallenge.data.model.Palavra;
import com.devthiagofurtado.fullstackchallenge.data.vo.PermissionVO;
import com.devthiagofurtado.fullstackchallenge.modelCreator.UserModelCreator;

import java.time.LocalDateTime;

public class WordModelCreator {

    public static Palavra word(){
        return Palavra.builder()
                .id(1L)
                .palavra("teste")
                .build();
    }


}
