package com.devthiagofurtado.cardapioqrcode.data.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeaningsVo {

    private String partOfSpeech;

    private List<DefinitionsVO> definitions;

    private String[] synonyms;

    private String[] antonyms;

}
