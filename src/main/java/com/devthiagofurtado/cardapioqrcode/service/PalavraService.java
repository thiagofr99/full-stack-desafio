package com.devthiagofurtado.cardapioqrcode.service;

import com.devthiagofurtado.cardapioqrcode.data.model.Palavra;
import com.devthiagofurtado.cardapioqrcode.data.vo.DictionaryVO;
import com.devthiagofurtado.cardapioqrcode.exception.ResourceNotFoundException;
import com.devthiagofurtado.cardapioqrcode.feign.FreeDictionaryFeign;
import com.devthiagofurtado.cardapioqrcode.repository.PalavraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class PalavraService {

    @Autowired
    private PalavraRepository palavraRepository;

    @Autowired
    private FreeDictionaryFeign freeDictionaryFeign;

    @Autowired
    private HistoryService historyService;

    public Map buscarPalavra(String palavra, Pageable pageable) {

        var page = palavraRepository.findAllByWord(palavra, pageable);

        Map<Object, Object> model = new HashMap<>();
        model.put("results", page.get().map(Palavra::getPalavra));
        model.put("totalDocs", page.getTotalElements());
        model.put("totalPages", page.getTotalPages());
        model.put("page", page.getNumber() + 1);
        model.put("hasNext", !page.isLast());
        model.put("hasPrev", !page.isFirst());

        return model;

    }

    public DictionaryVO buscarInfos(String palavra) {

        var word = palavraRepository.findByWord(palavra).orElseThrow(() -> new ResourceNotFoundException("Busca pela palavra não retornou dados"));

        historyService.salvarHistory(word);

        return freeDictionaryFeign.buscarPalavra(palavra).get(0);

    }

}
