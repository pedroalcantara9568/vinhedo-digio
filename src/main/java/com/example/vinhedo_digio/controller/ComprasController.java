package com.example.vinhedo_digio.controller;

import com.example.vinhedo_digio.client.ClientesComprasClient;
import com.example.vinhedo_digio.client.ProdutosClient;
import com.example.vinhedo_digio.model.MaioresComprasDTO;
import com.example.vinhedo_digio.service.ComprasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class ComprasController {

    private final ProdutosClient produtosClient;

    private final ClientesComprasClient client;

    private final ComprasService comprasService;

    @GetMapping("/compras")
    public ResponseEntity<List<MaioresComprasDTO>> obterMaioresCompras() {
        return ResponseEntity.ok(comprasService.obterMaioresCompras());
    }

    @GetMapping("/compras/{ano}")
    public ResponseEntity<MaioresComprasDTO> obterMaiorCompraPorAno(@PathVariable("ano") Long ano) {
        return ResponseEntity.ok(comprasService.obterMaiorCompraPorAno(ano));
    }

}