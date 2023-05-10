/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author willm
 */
public class PPMImage {

    protected Integer[][] matrizR, matrizG, matrizB;
    protected int linha;
    protected int coluna;
    protected int intensidade;
    protected String cabecalho;
    protected String comentario;

    public PPMImage(Integer[][] matrizR, Integer[][] matrizG, Integer[][] matrizB, int linha, int coluna, int intensidade, String cabecalho, String comentario) {
        this.matrizR = matrizR;
        this.matrizG = matrizG;
        this.matrizB = matrizB;
        this.linha = linha;
        this.coluna = coluna;
        this.intensidade = intensidade;
        this.cabecalho = cabecalho;
        this.comentario = comentario;
    }

    /**
     *
     * @param filepath
     */
    public PPMImage(String filepath) {
        try {
            File ArquivoObjeto = new File(filepath);
            Scanner Leitor = new Scanner(ArquivoObjeto);
            //Leitura do cabecalho, comentario, tamanho e intensidade
            this.cabecalho = Leitor.nextLine();
            this.comentario = Leitor.nextLine();
            //Leitura do tamanho de colunas e linhas
            this.linha = Leitor.nextInt();
            this.coluna = Leitor.nextInt();
            this.intensidade = Leitor.nextInt();
            this.matrizR = new Integer[this.linha][this.coluna];
            this.matrizG = new Integer[this.linha][this.coluna];
            this.matrizB = new Integer[this.linha][this.coluna];
            System.out.println("""
                           Nome do arquivo: %s
                           linha: %d
                           coluna: %d
                           Intensidade: %d
                           Cabecalho: %s
                           Comentario: %s
                           """.formatted(ArquivoObjeto.getName(), linha, coluna, intensidade, cabecalho, comentario));
            //Armazenamento do conteudo da imagem
            while (Leitor.hasNext()) {
                for (int colunaVetor = 0; colunaVetor < linha; colunaVetor++) {
                    for (int linhaVetor = 0; linhaVetor < coluna; linhaVetor++) {
                        matrizR[colunaVetor][linhaVetor] = Leitor.nextInt();
                        matrizG[colunaVetor][linhaVetor] = Leitor.nextInt();
                        matrizB[colunaVetor][linhaVetor] = Leitor.nextInt();
                    }
                }
            }
            Leitor.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ocorreu um erro no fechamento do arquivo");
            e.printStackTrace();
        }
    }

    public void saveImage(String filepath) {
        try {
            File ponteiroArquivo = new File(filepath);
            if (ponteiroArquivo.createNewFile()) {
                System.out.println("Arquivo criado %s foi criado".formatted(ponteiroArquivo.getName()));
            } else {
                System.out.println("A imagem %s ja existe".formatted(ponteiroArquivo.getName()));
            }
        } catch (IOException e) {
            System.out.println("Ocorreu um erro");
            e.printStackTrace();
        }
        try {
            FileWriter escritor = new FileWriter(filepath);
            //Impressao do cabecalho, comentario, linha e coluna, intensidade do pixel
            System.out.println("""
                           %s
                           %s
                           %d %d
                           %d
                           """.formatted(cabecalho, comentario, linha, coluna, intensidade));
            //Escrita do cabecalho, comentario, linha e coluna, intensidade do pixel
            escritor.write("""
                           %s
                           %s
                           %d %d
                           %d
                           """.formatted(cabecalho, comentario + " gerado pelo java", linha, coluna, intensidade));
            //Escrita da matriz em arquivo
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
                    escritor.write("""
                                    %d %d %d
                                    """.formatted(matrizR[linhaMatriz][colunaMatriz], matrizG[linhaMatriz][colunaMatriz], matrizB[linhaMatriz][colunaMatriz]));
                }
            }
            escritor.close();
            System.out.println("Escrito corretamente no arquivo.");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro na escrita");
            e.printStackTrace();
        }
    }
}
