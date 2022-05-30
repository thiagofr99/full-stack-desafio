package com.devthiagofurtado.fullstackchallenge.modelCreator;

import com.devthiagofurtado.fullstackchallenge.data.vo.PagePalavraVO;
import com.devthiagofurtado.fullstackchallenge.data.vo.PageVO;
import com.devthiagofurtado.fullstackchallenge.data.vo.ResultVO;

import java.time.LocalDateTime;
import java.util.Collections;

public class PageModelCreator {

    public static PageVO page() {
        return PageVO.builder()
                .hasNext(false)
                .hasPrev(false)
                .page(1)
                .results(
                        Collections.singletonList(
                                ResultVO.builder()
                                        .word("teste")
                                        .added(LocalDateTime.now())
                                        .build()
                        )
                )
                .build();
    }

    public static PagePalavraVO pagePalavraVO() {
        return PagePalavraVO.builder()
                .hasNext(false)
                .hasPrev(false)
                .page(1)
                .results(
                        Collections.singletonList("teste")
                )
                .build();
    }


}
