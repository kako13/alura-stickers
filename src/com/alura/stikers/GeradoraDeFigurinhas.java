package com.alura.stikers;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class GeradoraDeFigurinhas {


    public void cria(InputStream inputStream, String nomeArquivo, AvaliacaoEnum avaliacaoEnum) throws Exception {

        //Leitura da imagem
        BufferedImage imagemOriginal = ImageIO.read(inputStream);

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
        Font fonte = new Font("Impact", Font.BOLD, 115);
        graphics.setFont(fonte);
        graphics.setColor(Color.YELLOW);

        //Adicionar imagem de selo
        InputStream imagemSelo = new FileInputStream(new File(avaliacaoEnum.getImagem()));
        BufferedImage imagemSobreposicao = ImageIO.read(imagemSelo);
        int posicaoSeloTextoY = novaAltura - 400;

        graphics.drawImage(imagemSobreposicao, 0, posicaoSeloTextoY, largura/2,altura/4, null);

        //Escrever uma frase na nova imagem
        FontMetrics fontMetrics = graphics.getFontMetrics();
        Rectangle2D retanguloTexto = fontMetrics.getStringBounds(avaliacaoEnum.getTexto(), graphics);

        int larguraTexto = (int) retanguloTexto.getWidth();
        int posicaoTextoX = (largura - larguraTexto) / 2;
        int posicaoTextoY = novaAltura - 100;

        graphics.drawString(avaliacaoEnum.getTexto(), posicaoTextoX, posicaoTextoY);

        //Adicionar contorno (outline) nos caracteres
        adicionarContorno(avaliacaoEnum.getTexto(), largura, graphics, fonte, posicaoTextoX, posicaoTextoY);

        //Escrever imagem nova em um arquivo
        escreverNovaImagem(nomeArquivo, novaImagem);
    }

    private static void escreverNovaImagem(String nomeArquivo, BufferedImage novaImagem) throws IOException {
        File diretorio = new File("saida\\");
        diretorio.mkdir();
        File figurinha = new File(diretorio + "/"+ nomeArquivo);
        ImageIO.write(novaImagem, "png", figurinha);
    }

    private static void adicionarContorno(String texto, int largura, Graphics2D graphics, Font fonte, int posicaoTextoX, int posicaoTextoY) {
        FontRenderContext fontRenderContext = graphics.getFontRenderContext();
        TextLayout textLayout = new TextLayout(texto, fonte, fontRenderContext);
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
