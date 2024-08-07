package com.example.vinhedo_digio.client;


import com.example.vinhedo_digio.model.Produto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@FeignClient(value = "produtos-client", url = "${services.produtos-client.url}")
public interface ProdutosClient {

    @GetMapping("${services.produtos-client.get-protudos-path.url}")
    List<Produto> obterProdutos();

}