package com.example.vinhedo_digio.mock;

import com.example.vinhedo_digio.model.ClientesECompras;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientesEComprasMock {
    public static ClientesECompras build() {

        var clientes = List.of(ClienteMock.build());
        var compras = List.of(ProdutoMock.build());
        return new ClientesECompras(clientes, compras);
    }
}
