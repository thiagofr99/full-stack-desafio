package com.devthiagofurtado.cardapioqrcode.service;

import com.devthiagofurtado.cardapioqrcode.converter.DozerConverter;
import com.devthiagofurtado.cardapioqrcode.data.model.User;
import com.devthiagofurtado.cardapioqrcode.data.vo.PageVO;
import com.devthiagofurtado.cardapioqrcode.data.vo.ResultVO;
import com.devthiagofurtado.cardapioqrcode.data.vo.UserVO;
import com.devthiagofurtado.cardapioqrcode.data.vo.UsuarioVO;
import com.devthiagofurtado.cardapioqrcode.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FavoriteService favoriteService;

    @Autowired
    HistoryService historyService;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("Username " + username + " not found");
        }

    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public User signin(UsuarioVO user) {

        return userRepository.save(DozerConverter.parseUsuarioVOtoUser(user));

    }

    public User findByUserName(String userName) {
        return userRepository.findByUsername(userName);
    }

    public UserVO findMe(String userName) {
        return DozerConverter.parseObject(userRepository.findByUsername(userName), UserVO.class);
    }


    public PageVO returnFavoritesUser(String userName, Pageable pageable) {
        var user = userRepository.findByUsername(userName);
        var page = favoriteService.findByUser(user, pageable);

        List<ResultVO> results = new ArrayList<>();
        var result = page.get().collect(Collectors.toList());

        result.forEach(f -> {
            results.add(
                    ResultVO.builder()
                            .word(f.getWord().getPalavra())
                            .added(f.getAdded())
                            .build()
            );
        });

        return PageVO.builder()
                .totalPages(page.getTotalPages())
                .hasNext(!page.isLast())
                .hasPrev(!page.isFirst())
                .page(page.getNumber() + 1)
                .totalDocs(page.getTotalElements())
                .results(results)
                .build();
    }


    public PageVO returnHistoryUser(String userName, Pageable pageable) {
        var user = userRepository.findByUsername(userName);
        var page = historyService.findByUser(user, pageable);

        List<ResultVO> results = new ArrayList<>();
        var result = page.get().collect(Collectors.toList());

        result.forEach(h -> {
            results.add(
                    ResultVO.builder()
                            .word(h.getWord().getPalavra())
                            .added(h.getAdded())
                            .build()
            );
        });

        return PageVO.builder()
                .totalPages(page.getTotalPages())
                .hasNext(!page.isLast())
                .hasPrev(!page.isFirst())
                .page(page.getNumber() + 1)
                .totalDocs(page.getTotalElements())
                .results(results)
                .build();
    }

}
