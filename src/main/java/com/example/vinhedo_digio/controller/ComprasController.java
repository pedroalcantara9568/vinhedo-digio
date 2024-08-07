package com.example.vinhedo_digio.controller;

import com.example.vinhedo_digio.model.MaioresCompras;
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

    private final ComprasService comprasService;

    @GetMapping("/compras")
    public ResponseEntity<List<MaioresCompras>> obterMaioresCompras() {
        return ResponseEntity.ok(comprasService.obterMaioresCompras());
    }

    @GetMapping("/compras/{ano}")
    public ResponseEntity<MaioresCompras> obterMaiorCompraPorAno(@PathVariable("ano") Long ano) {
        return ResponseEntity.ok(comprasService.obterMaiorCompraPorAno(ano));
    }

}