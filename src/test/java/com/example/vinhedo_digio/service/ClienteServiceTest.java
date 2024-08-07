package com.example.vinhedo_digio.service;

import com.example.vinhedo_digio.mock.ClientesEComprasMock;
import com.example.vinhedo_digio.mock.ProdutoMock;
import com.example.vinhedo_digio.model.MaioresCompras;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {
    private static final EasyRandom RANDOM = new EasyRandom();
    public static final String CPF = "12345678911";
    @Mock
    private ClientesEProdutosService clientesEProdutosService;
    @Mock
    private ComprasService comprasService;

    @InjectMocks
    ClienteService service;

    @Test
    void obterClientesFieis() {
//        ClientesECompras expected = ClientesEComprasMock.build();
        var esperado = List.of(RANDOM.nextObject(MaioresCompras.class));
        when(comprasService.obterMaioresCompras()).thenReturn(esperado);

        var resposta = service.obterClientesFieis();

        assertThat(resposta)
                .isNotNull()
                .isEqualTo(esperado);
    }

    @Test
    void obterRecomendacao() {
        var clientesECompras = ClientesEComprasMock.build();
        var esperado = ProdutoMock.build();
        when(clientesEProdutosService.obterClientesEProdutos()).thenReturn(clientesECompras);

        var resposta = service.obterRecomendacao(CPF);

        assertThat(resposta)
                .isNotNull()
                .isEqualTo(esperado);
    }

    @Test
    void obterRecomendacaoNaoEcontradoException() {
        var clientesECompras = ClientesEComprasMock.build();
        when(clientesEProdutosService.obterClientesEProdutos()).thenReturn(clientesECompras);
        Assertions.assertThrows(RuntimeException.class, () -> service.obterRecomendacao("123"));
    }

}