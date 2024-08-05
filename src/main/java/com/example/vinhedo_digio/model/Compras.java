package com.example.vinhedo_digio.model;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Compras implements Serializable {

    @Serial
    private static final long serialVersionUID = 2953121052185664266L;
    private Long codigo;
    private String tipo_vinho;
    private BigDecimal preco;
    private Integer quantidade;
    private String safra;
}
