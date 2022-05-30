package com.devthiagofurtado.fullstackchallenge.data.vo;

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
public class UsuarioVO extends RepresentationModel<UsuarioVO> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Mapping("id")
    @JsonProperty("id")
    private Long key;

    @JsonProperty("email")
    private String userName;

    @JsonProperty("name")
    private String fullName;

    private String password;

    private Boolean enabled;

    private List<PermissionVO> permissions;

}
