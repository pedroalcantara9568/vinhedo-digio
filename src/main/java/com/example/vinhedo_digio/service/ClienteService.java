package com.example.vinhedo_digio.service;

import com.example.vinhedo_digio.exception.NaoEcontradoException;
import com.example.vinhedo_digio.model.Cliente;
import com.example.vinhedo_digio.model.Compra;
import com.example.vinhedo_digio.model.MaioresCompras;
import com.example.vinhedo_digio.model.Produto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class ClienteService {

    public static final String MESSAGE = "CPF não encontrado na Lista de Clientes";
    public static final String NENHUMA_RECOMENDACAO_DE_VINHO = "Não foi encontrada nenhuma recomendação de vinho";
    private final ClientesEProdutosService clientesEProdutosService;
    private final CompraService compraService;

    public List<MaioresCompras> obterClientesFieis() {
        return compraService.obterMaioresCompras().stream().limit(3).toList();
    }

    public Produto obterRecomendacao(String cpf) {
        var clientes = clientesEProdutosService.obterClientesEProdutos().clientes();
        var produtos = clientesEProdutosService.obterClientesEProdutos().produtos();
        var vinhoMaisComprado = obterCodigoDoVinhoMaisComprado(cpf, clientes);
        var tipoDoVinhoMaisComprado = obterTipoDoVinhoMaisComprado(vinhoMaisComprado, produtos);
        return produtos.stream()
                .filter(produto -> Objects.equals(tipoDoVinhoMaisComprado, produto.getTipoVinho()))
                .findFirst()
                .orElseThrow(() -> new NaoEcontradoException(NENHUMA_RECOMENDACAO_DE_VINHO));
    }

    private String obterTipoDoVinhoMaisComprado(String vinhoMaisComprado, List<Produto> produtos) {
        return produtos.stream()
                .filter(produto -> Objects.equals(Long.valueOf(vinhoMaisComprado), produto.getCodigo()))
                .map(Produto::getTipoVinho)
                .findFirst()
                .orElseThrow(() -> new NaoEcontradoException(NENHUMA_RECOMENDACAO_DE_VINHO));
    }

    private static String obterCodigoDoVinhoMaisComprado(String cpf, List<Cliente> clientes) {
        return clientes.stream().filter(cliente -> Objects.equals(cpf, cliente.getCpf()))
                .flatMap(cliente -> cliente.getCompras().stream())
                .max(Comparator.comparing(Compra::getQuantidade))
                .map(Compra::getCodigo)
                .orElseThrow(() -> new NaoEcontradoException(MESSAGE));
    }
}
