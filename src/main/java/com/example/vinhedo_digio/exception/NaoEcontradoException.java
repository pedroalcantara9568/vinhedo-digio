package com.example.vinhedo_digio.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class NaoEcontradoException extends RuntimeException {


    @Serial
    private static final long serialVersionUID = -6891221868906631387L;

    public NaoEcontradoException(String message) {
        super(message);
    }

}
