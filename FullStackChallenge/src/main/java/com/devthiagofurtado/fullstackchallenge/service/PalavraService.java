package com.devthiagofurtado.fullstackchallenge.service;

import com.devthiagofurtado.fullstackchallenge.data.model.Palavra;
import com.devthiagofurtado.fullstackchallenge.data.vo.DictionaryVO;
import com.devthiagofurtado.fullstackchallenge.data.vo.PagePalavraVO;
import com.devthiagofurtado.fullstackchallenge.exception.ResourceNotFoundException;
import com.devthiagofurtado.fullstackchallenge.feign.FreeDictionaryFeign;
import com.devthiagofurtado.fullstackchallenge.repository.PalavraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;


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

    public PagePalavraVO buscarPalavra(String palavra, Pageable pageable) {

        var page = palavraRepository.findAllByWord(palavra, pageable);

        return PagePalavraVO.builder()
                .results(page.get().map(Palavra::getPalavra).collect(Collectors.toList()))
                .totalDocs(page.getTotalElements())
                .hasNext(!page.isLast())
                .hasPrev(!page.isFirst())
                .page(page.getNumber() + 1)
                .totalPages(page.getTotalPages())
                .build();


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
