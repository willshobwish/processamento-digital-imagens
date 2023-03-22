/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Willian Murayama
 */
public class Matriz {

    private Integer Imagem[][];
    private int coluna;
    private int linha;
    private int intensidade;

    public Matriz(Integer[][] Imagem, int coluna, int linha, int intensidade) {
        this.Imagem = Imagem;
        this.coluna = coluna;
        this.linha = linha;
        this.intensidade = intensidade;
    }

    public Matriz(String filepath) {
        try {
            File ArquivoObjeto = new File(filepath);
            Scanner Leitor = new Scanner(ArquivoObjeto);
//Remocao do cabecalho e dos comentarios
            Leitor.nextLine();
            Leitor.nextLine();
//Leitura do tamanho de colunas e linhas
            String[] ColunaLinhaArquivo = Leitor.nextLine().split(" ");
            this.intensidade = Integer.parseInt(Leitor.nextLine());
            this.coluna = Integer.parseInt(ColunaLinhaArquivo[0]);
            this.linha = this.coluna = Integer.parseInt(ColunaLinhaArquivo[1]);
            this.Imagem = new Integer[coluna][linha];
            Leitor.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        System.out.println("""
                           Coluna: %d
                           Linha: %d
                           """.formatted(coluna, linha));
//        Armazenamento do conteudo da imagem
        ArrayList<Integer> ImagemVetor = new ArrayList<>();
        try {
            File ArquivoObjeto = new File(filepath);
            Scanner Leitor = new Scanner(ArquivoObjeto);
//Remocao do cabecalho e dos comentarios
            Leitor.nextLine();
            Leitor.nextLine();
            Leitor.nextLine();

            while (Leitor.hasNext()) {
                ImagemVetor.add(Integer.parseInt(Leitor.next()));
            }
            Leitor.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        int contadorVetor = 0;
        for (int colunaVetor = 0; colunaVetor < coluna; colunaVetor++) {
            for (int linhaVetor = 0; linhaVetor < linha; linhaVetor++) {
                Imagem[colunaVetor][linhaVetor] = ImagemVetor.get(contadorVetor);
                contadorVetor++;
            }
        }
    }

    public void ImprimeArquivo(String filepath) {
        try {
            File myObj = new File(filepath);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try {
            FileWriter myWriter = new FileWriter(filepath);
            myWriter.write("P2\n");
            myWriter.write("# Gerado pelo java\n");
            myWriter.write("""
                           %d %d
                           """.formatted(coluna, linha));
            myWriter.write(String.valueOf(intensidade) + "\n");
            for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
                for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                    myWriter.write(Imagem[colunaMatriz][linhaMatriz].toString() + "\n");
                }
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public Matriz ClarearAdicao(int Quantidade, int Limite) {
        Integer ImagemClareada[][] = new Integer[coluna][linha];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                int temp = Imagem[colunaMatriz][linhaMatriz] + Quantidade;
                if (temp > Limite) {
                    temp = Limite;
                }
                ImagemClareada[colunaMatriz][linhaMatriz] = temp;
            }
        }
        return new Matriz(ImagemClareada, coluna, linha, intensidade);

    }

    public Matriz ClarearMultiplicao(float Quantidade, int Limite) {
        Integer ImagemClareada[][] = new Integer[coluna][linha];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                float temp = Imagem[colunaMatriz][linhaMatriz] * Quantidade;
                if (temp > Limite) {
                    temp = Limite;
                }
                ImagemClareada[colunaMatriz][linhaMatriz] = (int) temp;
            }
        }
        return new Matriz(ImagemClareada, coluna, linha, intensidade);
    }

    public Matriz RotacionarMenos90() {
        Integer ImagemRotacionada[][] = new Integer[coluna][linha];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                ImagemRotacionada[linhaMatriz][colunaMatriz] = Imagem[colunaMatriz][linhaMatriz];
            }
        }
        return new Matriz(ImagemRotacionada, coluna, linha, intensidade);
    }

    public Matriz Rotacionar180() {
        Integer ImagemRotacionada[][] = new Integer[coluna][linha];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                ImagemRotacionada[colunaMatriz][linha - 1 - linhaMatriz] = Imagem[colunaMatriz][linhaMatriz];

            }
        }
        return new Matriz(ImagemRotacionada, coluna, linha, intensidade);
    }

    public Matriz Rotacionar90() {
        Integer ImagemRotacionada[][] = new Integer[coluna][linha];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                ImagemRotacionada[colunaMatriz][linhaMatriz] = Imagem[coluna - 1 - linhaMatriz][colunaMatriz];
            }
        }
        return new Matriz(ImagemRotacionada, coluna, linha, intensidade);
    }

    public Matriz PontaCabeca() {
        Integer ImagemRotacionada[][] = new Integer[coluna][linha];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                ImagemRotacionada[colunaMatriz][linhaMatriz] = Imagem[coluna - 1 - colunaMatriz][linha - 1 - linhaMatriz];
            }
        }
        return new Matriz(ImagemRotacionada, coluna, linha, intensidade);
    }
}
