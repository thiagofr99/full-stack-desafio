package com.devthiagofurtado.fullstackchallenge.data.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class DefinitionsVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String definition;

    private String[] synonyms;

    private String[] antonyms;

    private String example;
}
