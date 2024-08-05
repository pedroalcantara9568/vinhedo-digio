
package com.example.vinhedo_digio.model;


import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Clientes implements Serializable {

    @Serial
    private static final long serialVersionUID = 2765213458186250568L;
    private String nome;
    private String cpf;
    private List<Compra> compras;
}
