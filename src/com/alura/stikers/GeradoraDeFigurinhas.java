package com.alura.stikers;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

public class GeradoraDeFigurinhas {


    public void cria(InputStream inputStream, String nomeArquivo) throws Exception {

        //Leitura da imagem
//        InputStream inputStream = new URL("https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularTVs_1.jpg").openStream();
//        InputStream inputStream = new FileInputStream(new File("entrada/Filme.JPG"));
        BufferedImage imagemOriginal = ImageIO.read(inputStream);

        //Cria imagem em memoria com transparencia e com tamnaho novo
        int largura = imagemOriginal.getWidth();
        int altura = imagemOriginal.getHeight();
        int novaAltura = altura + 200;
        BufferedImage novaImagem = new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);

        //copiar a imagem original pra nova imagem em memoria (em memoria)
        //Desenhando uma imagem
        Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
        graphics.drawImage(imagemOriginal, 0, 0, null);


        //configurar fonte
        Font fonte = new Font("Impact", Font.BOLD, 84);
        graphics.setFont(fonte);
        graphics.setColor(Color.YELLOW);

        //Escrever uma frase na nova imagem
        String texto = "E TOME FIGURINHA";
        FontMetrics fontMetrics = graphics.getFontMetrics();
        Rectangle2D retanguloTexto = fontMetrics.getStringBounds(texto, graphics);

        int larguraTexto = (int) retanguloTexto.getWidth();
        int posicaoTextoX = (largura - larguraTexto) / 2;
        int posicaoTextoY = novaAltura - 100;

        graphics.drawString(texto, posicaoTextoX, posicaoTextoY);

        //Adicionar contorno (outline) nos caracteres
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

        //Escrever imagem nova em um arquivo
        File diretorio = new File("saida\\");
        diretorio.mkdir();
        File figurinha = new File(diretorio + "/"+nomeArquivo);
        ImageIO.write(novaImagem, "png", figurinha);
    }
}
