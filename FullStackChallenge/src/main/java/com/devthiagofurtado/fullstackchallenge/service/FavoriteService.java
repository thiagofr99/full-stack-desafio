package com.devthiagofurtado.fullstackchallenge.service;

import com.devthiagofurtado.fullstackchallenge.data.model.Favorite;
import com.devthiagofurtado.fullstackchallenge.data.model.User;
import com.devthiagofurtado.fullstackchallenge.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private PalavraService palavraService;

    @Transactional(propagation = Propagation.REQUIRED)
    public void addFavorite(String palavra, User user) {
        var word = palavraService.findByWordExactly(palavra);
        var favorite = favoriteRepository.findByPalavraAndUser(word, user).orElse(new Favorite());

        if (favorite.getId() == null) {
            favorite.setWord(word);
            favorite.setUser(user);
        }

        favorite.setAdded(LocalDateTime.now());

        favoriteRepository.save(favorite);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void unFavorite(String palavra, User user) {
        var word = palavraService.findByWordExactly(palavra);
        var favorite = favoriteRepository.findByPalavraAndUser(word, user).orElse(new Favorite());

        favoriteRepository.delete(favorite);

    }

    public Page<Favorite> findByUser(User user, Pageable pageable) {
        return favoriteRepository.findByUser(user, pageable);
    }

}
