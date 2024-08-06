package com.example.vinhedo_digio.mock;


import com.example.vinhedo_digio.model.Compra;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompraMock {


    public static Compra build() {
       return Compra.builder()
                .codigo("1")
                .quantidade(5)
                .build();
    }
}
