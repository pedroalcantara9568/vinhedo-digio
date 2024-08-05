package com.example.vinhedo_digio.model;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Compra implements Serializable {
    @Serial
    private static final long serialVersionUID = 6782292862466260839L;
    private String codigo;
    private Integer quantidade;
}