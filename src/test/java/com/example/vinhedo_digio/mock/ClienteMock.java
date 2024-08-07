package com.example.vinhedo_digio.mock;

import com.example.vinhedo_digio.model.Cliente;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteMock {

    public static Cliente build() {
        return Cliente.builder()
                .cpf("12345678911")
                .nome("Pedro Henrique Silva de Alcântara")
                .compras(List.of(CompraMock.build()))
                .build();
    }
}
