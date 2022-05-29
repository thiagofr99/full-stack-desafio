package com.devthiagofurtado.cardapioqrcode.data.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.dozermapper.core.Mapping;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserVO extends RepresentationModel<UserVO> implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("name")
    private String fullName;

    private Boolean enabled;

}
