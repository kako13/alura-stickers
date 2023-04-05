package com.alura.stikers.domain.exceptions.sticker;

import java.io.Serial;

public class StickerCreationException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public StickerCreationException(String mensagem) {
        super(mensagem);
    }

}
