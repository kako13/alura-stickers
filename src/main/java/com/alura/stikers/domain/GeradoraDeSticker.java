package com.alura.stikers.domain;

import com.alura.stikers.domain.enums.AvaliacaoEnum;
import com.alura.stikers.domain.exceptions.sticker.StickerApprovementImageReadException;
import com.alura.stikers.infra.io.StickerWriter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;

public class GeradoraDeSticker {


    public void cria(InputStream inputStream, String nomeArquivo, AvaliacaoEnum avaliacaoEnum) {

        //Leitura do stream da imagem
        BufferedImage imagemOriginal;
        try {
            imagemOriginal = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Cria imagem em memoria com transparencia e com tamnaho novo
        int largura = 1000;
        int altura = 1500;
        int novaAltura = altura + 200;
        BufferedImage novaImagem = new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);

        //copiar a imagem original pra nova imagem em memoria (em memoria)
        //Desenhando uma imagem
        Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
        graphics.drawImage(imagemOriginal, 0, 0, largura, altura, null);


        //configurar fonte
        Font fonteTexto = new Font("Impact", Font.BOLD, 115);
        graphics.setFont(fonteTexto);
        graphics.setColor(Color.YELLOW);

        //Adicionar imagem de selo
        adicionaSeloDeAprovacao(avaliacaoEnum, largura, altura, novaAltura, graphics);

        //Escrever uma frase centralizada na nova imagem
        FontMetrics fontMetrics = graphics.getFontMetrics();
        Rectangle2D retanguloTexto = fontMetrics.getStringBounds(avaliacaoEnum.getTexto(), graphics);
        int larguraTexto = (int) retanguloTexto.getWidth();
        int posicaoTextoX = (largura - larguraTexto) / 2;
        int posicaoTextoY = novaAltura - 100;
        graphics.drawString(avaliacaoEnum.getTexto(), posicaoTextoX, posicaoTextoY);

        //Adicionar contorno (outline) nos caracteres
        adicionarContorno(avaliacaoEnum.getTexto(), largura, graphics, fonteTexto, posicaoTextoX, posicaoTextoY);

        //Escrever imagem nova em um arquivo
        StickerWriter.escreverNovaImagem(nomeArquivo, novaImagem);
    }

    private static void adicionaSeloDeAprovacao(AvaliacaoEnum avaliacaoEnum, int largura, int altura,
                                                int novaAltura, Graphics2D graphics) {
        BufferedImage imagemSobreposicao;
        try {
            InputStream imagemSelo = new FileInputStream(avaliacaoEnum.getPathImagem());
            imagemSobreposicao = ImageIO.read(imagemSelo);
        } catch (IOException e) {
            throw new StickerApprovementImageReadException(
                    String.format("Erro ao ler a imagem do 'selo de aprovação' da figurinha do diretório '%s'",
                            avaliacaoEnum.getPathImagem()));
        }
        int posicaoSeloTextoY = novaAltura - 400;

        graphics.drawImage(imagemSobreposicao, 0, posicaoSeloTextoY, largura / 2, altura / 4, null);
    }


    private static void adicionarContorno(String texto, int largura, Graphics2D graphics, Font fonteTexto,
                                          int posicaoTextoX, int posicaoTextoY) {
        FontRenderContext fontRenderContext = graphics.getFontRenderContext();
        TextLayout textLayout = new TextLayout(texto, fonteTexto, fontRenderContext);
        Shape outline = textLayout.getOutline(null);
        AffineTransform transform = graphics.getTransform();
        transform.translate(posicaoTextoX, posicaoTextoY);
        graphics.setTransform(transform);

        BasicStroke outlineStroke = new BasicStroke(largura * 0.004f);
        graphics.setStroke(outlineStroke);
        graphics.setColor(Color.black);
        graphics.draw(outline);
        graphics.setClip(outline);
    }
}
