package com.devthiagofurtado.fullstackchallenge.feign;

import com.devthiagofurtado.fullstackchallenge.data.vo.DictionaryVO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "api-dictionaryapi.dev", url = "https://api.dictionaryapi.dev/api/v2/entries/en/")
public interface FreeDictionaryFeign {

    @Cacheable("dictionary")
    @GetMapping(value = "/{word}")
    List<DictionaryVO> buscarPalavra(@PathVariable("word") String word);

}
