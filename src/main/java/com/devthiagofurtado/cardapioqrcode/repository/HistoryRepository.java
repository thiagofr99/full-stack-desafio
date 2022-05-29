package com.devthiagofurtado.cardapioqrcode.repository;

import com.devthiagofurtado.cardapioqrcode.data.model.Favorite;
import com.devthiagofurtado.cardapioqrcode.data.model.History;
import com.devthiagofurtado.cardapioqrcode.data.model.Palavra;
import com.devthiagofurtado.cardapioqrcode.data.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

    @Query("SELECT h FROM History h WHERE h.word = :word and h.user = :user")
    Optional<History> findByPalavraAndUser(Palavra word, User user);

    @Query("SELECT h FROM History h WHERE h.user = :user")
    Page<History> findByUser(User user, Pageable pageable);
}
