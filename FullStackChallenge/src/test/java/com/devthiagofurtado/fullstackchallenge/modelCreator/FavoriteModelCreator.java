package com.devthiagofurtado.fullstackchallenge.modelCreator;

import com.devthiagofurtado.fullstackchallenge.data.model.Favorite;
import com.devthiagofurtado.fullstackchallenge.data.vo.PermissionVO;
import com.devthiagofurtado.fullstackchallenge.modelCreator.UserModelCreator;
import com.devthiagofurtado.fullstackchallenge.modelCreator.WordModelCreator;

import java.time.LocalDateTime;

public class FavoriteModelCreator {

    public static Favorite favorite(){
        return Favorite.builder()
                .id(1L)
                .added(LocalDateTime.now())
                .user(UserModelCreator.cadastrado(null,UserModelCreator.permissions(PermissionVO.COMMON_USER),true))
                .word(WordModelCreator.word())
                .build();
    }


}
