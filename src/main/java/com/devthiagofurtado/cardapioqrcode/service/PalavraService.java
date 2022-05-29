package com.devthiagofurtado.cardapioqrcode.service;

import com.devthiagofurtado.cardapioqrcode.data.model.Palavra;
import com.devthiagofurtado.cardapioqrcode.data.vo.DictionaryVO;
import com.devthiagofurtado.cardapioqrcode.exception.ResourceNotFoundException;
import com.devthiagofurtado.cardapioqrcode.feign.FreeDictionaryFeign;
import com.devthiagofurtado.cardapioqrcode.repository.PalavraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private UserService userService;

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

    @Cacheable(value = "infos", key = "#palavra")
    public DictionaryVO buscarInfos(String palavra, String userName, HttpServletResponse headers) {

        var user = userService.findByUserName(userName);

        var word = this.findByWordExactly(palavra);

        historyService.salvarHistory(word, user);

        headers.setHeader("x-cache", "MISS");

        return freeDictionaryFeign.buscarPalavra(palavra).get(0);

    }

    public Palavra findByWordExactly(String palavra) {
        return palavraRepository.findByWord(palavra).orElseThrow(() -> new ResourceNotFoundException("Busca pela palavra não retornou dados"));
    }

    public void favoriteWord(String word, String userName) {
        var user = userService.findByUserName(userName);
        favoriteService.addFavorite(word, user);

    }

    public void unFavoriteWord(String word, String userName) {
        var user = userService.findByUserName(userName);
        favoriteService.unFavorite(word, user);

    }

}
