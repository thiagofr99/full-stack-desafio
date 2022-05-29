package com.devthiagofurtado.cardapioqrcode.data.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"word", "added"})
public class ResultVO implements Serializable {

    private static final long serialVersionUID = 1L;

    String word;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    LocalDateTime added;
}
