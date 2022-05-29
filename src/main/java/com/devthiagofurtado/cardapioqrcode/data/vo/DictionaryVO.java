package com.devthiagofurtado.cardapioqrcode.data.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String word;

    private String phonetic;

    private List<PhoneticsVO> phonetics;

    private List<MeaningsVo> meanings;

    private LicenseVO license;

    private String[] sourceUrls;

    @JsonIgnore
    private String cache;

}
