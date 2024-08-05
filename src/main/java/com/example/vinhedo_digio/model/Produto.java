
package com.example.vinhedo_digio.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Produto implements Serializable {

    @Serial
    private static final long serialVersionUID = -8762461489587756302L;
    @JsonProperty("codigo")
    private Long codigo;
    @JsonProperty("tipo_vinho")
    private String tipoVinho;
    @JsonProperty("preco")
    private BigDecimal preco;
    @JsonProperty("safra")
    private String safra;
    @JsonProperty("ano_compra")
    private Long anoCompra;

}
