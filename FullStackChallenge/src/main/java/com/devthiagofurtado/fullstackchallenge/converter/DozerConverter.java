package com.devthiagofurtado.fullstackchallenge.converter;

import com.devthiagofurtado.fullstackchallenge.data.model.Permission;
import com.devthiagofurtado.fullstackchallenge.data.model.User;
import com.devthiagofurtado.fullstackchallenge.data.vo.PermissionVO;
import com.devthiagofurtado.fullstackchallenge.data.vo.UsuarioVO;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DozerConverter {

    private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    public static <O, D> D parseObject(O origin, Class<D> destination) {

        return mapper.map(origin, destination);
    }

    public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {

        return origin.stream().map(o -> mapper.map(o, destination)).collect(Collectors.toList());

    }

    public static <O, D> Page<D> parsePageObjects(Page<O> origin, Class<D> destination) {

        return new PageImpl<>(origin.stream().map(o -> mapper.map(o, destination)).collect(Collectors.toList()));

    }

    public static User parseUsuarioVOtoUser(UsuarioVO user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(16);
        String result = bCryptPasswordEncoder.encode(user.getPassword());
        User user1 = new User();
        user1.setId(user.getKey() == null || user.getKey() == 0 ? null : user.getKey());
        user1.setUserName(user.getUserName());
        user1.setFullName(user.getFullName());
        user1.setPassword(result);
        user1.setAccountNonExpired(true);
        user1.setAccountNonLocked(true);
        user1.setCredentialsNonExpired(true);
        user1.setDateLicense(null);
        user1.setPermissions(
                Collections.singletonList(DozerConverter.permissionVOToEntity(PermissionVO.COMMON_USER))
        );
        user1.setEnabled(true);
        return user1;
    }

    public static UsuarioVO parseUsertoVO(User user) {
        return UsuarioVO.builder()
                .key(user.getId())
                .userName(user.getUserName())
                .fullName(user.getFullName())
                .permissions(
                        Collections.singletonList(PermissionVO.COMMON_USER)
                )
                .enabled(true)
                .build();
    }

    public static Permission permissionVOToEntity(PermissionVO vo) {
        Permission permission = new Permission();
        permission.setId(vo.getCodigo().longValue());
        permission.setDescription(vo.name());
        return permission;
    }
}
