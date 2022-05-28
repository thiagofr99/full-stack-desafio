package com.devthiagofurtado.cardapioqrcode.data.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DefinitionsVO {

    private String definition;

    private String[] synonyms;

    private String[] antonyms;

    private String example;
}
