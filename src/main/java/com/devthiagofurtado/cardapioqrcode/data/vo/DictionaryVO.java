package com.devthiagofurtado.cardapioqrcode.data.vo;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryVO {

    private String word;

    private String phonetic;

    private List<PhoneticsVO> phonetics;

    private List<MeaningsVo> meanings;

    private LicenseVO license;

    private String[] sourceUrls;

}
