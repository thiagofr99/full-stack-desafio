package com.devthiagofurtado.fullstackchallenge.repository;

import com.devthiagofurtado.fullstackchallenge.data.model.History;
import com.devthiagofurtado.fullstackchallenge.data.model.Palavra;
import com.devthiagofurtado.fullstackchallenge.data.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

    @Query("SELECT h FROM History h WHERE h.word = :word and h.user = :user")
    Optional<History> findByPalavraAndUser(Palavra word, User user);

    @Query("SELECT h FROM History h WHERE h.user = :user")
    Page<History> findByUser(User user, Pageable pageable);
}
