/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import java.io.File;
import java.io.FileNotFoundException;
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

    public PPMImage(Integer[][] Matriz, int linha, int coluna, int intensidade, String cabecalho, String comentario) {
//        this.Matriz = Matriz;
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
}
