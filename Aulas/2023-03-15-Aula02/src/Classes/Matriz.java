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
            this.linha = Integer.parseInt(ColunaLinhaArquivo[1]);
            this.Imagem = new Integer[coluna][linha];
            Leitor.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ocorreu um erro no fechamento do arquivo");
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
                int aux = Integer.parseInt(Leitor.next());
//                System.out.println(aux);
                ImagemVetor.add(aux);
            }
            Leitor.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ocorreu um erro no fechamento do arquivo");
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
            escritor.write("P2\n");
            escritor.write("# Gerado pelo java\n");
            escritor.write("""
                           %d %d
                           """.formatted(coluna, linha));
            escritor.write(String.valueOf(intensidade) + "\n");
            for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
                for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                    escritor.write("""
                                    %d
                                    """.formatted(Imagem[colunaMatriz][linhaMatriz]));
                }
            }
            escritor.close();
            System.out.println("Escrito corretamente no arquivo.");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro na escrita");
            e.printStackTrace();
        }
    }

    public Matriz ClarearAdicao(int Quantidade) {
        Integer ImagemClareada[][] = new Integer[coluna][linha];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                int temp = Imagem[colunaMatriz][linhaMatriz] + Quantidade;
                if (temp > intensidade) {
                    temp = intensidade;
                }
                ImagemClareada[colunaMatriz][linhaMatriz] = temp;
            }
        }
        return new Matriz(ImagemClareada, coluna, linha, intensidade);

    }

    public Matriz ClarearMultiplicao(float Quantidade) {
        Integer ImagemClareada[][] = new Integer[coluna][linha];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                float temp = Imagem[colunaMatriz][linhaMatriz] * Quantidade;
                if (temp > intensidade) {
                    temp = intensidade;
                }
                ImagemClareada[colunaMatriz][linhaMatriz] = (int) temp;
            }
        }
        return new Matriz(ImagemClareada, coluna, linha, intensidade);
    }

    public Matriz RotacaoMenos90() {
        Integer ImagemRotacionada[][] = new Integer[coluna][linha];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                ImagemRotacionada[linhaMatriz][colunaMatriz] = Imagem[colunaMatriz][linhaMatriz];
            }
        }
        return new Matriz(ImagemRotacionada, coluna, linha, intensidade);
    }

    public Matriz Rotacao90() {
        Integer ImagemRotacionada[][] = new Integer[linha][coluna];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
//                ImagemRotacionada[colunaMatriz][linhaMatriz] = Imagem[colunaMatriz][linhaMatriz];
                ImagemRotacionada[linhaMatriz][coluna - 1 - colunaMatriz] = Imagem[colunaMatriz][linhaMatriz];
            }
        }
        return new Matriz(ImagemRotacionada, linha, coluna, intensidade);
    }

    public Matriz Rotacao180() {
        Integer ImagemRotacionada[][] = new Integer[coluna][linha];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                ImagemRotacionada[colunaMatriz][linhaMatriz] = Imagem[coluna - 1 - colunaMatriz][linha - 1 - linhaMatriz];
            }
        }
        return new Matriz(ImagemRotacionada, coluna, linha, intensidade);
    }

    public Matriz Teste() {
        Integer ImagemTeste[][] = new Integer[coluna][linha];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                ImagemTeste[colunaMatriz][linhaMatriz] = Imagem[colunaMatriz][linhaMatriz];
            }
        }
        return new Matriz(ImagemTeste, coluna, linha, intensidade);
    }

    public Matriz EspelhamentoHorizontal() {
        Integer ImagemRotacionada[][] = new Integer[coluna][linha];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                ImagemRotacionada[colunaMatriz][linha - 1 - linhaMatriz] = Imagem[colunaMatriz][linhaMatriz];
            }
        }
        return new Matriz(ImagemRotacionada, coluna, linha, intensidade);
    }

    public Matriz EspelhamentoVertical() {
        Integer ImagemRotacionada[][] = new Integer[coluna][linha];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                ImagemRotacionada[coluna - 1 - colunaMatriz][linhaMatriz] = Imagem[colunaMatriz][linhaMatriz];
            }
        }
        return new Matriz(ImagemRotacionada, coluna, linha, intensidade);
    }

    public Matriz ReducaoNivel(int quantidadeDeNiveis) {
        Integer Reduzida[][] = new Integer[coluna][linha];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                Reduzida[colunaMatriz][linhaMatriz] = (int) Math.ceil(Imagem[colunaMatriz][linhaMatriz] * (quantidadeDeNiveis - 1) / intensidade);
            }
        }
        return new Matriz(Reduzida, coluna, linha, quantidadeDeNiveis);
    }

    public Matriz Binarizacao(int limiar) {
        Integer Reduzida[][] = new Integer[coluna][linha];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                if (Imagem[colunaMatriz][linhaMatriz] <= limiar) {
                    Reduzida[colunaMatriz][linhaMatriz] = intensidade;
                } else {
                    Reduzida[colunaMatriz][linhaMatriz] = 0;
                }
            }
        }
        return new Matriz(Reduzida, coluna, linha, intensidade);
    }
}
