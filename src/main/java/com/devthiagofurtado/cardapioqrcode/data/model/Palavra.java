package com.devthiagofurtado.cardapioqrcode.data.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tab_palavra")
public class Palavra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "palavra")
    private String palavra;

}
