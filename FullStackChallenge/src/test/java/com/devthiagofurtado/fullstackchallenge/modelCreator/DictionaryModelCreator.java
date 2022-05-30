package com.devthiagofurtado.fullstackchallenge.modelCreator;

import com.devthiagofurtado.fullstackchallenge.data.vo.DefinitionsVO;
import com.devthiagofurtado.fullstackchallenge.data.vo.DictionaryVO;
import com.devthiagofurtado.fullstackchallenge.data.vo.LicenseVO;
import com.devthiagofurtado.fullstackchallenge.data.vo.MeaningsVO;

import java.util.Collections;

public class DictionaryModelCreator {

    static String[] teste = {"teste", "teste2"};

    static DefinitionsVO definitionsVO = DefinitionsVO.builder()
            .definition("teste")
            .example("teste")
            .synonyms(teste)
            .antonyms(teste)
            .build();

    static MeaningsVO meaningsVo = MeaningsVO.builder()
            .definitions(Collections.singletonList(
                    definitionsVO
            ))
            .synonyms(teste)
            .antonyms(teste)
            .build();

    public static DictionaryVO dictionaryVO() {
        return DictionaryVO.builder()
                .word("teste")
                .license(
                        LicenseVO.builder()
                                .name("teste")
                                .url("teste")
                                .build()
                )
                .meanings(Collections.singletonList(meaningsVo))

                .build();
    }


}
