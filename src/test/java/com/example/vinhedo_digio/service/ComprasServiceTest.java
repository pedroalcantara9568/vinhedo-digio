package com.example.vinhedo_digio.service;

import com.example.vinhedo_digio.exception.NaoEcontradoException;
import com.example.vinhedo_digio.mock.ClientesEComprasMock;
import com.example.vinhedo_digio.mock.MaioresComprasDTOMock;
import com.example.vinhedo_digio.mock.ProdutoMock;
import com.example.vinhedo_digio.model.MaioresComprasDTO;
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
class ComprasServiceTest {
    private static final EasyRandom RANDOM = new EasyRandom();
    public static final String CPF = "12345678911";
    @Mock
    private ClientesEProdutosService clientesEProdutosService;

    @InjectMocks
    ComprasService service;

    @Test
    void obterMaioresCompras() {
        var mock = ClientesEComprasMock.build();
        var esperado = List.of(MaioresComprasDTOMock.build());
        when(clientesEProdutosService.obterClientesEProdutos()).thenReturn(mock);

        var resposta = service.obterMaioresCompras();

        assertThat(resposta)
                .isNotNull()
                .isEqualTo(esperado);
    }

    @Test
    void obterMaiorCompraPorAno() {
        var clientesECompras = ClientesEComprasMock.build();
        var esperado = MaioresComprasDTOMock.build();
        when(clientesEProdutosService.obterClientesEProdutos()).thenReturn(clientesECompras);

        var resposta = service.obterMaiorCompraPorAno(2020L);

        assertThat(resposta)
                .isNotNull()
                .isEqualTo(esperado);
    }

    @Test
    void obterMaiorCompraPorAnoUnsucessull() {
        var clientesECompras = ClientesEComprasMock.build();
        when(clientesEProdutosService.obterClientesEProdutos()).thenReturn(clientesECompras);

        Assertions.assertThrows(NaoEcontradoException.class, () -> service.obterMaiorCompraPorAno(2030L));
    }

}