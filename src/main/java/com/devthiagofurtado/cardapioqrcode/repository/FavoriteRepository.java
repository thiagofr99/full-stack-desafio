package com.devthiagofurtado.cardapioqrcode.repository;

import com.devthiagofurtado.cardapioqrcode.data.model.Favorite;
import com.devthiagofurtado.cardapioqrcode.data.model.Palavra;
import com.devthiagofurtado.cardapioqrcode.data.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    @Query("SELECT f FROM Favorite f WHERE f.word = :word and f.user = :user")
    Optional<Favorite> findByPalavraAndUser(Palavra word, User user);

    @Query("SELECT f FROM Favorite f WHERE f.user = :user")
    Page<Favorite> findByUser(User user, Pageable pageable);
}
