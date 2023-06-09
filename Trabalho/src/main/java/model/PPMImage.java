package model;

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
    protected int altura;

    /**
     * Quantidade de coluna da imagems
     */
    protected int largura;

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
        this.altura = linha;
        this.largura = coluna;
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
                    altura = Integer.parseInt(width_height[0]);
                    largura = Integer.parseInt(width_height[1]);
                    intensidade = readInteger(fileInputStream);
                    matrizR = new Integer[altura][largura];
                    matrizG = new Integer[altura][largura];
                    matrizB = new Integer[altura][largura];
                    System.out.println("""
                           Linha: %d
                           coluna: %d
                           Intensidade: %d
                           Cabecalho: %s
                           Comentario: %s
                           """.formatted(altura, largura, intensidade, cabecalho, comentario));
                    // Create a byte array to hold the pixel values
                    byte[] pixels = new byte[altura * largura * 3];
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
                    for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
                        for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
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
                    altura = Integer.parseInt(ColunaLinhaArquivo[0]);
                    largura = Integer.parseInt(ColunaLinhaArquivo[1]);
                    matrizR = new Integer[altura][largura];
                    matrizG = new Integer[altura][largura];
                    matrizB = new Integer[altura][largura];
                    System.out.println("""
                           Nome do arquivo: %s
                           linha: %d
                           coluna: %d
                           Intensidade: %d
                           Cabecalho: %s
                           Comentario: %s
                           """.formatted(ArquivoObjeto.getName(), altura, largura, intensidade, cabecalho, comentario));
                    //Armazenamento do conteudo da imagem
                    while (Leitor.hasNext()) {
                        for (int colunaVetor = 0; colunaVetor < altura; colunaVetor++) {
                            for (int linhaVetor = 0; linhaVetor < largura; linhaVetor++) {
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
                           """.formatted(cabecalho, comentario, altura, largura, intensidade));
            //Escrita do cabecalho, comentario, linha e coluna, intensidade do pixel
            escritor.write("""
                           %s
                           %s
                           %d %d
                           %d
                           """.formatted(cabecalho, comentario + " gerado pelo java", largura, altura, intensidade));
            //Escrita da matriz em arquivo
            for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
                for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
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
     * @param R
     * @param G
     * @param todos
     * @param B
     */
    public void saveImageChannels(String filepath, boolean R, boolean G, boolean B, boolean todos) {
        try {
            if (R) {
                File ponteiroArquivoR = new File(filepath + "R.pgm");
                if (ponteiroArquivoR.createNewFile()) {
                    System.out.println("Arquivo criado %s foi criado".formatted(ponteiroArquivoR.getName()));
                } else {
                    System.out.println("A imagem %s ja existe".formatted(ponteiroArquivoR.getName()));
                }
                FileWriter writer = new FileWriter(ponteiroArquivoR);
                //Escrita do cabecalho, comentario, linha e coluna, intensidade do pixel
                writer.write("""
                           %s
                           %s
                           %d %d
                           %d
                           """.formatted("P2", comentario + " gerado pelo java", altura, largura, intensidade));
                //Escrita da matriz em arquivo
                for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
                    for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
                        writer.write("""
                                    %d
                                    """.formatted(matrizR[linhaMatriz][colunaMatriz]));
                    }
                }
                writer.close();
                System.out.println("Escrito corretamente no arquivo.");
            }
            if (G) {
                File ponteiroArquivoG = new File(filepath + "G.pgm");
                if (ponteiroArquivoG.createNewFile()) {
                    System.out.println("Arquivo criado %s foi criado".formatted(ponteiroArquivoG.getName()));
                } else {
                    System.out.println("A imagem %s ja existe".formatted(ponteiroArquivoG.getName()));
                }
                FileWriter writer = new FileWriter(ponteiroArquivoG);
                //Escrita do cabecalho, comentario, linha e coluna, intensidade do pixel
                writer.write("""
                           %s
                           %s
                           %d %d
                           %d
                           """.formatted("P2", comentario + " gerado pelo java", altura, largura, intensidade));
                //Escrita da matriz em arquivo
                for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
                    for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
                        writer.write("""
                                    %d
                                    """.formatted(matrizG[linhaMatriz][colunaMatriz]));
                    }
                }
                writer.close();
                System.out.println("Escrito corretamente no arquivo.");
            }

            if (B) {
                File ponteiroArquivoB = new File(filepath + "B.pgm");
                if (ponteiroArquivoB.createNewFile()) {
                    System.out.println("Arquivo criado %s foi criado".formatted(ponteiroArquivoB.getName()));
                } else {
                    System.out.println("A imagem %s ja existe".formatted(ponteiroArquivoB.getName()));
                }
                FileWriter writer = new FileWriter(ponteiroArquivoB);
                //Escrita do cabecalho, comentario, linha e coluna, intensidade do pixel
                writer.write("""
                           %s
                           %s
                           %d %d
                           %d
                           """.formatted("P2", comentario + " gerado pelo java", altura, largura, intensidade));
                //Escrita da matriz em arquivo
                for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
                    for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
                        writer.write("""
                                    %d
                                    """.formatted(matrizB[linhaMatriz][colunaMatriz]));
                    }
                }
                writer.close();
                System.out.println("Escrito corretamente no arquivo.");
            }

            if (todos) {
                File ponteiroArquivoR = new File(filepath + "R.pgm");
                if (ponteiroArquivoR.createNewFile()) {
                    System.out.println("Arquivo criado %s foi criado".formatted(ponteiroArquivoR.getName()));
                } else {
                    System.out.println("A imagem %s ja existe".formatted(ponteiroArquivoR.getName()));
                }
                FileWriter writer = new FileWriter(ponteiroArquivoR);
                //Escrita do cabecalho, comentario, linha e coluna, intensidade do pixel
                writer.write("""
                           %s
                           %s
                           %d %d
                           %d
                           """.formatted("P2", comentario + " gerado pelo java", altura, largura, intensidade));
                //Escrita da matriz em arquivo
                for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
                    for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
                        writer.write("""
                                    %d
                                    """.formatted(matrizR[linhaMatriz][colunaMatriz]));
                    }
                }
                writer.close();
                System.out.println("Escrito corretamente no arquivo.");

                File ponteiroArquivoG = new File(filepath + "G.pgm");
                if (ponteiroArquivoG.createNewFile()) {
                    System.out.println("Arquivo criado %s foi criado".formatted(ponteiroArquivoG.getName()));
                } else {
                    System.out.println("A imagem %s ja existe".formatted(ponteiroArquivoG.getName()));
                }
                writer = new FileWriter(ponteiroArquivoG);
                //Escrita do cabecalho, comentario, linha e coluna, intensidade do pixel
                writer.write("""
                           %s
                           %s
                           %d %d
                           %d
                           """.formatted("P2", comentario + " gerado pelo java", altura, largura, intensidade));
                //Escrita da matriz em arquivo
                for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
                    for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
                        writer.write("""
                                    %d
                                    """.formatted(matrizG[linhaMatriz][colunaMatriz]));
                    }
                }
                writer.close();
                System.out.println("Escrito corretamente no arquivo.");
                File ponteiroArquivoB = new File(filepath + "B.pgm");
                if (ponteiroArquivoB.createNewFile()) {
                    System.out.println("Arquivo criado %s foi criado".formatted(ponteiroArquivoB.getName()));
                } else {
                    System.out.println("A imagem %s ja existe".formatted(ponteiroArquivoB.getName()));
                }
                writer = new FileWriter(ponteiroArquivoB);
                //Escrita do cabecalho, comentario, linha e coluna, intensidade do pixel
                writer.write("""
                           %s
                           %s
                           %d %d
                           %d
                           """.formatted("P2", comentario + " gerado pelo java", altura, largura, intensidade));
                //Escrita da matriz em arquivo
                for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
                    for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
                        writer.write("""
                                    %d
                                    """.formatted(matrizB[linhaMatriz][colunaMatriz]));
                    }
                }
                writer.close();
                System.out.println("Escrito corretamente no arquivo.");
            }
        } catch (IOException e) {
            System.out.println("Ocorreu um erro");
            e.printStackTrace();
        }
    }

    public PGMImage saveImageChannels(boolean R, boolean G, boolean B) {
        if (R) {
            return new PGMImage(matrizR, altura, largura, intensidade, "P2", comentario);
        }
        if (G) {
            return new PGMImage(matrizG, altura, largura, intensidade, "P2", comentario);
        }
        if (B) {
            return new PGMImage(matrizB, altura, largura, intensidade, "P2", comentario);
        }
        return null;
    }

    /**
     *
     * @param r
     * @param g
     * @param b
     * @return
     */
    public PPMImage Clarear(int r, int g, int b) {
        Integer[][] newMatrizR = new Integer[altura][largura], newMatrizG = new Integer[altura][largura], newMatrizB = new Integer[altura][largura];
        for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
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
        return new PPMImage(newMatrizR, newMatrizG, newMatrizB, altura, largura, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @param r
     * @param g
     * @param b
     * @return
     */
    public PPMImage Escurecer(int r, int g, int b) {
        Integer[][] newMatrizR = new Integer[altura][largura], newMatrizG = new Integer[altura][largura], newMatrizB = new Integer[altura][largura];
        for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
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
        return new PPMImage(newMatrizR, newMatrizG, newMatrizB, altura, largura, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @param r
     * @param g
     * @param b
     * @return
     */
    public PPMImage InvertChannels(String r, String g, String b) {
        Integer[][] newMatrizR = new Integer[altura][largura], newMatrizG = new Integer[altura][largura], newMatrizB = new Integer[altura][largura];
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
        return new PPMImage(newMatrizR, newMatrizG, newMatrizB, altura, largura, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @return
     */
    public String getInformation() {
        return """
            Altura: %d
            Largura: %d
            Quantidade de bits: %d
            Cabeçalho: %s
            Comentário: %s
               """.formatted(altura, largura, intensidade, cabecalho, comentario);
    }
}
