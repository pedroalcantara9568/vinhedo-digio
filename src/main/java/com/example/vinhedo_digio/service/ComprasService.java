package com.example.vinhedo_digio.service;

import com.example.vinhedo_digio.client.ClientesComprasClient;
import com.example.vinhedo_digio.client.ProdutosClient;
import com.example.vinhedo_digio.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ComprasService {
    private final ProdutosClient produtosClient;
    private final ClientesComprasClient client;

    public List<MaioresComprasDTO> obterMaioresCompras() {
        var result = obterClientesEProdutos();

        List<MaioresComprasDTO> maioresComprasDTOS = obterMaioresComprasDetalhadas(result.clientes(), result.produtos());

        return maioresComprasDTOS.stream()
                .sorted(Comparator.comparing(MaioresComprasDTO::getValorTotal).reversed())
                .toList();
    }

    public MaioresComprasDTO obterMaiorCompraPorAno(Long ano) {
        var clientesEProdutos = obterClientesEProdutos();

        return clientesEProdutos.clientes().stream()
                .flatMap(cliente -> cliente.getCompras().stream()
                        .map(compra -> criarMaioresComprasDTOSeAnoIgual(ano, cliente, compra, clientesEProdutos.produtos()))
                        .filter(Optional::isPresent)
                        .map(Optional::get))
                .max(Comparator.comparing(MaioresComprasDTO::getValorTotal))
                .orElse(null);
    }

    private Optional<MaioresComprasDTO> criarMaioresComprasDTOSeAnoIgual(Long ano, Cliente cliente, Compra compra, List<Produto> produtos) {
        return produtos.stream()
                .filter(produto -> Objects.equals(produto.getCodigo(), Long.valueOf(compra.getCodigo())))
                .filter(produto -> Objects.equals(ano, produto.getAnoCompra()))
                .map(produto -> {
                    BigDecimal valorTotalCompra = produto.getPreco().multiply(BigDecimal.valueOf(compra.getQuantidade()));
                    return criarMaioresComprasDTO(cliente, compra, produto, valorTotalCompra);
                })
                .findFirst();
    }

    private MaioresComprasDTO criarMaioresComprasDTO(Cliente cliente, Compra compra, Produto produto, BigDecimal valorTotalCompra) {
        List<Compras> comprasList = List.of(new Compras(produto.getCodigo(), produto.getTipoVinho(), produto.getPreco(), compra.getQuantidade(), produto.getAnoCompra(), produto.getSafra()));
        return new MaioresComprasDTO(cliente.getNome(), cliente.getCpf(), comprasList, valorTotalCompra);
    }

    private ClientesECompras obterClientesEProdutos() {
        List<Cliente> clientes = client.obterClientesCompras();
        List<Produto> produtos = produtosClient.obterProdutos();
        return new ClientesECompras(clientes, produtos);
    }

    private List<MaioresComprasDTO> obterMaioresComprasDetalhadas(List<Cliente> clientes, List<Produto> produtos) {
        List<MaioresComprasDTO> listaMaiores = new ArrayList<>();
        clientes.forEach(cliente -> {
            BigDecimal valorTotal = obterValorTotal(cliente, produtos);
            List<Compras> compras = obterCompras(cliente, produtos);
            listaMaiores.add(MaioresComprasDTO.builder()
                    .nome(cliente.getNome())
                    .cpf(cliente.getCpf())
                    .compras(compras)
                    .valorTotal(valorTotal).build());
        });
        return listaMaiores;
    }

    private List<Compras> obterCompras(Cliente cliente, List<Produto> produtos) {
        return cliente.getCompras().stream()
                .flatMap(compra -> produtos.stream()
                        .filter(produto -> Objects.equals(produto.getCodigo(), Long.valueOf(compra.getCodigo())))
                        .map(produto -> Compras.builder()
                                .codigo(produto.getCodigo())
                                .safra(produto.getSafra())
                                .preco(produto.getPreco())
                                .ano_compra(produto.getAnoCompra())
                                .tipo_vinho(produto.getTipoVinho())
                                .quantidade(compra.getQuantidade())
                                .build()))
                .toList();
    }

    private BigDecimal obterValorTotal(Cliente cliente, List<Produto> produtos) {
        return cliente.getCompras().stream()
                .map(compra -> produtos.stream()
                        .filter(produto -> Objects.equals(produto.getCodigo(), Long.valueOf(compra.getCodigo())))
                        .map(produto -> produto.getPreco().multiply(BigDecimal.valueOf(compra.getQuantidade())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
