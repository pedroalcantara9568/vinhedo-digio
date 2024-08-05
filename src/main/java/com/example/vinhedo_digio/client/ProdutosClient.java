package com.example.vinhedo_digio.client;


import com.example.vinhedo_digio.model.Produto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@FeignClient(value = "produtos-client", url = "https://rgr3viiqdl8sikgv.public.blob.vercel-storage.com")
public interface ProdutosClient {

    @GetMapping("/produtos-mnboX5IPl6VgG390FECTKqHsD9SkLS.json")
    List<Produto> obterProdutos();

}