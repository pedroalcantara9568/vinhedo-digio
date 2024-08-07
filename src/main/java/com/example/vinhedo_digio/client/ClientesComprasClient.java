package com.example.vinhedo_digio.client;


import com.example.vinhedo_digio.model.Cliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@FeignClient(value = "clientes-compras-client", url = "${services.clientes-compras-client.url}")
public interface ClientesComprasClient {

    @GetMapping("${services.clientes-compras-client.get-compras-client-path.url}")
    List<Cliente> obterClientesCompras();

}