package com.devthiagofurtado.cardapioqrcode.service;

import com.devthiagofurtado.cardapioqrcode.data.model.Favorite;
import com.devthiagofurtado.cardapioqrcode.data.model.History;
import com.devthiagofurtado.cardapioqrcode.data.model.Palavra;
import com.devthiagofurtado.cardapioqrcode.data.model.User;
import com.devthiagofurtado.cardapioqrcode.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepository;


    @Transactional(propagation = Propagation.REQUIRED)
    public void salvarHistory(Palavra palavra, User user) {
        var history = historyRepository.findByPalavraAndUser(palavra, user).orElse(new History());

        if (history.getId() == null) {
            history.setWord(palavra);
            history.setUser(user);
        }

        history.setAdded(LocalDateTime.now());

        historyRepository.save(history);

    }


    public Page<History> findByUser(User user, Pageable pageable) {
        return historyRepository.findByUser(user, pageable);
    }

}
