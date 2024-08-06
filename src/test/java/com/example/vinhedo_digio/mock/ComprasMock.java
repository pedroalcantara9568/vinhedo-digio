package com.example.vinhedo_digio.mock;

import com.example.vinhedo_digio.model.Compras;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ComprasMock {


    public static Compras build() {
        return Compras.builder()
                .ano_compra(2020L)
                .preco(BigDecimal.TEN)
                .codigo(1L)
                .quantidade(5)
                .safra("2014")
                .tipo_vinho("Tinto")
                .build();

    }
}
