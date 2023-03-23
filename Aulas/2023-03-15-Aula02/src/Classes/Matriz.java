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
    private String cabecalho;
    private String comentario;

    public Matriz(Integer[][] Imagem, int coluna, int linha, int intensidade, String cabecalho, String comentario) {
        this.Imagem = Imagem;
        this.coluna = coluna;
        this.linha = linha;
        this.intensidade = intensidade;
        this.cabecalho = cabecalho;
        this.comentario = comentario;
    }

    public Matriz(String filepath) {
        try {
            File ArquivoObjeto = new File(filepath);
            Scanner Leitor = new Scanner(ArquivoObjeto);
            //Leitura do cabecalho, comentario, tamanho e intensidade
            this.cabecalho = Leitor.nextLine();
            System.out.println(cabecalho);
            this.comentario = Leitor.nextLine();
            System.out.println(comentario);
            String[] ColunaLinhaArquivo = Leitor.nextLine().split(" ");
            this.intensidade = Integer.parseInt(Leitor.nextLine());
            //Leitura do tamanho de colunas e linhas
            this.coluna = Integer.parseInt(ColunaLinhaArquivo[0]);
            this.linha = Integer.parseInt(ColunaLinhaArquivo[1]);
            this.Imagem = new Integer[coluna][linha];
            System.out.println("""
                           Nome do arquivo: %s
                           Coluna: %d
                           Linha: %d
                           Cabecalho: %s
                           Comentario: %s
                           """.formatted(ArquivoObjeto.getName(), coluna, linha, cabecalho, comentario));
            //Armazenamento do conteudo da imagem
            ArrayList<Integer> ImagemVetor = new ArrayList<>();
            while (Leitor.hasNext()) {
                ImagemVetor.add(Integer.parseInt(Leitor.next()));
            }
            int contadorVetor = 0;
            for (int colunaVetor = 0; colunaVetor < coluna; colunaVetor++) {
                for (int linhaVetor = 0; linhaVetor < linha; linhaVetor++) {
                    Imagem[colunaVetor][linhaVetor] = ImagemVetor.get(contadorVetor);
                    contadorVetor++;
                }
            }
            Leitor.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ocorreu um erro no fechamento do arquivo");
            e.printStackTrace();
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
            //Impressao do cabecalho, comentario, coluna e linha, intensidade do pixel
            System.out.println("""
                           %s
                           %s
                           %d %d
                           %d
                           """.formatted(cabecalho, comentario, coluna, linha, intensidade));
            //Escrita do cabecalho, comentario, coluna e linha, intensidade do pixel
            escritor.write("""
                           %s
                           %s
                           %d %d
                           %d
                           """.formatted(cabecalho, comentario + " gerado pelo java", coluna, linha, intensidade));
            //Escrita da matriz em arquivo
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

                if (Imagem[colunaMatriz][linhaMatriz] + Quantidade > intensidade) {
                    ImagemClareada[colunaMatriz][linhaMatriz] = intensidade;
                } else {
                    ImagemClareada[colunaMatriz][linhaMatriz] = Imagem[colunaMatriz][linhaMatriz] + Quantidade;
                }

            }
        }
        return new Matriz(ImagemClareada, coluna, linha, intensidade, cabecalho, comentario);

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
        return new Matriz(ImagemClareada, coluna, linha, intensidade, cabecalho, comentario);
    }

    public Matriz RotacaoMenos90() {
        Integer ImagemRotacionada[][] = new Integer[linha][coluna];
        Comentario("Rotacao -90", linha, coluna);
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                System.out.println("Coluna Linha: %d x %d = %d".formatted(linhaMatriz, colunaMatriz, Imagem[colunaMatriz][linhaMatriz]));
                ImagemRotacionada[linha - 1 - linhaMatriz][colunaMatriz] = Imagem[colunaMatriz][linhaMatriz];
            }
        }
        return new Matriz(ImagemRotacionada, linha, coluna, intensidade, cabecalho, comentario);
    }

    public Matriz Rotacao90() {
        Integer ImagemRotacionada[][] = new Integer[linha][coluna];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                ImagemRotacionada[linhaMatriz][coluna - 1 - colunaMatriz] = Imagem[colunaMatriz][linhaMatriz];
            }
        }
        return new Matriz(ImagemRotacionada, linha, coluna, intensidade, cabecalho, comentario);
    }

    public Matriz Rotacao180() {
        Integer ImagemRotacionada[][] = new Integer[coluna][linha];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                ImagemRotacionada[colunaMatriz][linhaMatriz] = Imagem[coluna - 1 - colunaMatriz][linha - 1 - linhaMatriz];
            }
        }
        return new Matriz(ImagemRotacionada, coluna, linha, intensidade, cabecalho, comentario);
    }

    public Matriz Teste() {
        Integer ImagemTeste[][] = new Integer[coluna][linha];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                ImagemTeste[colunaMatriz][linhaMatriz] = Imagem[colunaMatriz][linhaMatriz];
            }
        }
        return new Matriz(ImagemTeste, coluna, linha, intensidade, cabecalho, comentario);
    }

    public void Comentario(String funcao, int colunaModificada, int linhaModificada) {
        System.out.println("""
                           %s
                           Coluna e linha original: %d x %d
                           Coluna e linha modificado: %d x %d
                           """.formatted(funcao, coluna, linha, colunaModificada, linhaModificada));
    }

    public Matriz EspelhamentoHorizontal() {
        Integer ImagemRotacionada[][] = new Integer[coluna][linha];
        Comentario("Espelhamento horizontal", coluna, linha);
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                ImagemRotacionada[colunaMatriz][linha - 1 - linhaMatriz] = Imagem[colunaMatriz][linhaMatriz];
            }
        }
        return new Matriz(ImagemRotacionada, coluna, linha, intensidade, cabecalho, comentario);
    }

    public Matriz EspelhamentoVertical() {
        Integer ImagemRotacionada[][] = new Integer[coluna][linha];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                ImagemRotacionada[coluna - 1 - colunaMatriz][linhaMatriz] = Imagem[colunaMatriz][linhaMatriz];
            }
        }
        return new Matriz(ImagemRotacionada, coluna, linha, intensidade, cabecalho, comentario);
    }

    public Matriz ReducaoNivel(int quantidadeDeNiveis) {
        Integer Reduzida[][] = new Integer[coluna][linha];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                Reduzida[colunaMatriz][linhaMatriz] = (int) Math.ceil(Imagem[colunaMatriz][linhaMatriz] * (quantidadeDeNiveis - 1) / intensidade);
            }
        }
        return new Matriz(Reduzida, coluna, linha, quantidadeDeNiveis, cabecalho, comentario);
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
        return new Matriz(Reduzida, coluna, linha, intensidade, cabecalho, comentario);
    }
}
