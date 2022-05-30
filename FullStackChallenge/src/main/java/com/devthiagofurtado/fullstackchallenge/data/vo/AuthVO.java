package com.devthiagofurtado.fullstackchallenge.data.vo;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthVO extends RepresentationModel<AuthVO> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String token;

}
