package com.devthiagofurtado.cardapioqrcode.service;

import com.devthiagofurtado.cardapioqrcode.data.model.History;
import com.devthiagofurtado.cardapioqrcode.data.model.Palavra;
import com.devthiagofurtado.cardapioqrcode.data.vo.DictionaryVO;
import com.devthiagofurtado.cardapioqrcode.feign.FreeDictionaryFeign;
import com.devthiagofurtado.cardapioqrcode.repository.HistoryRepository;
import com.devthiagofurtado.cardapioqrcode.repository.PalavraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Service
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepository;



    @Transactional(propagation = Propagation.REQUIRED)
    public void salvarHistory(Palavra palavra) {
        var history = historyRepository.findByPalavra(palavra).orElse(new History());

        if(history.getId()==null){
            history.setWord(palavra);
        }
        history.setAdded(LocalDateTime.now());

        historyRepository.save(history);

    }

}
