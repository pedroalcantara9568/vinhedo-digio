package com.example.vinhedo_digio.mock;

import com.example.vinhedo_digio.model.MaioresComprasDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MaioresComprasDTOMock {


    public static MaioresComprasDTO build() {
        return MaioresComprasDTO.builder()
                .cpf("12345678911")
                .nome("Pedro Henrique Silva de Alcântara")
                .compras(List.of(ComprasMock.build()))
                .valorTotal(BigDecimal.valueOf(50L))
                .build();
    }
}
