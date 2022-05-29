package com.devthiagofurtado.cardapioqrcode.data.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DefinitionsVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String definition;

    private String[] synonyms;

    private String[] antonyms;

    private String example;
}
