package com.example.vinhedo_digio.service;

import com.example.vinhedo_digio.client.ClientesComprasClient;
import com.example.vinhedo_digio.client.ProdutosClient;
import com.example.vinhedo_digio.model.Clientes;
import com.example.vinhedo_digio.model.Compras;
import com.example.vinhedo_digio.model.MaioresComprasDTO;
import com.example.vinhedo_digio.model.Produto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ComprasService {
    private final ProdutosClient produtosClient;
    private final ClientesComprasClient client;


    public List<MaioresComprasDTO> obterMaioresCompras() {
        var clientes = client.obterClientesCompras();
        var produtos = produtosClient.obterProdutos();

        var maioresComprasDTOS = obterMaioresComprasDetalhadas(clientes, produtos);

        return maioresComprasDTOS.stream()
                .sorted(Comparator.comparing(MaioresComprasDTO::getValorTotal).reversed())
                .toList();
    }

    private ArrayList<MaioresComprasDTO> obterMaioresComprasDetalhadas(List<Clientes> clientesCompras, List<Produto> produtos) {
        var listaMaiores = new ArrayList<MaioresComprasDTO>();
        clientesCompras.forEach(cliente -> {
            var valorTotal = obterValorTotal(cliente, produtos);
            var compras = obterCompras(cliente, produtos);
            listaMaiores.add(MaioresComprasDTO.builder()
                    .nome(cliente.getNome())
                    .cpf(cliente.getCpf())
                    .compras(compras)
                    .valorTotal(valorTotal).build());
        });
        return listaMaiores;
    }

    private List<Compras> obterCompras(Clientes clienteCompra, List<Produto> produtos) {
        return clienteCompra.getCompras().stream()
                .flatMap(compra -> produtos.stream()
                        .filter(produto -> Objects.equals(produto.getCodigo(), Long.valueOf(compra.getCodigo())))
                        .map(produto -> Compras.builder()
                                .codigo(produto.getCodigo())
                                .safra(produto.getSafra())
                                .preco(produto.getPreco())
                                .tipo_vinho(produto.getTipoVinho())
                                .quantidade(compra.getQuantidade())
                                .build()))
                .toList();
    }

    private BigDecimal obterValorTotal(Clientes clientes, List<Produto> produtos) {
        return clientes.getCompras().stream()
                .map(compra -> produtos.stream()
                        .filter(produto -> Objects.equals(produto.getCodigo(), Long.valueOf(compra.getCodigo())))
                        .map(produto -> produto.getPreco().multiply(BigDecimal.valueOf(compra.getQuantidade())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
