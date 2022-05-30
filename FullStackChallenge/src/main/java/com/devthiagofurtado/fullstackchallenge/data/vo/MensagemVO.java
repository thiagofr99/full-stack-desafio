package com.devthiagofurtado.fullstackchallenge.data.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MensagemVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("Message")
    private String mensagem;

}
