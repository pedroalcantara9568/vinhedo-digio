package com.example.vinhedo_digio.service;

import com.example.vinhedo_digio.client.ClientesComprasClient;
import com.example.vinhedo_digio.client.ProdutosClient;
import com.example.vinhedo_digio.model.ClientesECompras;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientesEProdutosService {
    private final ProdutosClient produtosClient;
    private final ClientesComprasClient comprasClient;

    public ClientesECompras obterClientesEProdutos() {
        var clientes = comprasClient.obterClientesCompras();
        var produtos = produtosClient.obterProdutos();
        return new ClientesECompras(clientes, produtos);
    }

}
