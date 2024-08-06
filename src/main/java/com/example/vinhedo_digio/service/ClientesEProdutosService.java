package com.example.vinhedo_digio.service;

import com.example.vinhedo_digio.client.ClientesComprasClient;
import com.example.vinhedo_digio.client.ProdutosClient;
import com.example.vinhedo_digio.model.Cliente;
import com.example.vinhedo_digio.model.ClientesECompras;
import com.example.vinhedo_digio.model.Produto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientesEProdutosService {
    private final ProdutosClient produtosClient;
    private final ClientesComprasClient comprasClient;

    public ClientesECompras obterClientesEProdutos() {
        List<Cliente> clientes = comprasClient.obterClientesCompras();
        List<Produto> produtos = produtosClient.obterProdutos();
        return new ClientesECompras(clientes, produtos);
    }

}
