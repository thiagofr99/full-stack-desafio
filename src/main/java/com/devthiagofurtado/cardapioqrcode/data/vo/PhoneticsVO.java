package com.devthiagofurtado.cardapioqrcode.data.vo;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhoneticsVO {

    private String text;

    private String audio;

    private String sourceUrl;

    private LicenseVO license;

}
