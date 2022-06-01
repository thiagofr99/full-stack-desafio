package com.devthiagofurtado.fullstackchallenge.repository;

import com.devthiagofurtado.fullstackchallenge.data.model.Palavra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PalavraRepository extends JpaRepository<Palavra, Long> {


    @Query(nativeQuery = true, value = "SELECT * FROM tab_palavras WHERE palavra LIKE CONCAT('%', ?1 ,'%') AND palavra REGEXP '[A-Z][AEIOU]' AND palavra NOT LIKE CONCAT('%', ' ' ,'%') AND palavra NOT LIKE CONCAT('%', '-' ,'%')")
    Page<Palavra> findAllByWord(String palavra, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM tab_palavras WHERE palavra = ?1 ")
    Optional<Palavra> findByWord(String palavra);
}
