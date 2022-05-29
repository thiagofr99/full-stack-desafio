package com.devthiagofurtado.cardapioqrcode.data.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean hasPrev;

    private int totalPages;

    private Long totalDocs;

    private boolean hasNext;

    private int page;

    private List<ResultVO> results;

}
