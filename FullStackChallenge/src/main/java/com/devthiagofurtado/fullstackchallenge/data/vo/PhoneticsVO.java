package com.devthiagofurtado.fullstackchallenge.data.vo;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhoneticsVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String text;

    private String audio;

    private String sourceUrl;

    private LicenseVO license;

}
