package com.example.vinhedo_digio.exception.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErroDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -7824111635316850171L;
    private String message;
}
