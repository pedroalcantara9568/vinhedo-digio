package com.example.vinhedo_digio.controller;

import com.example.vinhedo_digio.model.MaioresCompras;
import com.example.vinhedo_digio.model.Produto;
import com.example.vinhedo_digio.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping("/clientes-fieis")
    public ResponseEntity<List<MaioresCompras>> obterClientesFieis() {
        return ResponseEntity.ok(clienteService.obterClientesFieis());
    }

    @GetMapping("/recomendacao/cliente/tipo/{cpf}")
    public ResponseEntity<Produto> obterRecomendacao(@PathVariable("cpf") String cpf) {
        return ResponseEntity.ok(clienteService.obterRecomendacao(cpf));
    }

}