package com.devthiagofurtado.fullstackchallenge.data.vo;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LicenseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String url;

}
