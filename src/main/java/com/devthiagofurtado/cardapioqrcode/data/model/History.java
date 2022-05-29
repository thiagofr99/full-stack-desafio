package com.devthiagofurtado.cardapioqrcode.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "history")
public class History implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private LocalDateTime added;

    @ManyToOne
    @JoinColumn(name = "palavra_id")
    private Palavra word;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
