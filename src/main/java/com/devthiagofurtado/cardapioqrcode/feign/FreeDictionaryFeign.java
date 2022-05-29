package com.devthiagofurtado.cardapioqrcode.feign;

import com.devthiagofurtado.cardapioqrcode.data.vo.DictionaryVO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "api-dictionaryapi.dev", url = "https://api.dictionaryapi.dev/api/v2/entries/en/")
public interface FreeDictionaryFeign {

    @Cacheable("dictionary")
    @RequestMapping(value = "/{word}", method = RequestMethod.GET)
    List<DictionaryVO> buscarPalavra(@PathVariable("word") String word);

}
