package com.example.vinhedo_digio.model;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MaioresCompras implements Serializable {

    @Serial
    private static final long serialVersionUID = 4032213499797371384L;
    private String nome;
    private String cpf;
    private List<Compras> compras;
    private BigDecimal valorTotal;

}
