/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author willm
 */
public class PPMImage {

    /**
     * Matriz para o matriz red
     */
    protected Integer[][] matrizR,
            /**
             * Matriz para o canal green
             */
            matrizG,
            /**
             * Matriz para o canal blue
             */
            matrizB;

    /**
     * Quantidade de linhas da imagem
     */
    protected int linha;

    /**
     * Quantidade de coluna da imagems
     */
    protected int coluna;

    /**
     * A profundidade de cor da imagem
     */
    protected int intensidade;

    /**
     * Cabecalho da imagem
     */
    protected String cabecalho;

    /**
     * Comentarios da imagem
     */
    protected String comentario;

    /**
     * Construtor da classe
     *
     * @param matrizR Matriz red
     * @param matrizG Matriz green
     * @param matrizB Matriz blue
     * @param linha Quantidade de linha da imagem
     * @param coluna Quantidade de coluna da imagem
     * @param intensidade Quantidade da profundidade de cor
     * @param cabecalho Cabecalho da imagem
     * @param comentario Comentario da imagem
     */
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
     * AAAs
     *
     * @param filepath
     */
    public PPMImage(String filepath) {
        try {
            System.out.println("Abertura de imagem PPM binario");
            FileInputStream fileInputStream = new FileInputStream(filepath);
            String magicNumber = readLineBinary(fileInputStream);
            fileInputStream.close();
            if (magicNumber.equals("P6")) {
//                Para tratar arquivos binários é necessário utilizar um método mais "primitivo" do java que tem controle sobre a quantidade de leitura de bytes
                try {
                    fileInputStream = new FileInputStream(filepath);
//                    Leitura do cabeçalho
//                    Define o objeto matriz para P3 porque será escrito em ASCII posteriormente
                    cabecalho = "P3";
//                    Pula o número mágico para o próxima linha
                    readLineBinary(fileInputStream);
                    comentario = readLineBinary(fileInputStream);
//                    Como é possível ler somente uma linha direta, é necessário dividir a string e definir cada parte como linha e coluna
                    String[] width_height = readLineBinary(fileInputStream).split(" ");
                    linha = Integer.parseInt(width_height[0]);
                    coluna = Integer.parseInt(width_height[1]);
                    intensidade = readInteger(fileInputStream);
                    matrizR = new Integer[linha][coluna];
                    matrizG = new Integer[linha][coluna];
                    matrizB = new Integer[linha][coluna];
                    System.out.println("""
                           Linha: %d
                           coluna: %d
                           Intensidade: %d
                           Cabecalho: %s
                           Comentario: %s
                           """.formatted(linha, coluna, intensidade, cabecalho, comentario));
                    // Create a byte array to hold the pixel values
                    byte[] pixels = new byte[linha * coluna * 3];
                    // Read the pixel values
                    int bytesRead = 0;
                    while (bytesRead < pixels.length) {
                        int count = fileInputStream.read(pixels, bytesRead, pixels.length - bytesRead);
                        if (count == -1) {
                            break;
                        }
                        bytesRead += count;
                    }
                    int aux = 0;
                    for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
//                            Incrementar em um cada vez que le para ter todas as tres cores por vez
                            matrizR[linhaMatriz][colunaMatriz] = pixels[aux] & 0xFF;
                            aux++;
                            matrizG[linhaMatriz][colunaMatriz] = pixels[aux] & 0xFF;
                            aux++;
                            matrizB[linhaMatriz][colunaMatriz] = pixels[aux] & 0xFF;
                            aux++;
                        }
                    }
                    fileInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (magicNumber.equals("P3")) {
//                Para ler arquivos em ASCII podemos utilizar métodos menos primitivos e temos métodos mais simples e direto
                try {
                    System.out.println("Abertura de imagem PPM ASCII");
                    File ArquivoObjeto = new File(filepath);
                    Scanner Leitor = new Scanner(ArquivoObjeto);
                    //Leitura do cabecalho, comentario, tamanho e intensidade
                    cabecalho = Leitor.nextLine();
                    System.out.println(cabecalho);
                    comentario = Leitor.nextLine();
                    System.out.println(comentario);
                    String[] ColunaLinhaArquivo = Leitor.nextLine().split(" ");
                    intensidade = Integer.parseInt(Leitor.nextLine());
                    //Leitura do tamanho de colunas e linhas
                    linha = Integer.parseInt(ColunaLinhaArquivo[0]);
                    coluna = Integer.parseInt(ColunaLinhaArquivo[1]);
                    matrizR = new Integer[linha][coluna];
                    matrizG = new Integer[linha][coluna];
                    matrizB = new Integer[linha][coluna];
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
                                matrizR[colunaVetor][linhaVetor] = Integer.valueOf(Leitor.next());
                                matrizG[colunaVetor][linhaVetor] = Integer.valueOf(Leitor.next());
                                matrizB[colunaVetor][linhaVetor] = Integer.valueOf(Leitor.next());

                            }
                        }
                    }
                    Leitor.close();
                } catch (FileNotFoundException e) {
                    System.out.println("Ocorreu um erro no fechamento do arquivo");
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int readInteger(FileInputStream fileInputStream) {
        String s = "";
        try {
            s = readLineBinary(fileInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Integer.parseInt(s);
    }

    private static String readLineBinary(FileInputStream fileInputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            int c;
            while ((c = fileInputStream.read()) != -1 && (c != '\n')) {
                stringBuilder.append((char) c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString().trim();
    }

    /**
     *
     * @param filepath
     */
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
                               Cabecalho: %s
                               Comentario: %s
                               Quantidade de linha e coluna: %d %d
                               Profundidade de cores: %d
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

    /**
     *
     * @param filepath
     */
    public void saveImageChannels(String filepath) {
        File ponteiroArquivoR = new File(filepath + "R.pgm");
        File ponteiroArquivoG = new File(filepath + "G.pgm");
        File ponteiroArquivoB = new File(filepath + "B.pgm");
        try {
            if (ponteiroArquivoR.createNewFile()) {
                System.out.println("Arquivo criado %s foi criado".formatted(ponteiroArquivoR.getName()));
            } else {
                System.out.println("A imagem %s ja existe".formatted(ponteiroArquivoR.getName()));
            }
            if (ponteiroArquivoG.createNewFile()) {
                System.out.println("Arquivo criado %s foi criado".formatted(ponteiroArquivoG.getName()));
            } else {
                System.out.println("A imagem %s ja existe".formatted(ponteiroArquivoG.getName()));
            }
            if (ponteiroArquivoB.createNewFile()) {
                System.out.println("Arquivo criado %s foi criado".formatted(ponteiroArquivoB.getName()));
            } else {
                System.out.println("A imagem %s ja existe".formatted(ponteiroArquivoB.getName()));
            }
        } catch (IOException e) {
            System.out.println("Ocorreu um erro");
            e.printStackTrace();
        }
        try {
            FileWriter writer = new FileWriter(ponteiroArquivoR);
            //Escrita do cabecalho, comentario, linha e coluna, intensidade do pixel
            writer.write("""
                           %s
                           %s
                           %d %d
                           %d
                           """.formatted("P2", comentario + " gerado pelo java", linha, coluna, intensidade));
            //Escrita da matriz em arquivo
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
                    writer.write("""
                                    %d
                                    """.formatted(matrizR[linhaMatriz][colunaMatriz]));
                }
            }
            writer.close();
            System.out.println("Escrito corretamente no arquivo.");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro na escrita");
            e.printStackTrace();
        }
        try {
            FileWriter writer = new FileWriter(ponteiroArquivoG);
            //Escrita do cabecalho, comentario, linha e coluna, intensidade do pixel
            writer.write("""
                           %s
                           %s
                           %d %d
                           %d
                           """.formatted("P2", comentario + " gerado pelo java", linha, coluna, intensidade));
            //Escrita da matriz em arquivo
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
                    writer.write("""
                                    %d
                                    """.formatted(matrizG[linhaMatriz][colunaMatriz]));
                }
            }
            writer.close();
            System.out.println("Escrito corretamente no arquivo.");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro na escrita");
            e.printStackTrace();
        }
        try {
            FileWriter writer = new FileWriter(ponteiroArquivoB);
            //Escrita do cabecalho, comentario, linha e coluna, intensidade do pixel
            writer.write("""
                           %s
                           %s
                           %d %d
                           %d
                           """.formatted("P2", comentario + " gerado pelo java", linha, coluna, intensidade));
            //Escrita da matriz em arquivo
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
                    writer.write("""
                                    %d
                                    """.formatted(matrizB[linhaMatriz][colunaMatriz]));
                }
            }
            writer.close();
            System.out.println("Escrito corretamente no arquivo.");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro na escrita");
            e.printStackTrace();
        }
    }

    /**
     *
     * @param r
     * @param g
     * @param b
     * @return
     */
    public PPMImage Clarear(int r, int g, int b) {
        Integer[][] newMatrizR = new Integer[linha][coluna], newMatrizG = new Integer[linha][coluna], newMatrizB = new Integer[linha][coluna];
        for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
                if (matrizR[linhaMatriz][colunaMatriz] + r > intensidade) {
                    newMatrizR[linhaMatriz][colunaMatriz] = intensidade;
                } else {
                    newMatrizR[linhaMatriz][colunaMatriz] = matrizR[linhaMatriz][colunaMatriz] + r;
                }
                if (matrizG[linhaMatriz][colunaMatriz] + g > intensidade) {
                    newMatrizG[linhaMatriz][colunaMatriz] = intensidade;
                } else {
                    newMatrizG[linhaMatriz][colunaMatriz] = matrizG[linhaMatriz][colunaMatriz] + g;
                }
                if (matrizB[linhaMatriz][colunaMatriz] + b > intensidade) {
                    newMatrizB[linhaMatriz][colunaMatriz] = intensidade;
                } else {
                    newMatrizB[linhaMatriz][colunaMatriz] = matrizB[linhaMatriz][colunaMatriz] + b;
                }
            }
        }
        return new PPMImage(newMatrizR, newMatrizG, newMatrizB, linha, coluna, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @param r
     * @param g
     * @param b
     * @return
     */
    public PPMImage Escurecer(int r, int g, int b) {
        Integer[][] newMatrizR = new Integer[linha][coluna], newMatrizG = new Integer[linha][coluna], newMatrizB = new Integer[linha][coluna];
        for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
                if (matrizR[linhaMatriz][colunaMatriz] - r < 0) {
                    newMatrizR[linhaMatriz][colunaMatriz] = 0;
                } else {
                    newMatrizR[linhaMatriz][colunaMatriz] = matrizR[linhaMatriz][colunaMatriz] - r;
                }
                if (matrizG[linhaMatriz][colunaMatriz] - g < 0) {
                    newMatrizG[linhaMatriz][colunaMatriz] = 0;
                } else {
                    newMatrizG[linhaMatriz][colunaMatriz] = matrizG[linhaMatriz][colunaMatriz] - g;
                }
                if (matrizB[linhaMatriz][colunaMatriz] - b < 0) {
                    newMatrizB[linhaMatriz][colunaMatriz] = 0;
                } else {
                    newMatrizB[linhaMatriz][colunaMatriz] = matrizB[linhaMatriz][colunaMatriz] - b;
                }
            }
        }
        return new PPMImage(newMatrizR, newMatrizG, newMatrizB, linha, coluna, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @param r
     * @param g
     * @param b
     * @return
     */
    public PPMImage InvertChannels(String r, String g, String b) {
        Integer[][] newMatrizR = new Integer[linha][coluna], newMatrizG = new Integer[linha][coluna], newMatrizB = new Integer[linha][coluna];
        switch (r) {
            case "r":
                newMatrizR = matrizR;
                break;
            case "g":
                newMatrizR = matrizG;
                break;
            case "b":
                newMatrizR = matrizB;
                break;
            default:
                throw new AssertionError();
        }
        switch (g) {
            case "r":
                newMatrizG = matrizR;
                break;
            case "g":
                newMatrizG = matrizG;
                break;
            case "b":
                newMatrizG = matrizB;
                break;
            default:
                throw new AssertionError();
        }
        switch (b) {
            case "r":
                newMatrizB = matrizR;
                break;
            case "g":
                newMatrizB = matrizG;
                break;
            case "b":
                newMatrizB = matrizB;
                break;
            default:
                throw new AssertionError();
        }
        return new PPMImage(newMatrizR, newMatrizG, newMatrizB, linha, coluna, intensidade, cabecalho, comentario);
    }

}
