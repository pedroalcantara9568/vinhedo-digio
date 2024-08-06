package com.example.vinhedo_digio.client;


import com.example.vinhedo_digio.model.Cliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@FeignClient(value = "clientes-compras-client", url = "https://rgr3viiqdl8sikgv.public.blob.vercel-storage.com/")
public interface ClientesComprasClient {

    @GetMapping("/clientes-Vz1U6aR3GTsjb3W8BRJhcNKmA81pVh.json")
    List<Cliente> obterClientesCompras();

}