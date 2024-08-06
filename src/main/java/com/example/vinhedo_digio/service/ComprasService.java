package com.example.vinhedo_digio.service;

import com.example.vinhedo_digio.exception.NaoEcontradoException;
import com.example.vinhedo_digio.model.*;
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
    public static final String MESSAGE = "Não foi encontrado compras para o ano buscado";
    private final ClientesEProdutosService clientesEProdutosService;

    public List<MaioresComprasDTO> obterMaioresCompras() {
        var result = clientesEProdutosService.obterClientesEProdutos();
        var maioresComprasDTOS = obterMaioresComprasDetalhadas(result.clientes(), result.produtos());

        return maioresComprasDTOS.stream()
                .sorted(Comparator.comparing(MaioresComprasDTO::getValorTotal).reversed())
                .toList();
    }

    public MaioresComprasDTO obterMaiorCompraPorAno(Long ano) {
        var clientesEProdutos = clientesEProdutosService.obterClientesEProdutos();

        return clientesEProdutos.clientes().stream()
                .flatMap(cliente -> cliente.getCompras().stream()
                        .map(compra -> criarMaioresComprasDTOSeAnoIgual(ano, cliente, compra, clientesEProdutos.produtos())))
                .max(Comparator.comparing(MaioresComprasDTO::getValorTotal))
                .orElseThrow(() -> new NaoEcontradoException(MESSAGE));
    }

    private MaioresComprasDTO criarMaioresComprasDTOSeAnoIgual(Long ano, Cliente cliente, Compra compra, List<Produto> produtos) {
        return produtos.stream()
                .filter(produto -> Objects.equals(produto.getCodigo(), Long.valueOf(compra.getCodigo())))
                .filter(produto -> Objects.equals(ano, produto.getAnoCompra()))
                .map(produto -> {
                    BigDecimal valorTotalCompra = produto.getPreco().multiply(BigDecimal.valueOf(compra.getQuantidade()));
                    return criarMaioresComprasDTO(cliente, compra, produto, valorTotalCompra);
                })
                .findFirst().orElseThrow(() -> new NaoEcontradoException(MESSAGE));
    }

    private MaioresComprasDTO criarMaioresComprasDTO(Cliente cliente, Compra compra, Produto produto, BigDecimal valorTotalCompra) {
        var comprasList = List.of(new Compras(produto.getCodigo(),
                produto.getTipoVinho(),
                produto.getPreco(),
                compra.getQuantidade(),
                produto.getAnoCompra(),
                produto.getSafra()));
        return new MaioresComprasDTO(cliente.getNome(), cliente.getCpf(), comprasList, valorTotalCompra);
    }

    private List<MaioresComprasDTO> obterMaioresComprasDetalhadas(List<Cliente> clientes, List<Produto> produtos) {
        var listaMaiores = new ArrayList<MaioresComprasDTO>();
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
