package com.example.vinhedo_digio.service;

import com.example.vinhedo_digio.exception.NaoEcontradoException;
import com.example.vinhedo_digio.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CompraService {
    public static final String MESSAGE = "CPF não encontrado na Lista de Clientes";
    private final ClientesEProdutosService clientesEProdutosService;

    public List<MaioresCompras> obterMaioresCompras() {
        var clientesECompras = clientesEProdutosService.obterClientesEProdutos();

        var maioresComprasDTOS = obterMaioresComprasDetalhadas(clientesECompras.clientes(), clientesECompras.produtos());

        return maioresComprasDTOS.stream()
                .sorted(Comparator.comparing(MaioresCompras::getValorTotal).reversed())
                .toList();
    }

    public MaioresCompras obterMaiorCompraPorAno(Long ano) {
        var clientesEProdutos = clientesEProdutosService.obterClientesEProdutos();

        return clientesEProdutos.clientes().stream()
                .flatMap(cliente -> cliente.getCompras().stream()
                        .map(compra -> criarMaioresComprasDTOSeAnoIgual(ano, cliente, compra, clientesEProdutos.produtos()))
                        .filter(Optional::isPresent)
                        .map(Optional::get))
                .max(Comparator.comparing(MaioresCompras::getValorTotal))
                .orElseThrow(() -> new NaoEcontradoException(MESSAGE));
    }

    private Optional<MaioresCompras> criarMaioresComprasDTOSeAnoIgual(Long ano, Cliente cliente, Compra compra, List<Produto> produtos) {
        return produtos.stream()
                .filter(produto -> Objects.equals(produto.getCodigo(), Long.valueOf(compra.getCodigo())))
                .filter(produto -> Objects.equals(ano, produto.getAnoCompra()))
                .map(produto -> {
                    BigDecimal valorTotalCompra = produto.getPreco().multiply(BigDecimal.valueOf(compra.getQuantidade()));
                    return criarMaioresComprasDTO(cliente, compra, produto, valorTotalCompra);
                })
                .findFirst();
    }

    private MaioresCompras criarMaioresComprasDTO(Cliente cliente, Compra compra, Produto produto, BigDecimal valorTotalCompra) {
        var comprasList = List.of(new Compras(produto.getCodigo(), produto.getTipoVinho(), produto.getPreco(), compra.getQuantidade(), produto.getAnoCompra(), produto.getSafra()));
        return new MaioresCompras(cliente.getNome(), cliente.getCpf(), comprasList, valorTotalCompra);
    }

    private List<MaioresCompras> obterMaioresComprasDetalhadas(List<Cliente> clientes, List<Produto> produtos) {
        List<MaioresCompras> listaMaiores = new ArrayList<>();
        clientes.forEach(cliente -> {
            BigDecimal valorTotal = obterValorTotal(cliente, produtos);
            List<Compras> compras = obterCompras(cliente, produtos);
            listaMaiores.add(MaioresCompras.builder()
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
