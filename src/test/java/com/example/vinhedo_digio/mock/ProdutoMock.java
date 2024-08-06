package com.example.vinhedo_digio.mock;

import com.example.vinhedo_digio.model.Produto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProdutoMock {

    public static Produto build() {
        return Produto.builder()
                .codigo(1L)
                .anoCompra(2020L)
                .preco(BigDecimal.TEN)
                .safra("2014")
                .tipoVinho("Tinto")
                .build();
    }
}
