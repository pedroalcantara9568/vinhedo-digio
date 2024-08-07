package com.example.vinhedo_digio.service;

import com.example.vinhedo_digio.client.ClientesComprasClient;
import com.example.vinhedo_digio.client.ProdutosClient;
import com.example.vinhedo_digio.mock.ClienteMock;
import com.example.vinhedo_digio.mock.ProdutoMock;
import com.example.vinhedo_digio.model.ClientesECompras;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientesEProdutosServiceTest {

    @Mock
    private ProdutosClient produtosClient;

    @Mock
    private ClientesComprasClient comprasClient;
    @InjectMocks
    ClientesEProdutosService service;

    @Test
    void obterClientesEProdutosTest() {
        when(this.produtosClient.obterProdutos()).thenReturn(List.of(ProdutoMock.build()));
        when(this.comprasClient.obterClientesCompras()).thenReturn(List.of(ClienteMock.build()));
        ClientesECompras clientesECompras = this.service.obterClientesEProdutos();

        assertNotNull(clientesECompras, "clientesECompras should not be null");
    }

}