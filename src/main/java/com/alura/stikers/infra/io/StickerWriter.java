package com.alura.stikers.infra.io;

import com.alura.stikers.domain.exceptions.sticker.StickerImageWriteException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StickerWriter {

    public static void escreverNovaImagem(String nomeArquivo, BufferedImage novaImagem) throws StickerImageWriteException {
        File diretorio = new File("saida\\");
        diretorio.mkdir();
        File figurinha = new File(diretorio + "/" + nomeArquivo);
        try {
            ImageIO.write(novaImagem, "png", figurinha);
        } catch (IOException e) {
            throw new StickerImageWriteException(String.format("Erro ao gravar imagem da figurinha no diretório: '%s'",
                    diretorio.getAbsolutePath()));
        }
    }
}
