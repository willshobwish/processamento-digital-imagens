package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Math.pow;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/**
 *
 * @author Willian
 */
public class PGMImage {

    /**
     *
     */
    protected Integer[][] Matriz;

    /**
     *
     */
    protected int altura;

    /**
     *
     */
    protected int largura;

    /**
     *
     */
    protected int intensidade;

    /**
     *
     */
    protected String cabecalho;

    /**
     *
     */
    protected String comentario;

    /**
     *
     * @param Matriz
     * @param altura
     * @param lagura
     * @param intensidade
     * @param cabecalho
     * @param comentario
     */
    public PGMImage(Integer[][] Matriz, int altura, int lagura, int intensidade, String cabecalho, String comentario) {
        this.Matriz = Matriz;
        this.altura = altura;
        this.largura = lagura;
        this.intensidade = intensidade;
        this.cabecalho = cabecalho;
        this.comentario = comentario;
    }

    public PGMImage(String filepath) {
        try {
            System.out.println("Abertura de imagem PGM binario");
            FileInputStream fileInputStream = new FileInputStream(filepath);
            String magic = readLineBinary(fileInputStream);
            fileInputStream.close();
            if (magic.equals("P5")) {
//                Para tratar arquivos binários é necessário utilizar um método mais "primitivo" do java que tem controle sobre a quantidade de leitura de bytes
                try {
                    fileInputStream = new FileInputStream(filepath);
//                    Leitura do cabeçalho
//                    Define o objeto matriz para P2 porque será escrito em ASCII posteriormente
                    cabecalho = "P2";
//                    Pula o número mágico para o próxima linha
                    readLineBinary(fileInputStream);
                    comentario = readLineBinary(fileInputStream);
//                    Como é possível ler somente uma linha direta, é necessário dividir a string e definir cada parte como linha e coluna
                    String[] width_height = readLineBinary(fileInputStream).split(" ");
                    altura = Integer.parseInt(width_height[1]);
                    largura = Integer.parseInt(width_height[0]);
                    intensidade = readInteger(fileInputStream);
                    Matriz = new Integer[altura][largura];
                    System.out.println("""
                           Linha: %d
                           coluna: %d
                           Intensidade: %d
                           Cabecalho: %s
                           Comentario: %s
                           """.formatted(altura, largura, intensidade, cabecalho, comentario));
                    // Create a byte array to hold the pixel values
                    byte[] pixels = new byte[altura * largura];
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
                            Matriz[linhaMatriz][colunaMatriz] = pixels[aux] & 0xFF;
                            aux++;
                        }
                    }
                    fileInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (magic.equals("P2")) {
//                Para ler arquivos em ASCII podemos utilizar métodos menos primitivos e temos métodos mais simples e direto
                try {
                    System.out.println("Abertura de imagem PGM ASCII");
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
                    this.altura = Integer.parseInt(ColunaLinhaArquivo[1]);
                    this.largura = Integer.parseInt(ColunaLinhaArquivo[0]);
                    this.Matriz = new Integer[altura][largura];
                    System.out.println("""
                           Nome do arquivo: %s
                           Altura: %d
                           Largura: %d
                           Intensidade: %d
                           Cabecalho: %s
                           Comentario: %s
                           """.formatted(ArquivoObjeto.getName(), altura, largura, intensidade, cabecalho, comentario));
                    //Armazenamento do conteudo da imagem
                    while (Leitor.hasNext()) {
                        for (int linhaAltura = 0; linhaAltura < altura; linhaAltura++) {
                            for (int linhaLargura = 0; linhaLargura < largura; linhaLargura++) {
                                Matriz[linhaAltura][linhaLargura] = Integer.valueOf(Leitor.next());
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

    public Integer[][] getMatriz() {
        return Matriz;
    }

    public int getAltura() {
        return altura;
    }

    public int getLargura() {
        return largura;
    }

    public int getIntensidade() {
        return intensidade;
    }

    public String getCabecalho() {
        return cabecalho;
    }

    public String getComentario() {
        return comentario;
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
                    escritor.write("%d ".formatted(Matriz[linhaMatriz][colunaMatriz]));
                }
                escritor.write("\n");
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
     * @return
     */
    public PGMImage Negative() {
        Integer ImagemNegativa[][] = new Integer[altura][largura];
        for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
                ImagemNegativa[linhaMatriz][colunaMatriz] = intensidade - Matriz[linhaMatriz][colunaMatriz];
            }
        }
        return new PGMImage(ImagemNegativa, altura, largura, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @param quantidade
     * @return
     */
    public PGMImage Darken(int quantidade) {
        Integer ImagemEscurecida[][] = new Integer[altura][largura];
        for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
                if (Matriz[linhaMatriz][colunaMatriz] - quantidade < 0) {
                    ImagemEscurecida[linhaMatriz][colunaMatriz] = 0;
                } else {
                    ImagemEscurecida[linhaMatriz][colunaMatriz] = Matriz[linhaMatriz][colunaMatriz] - quantidade;
                }
            }
        }
        return new PGMImage(ImagemEscurecida, altura, largura, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @param Quantidade
     * @return
     */
    public PGMImage ClarearAdicao(int Quantidade) {
        Integer ImagemClareada[][] = new Integer[altura][largura];
        for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {

                if (Matriz[linhaMatriz][colunaMatriz] + Quantidade > intensidade) {
                    ImagemClareada[linhaMatriz][colunaMatriz] = intensidade;
                } else {
                    ImagemClareada[linhaMatriz][colunaMatriz] = Matriz[linhaMatriz][colunaMatriz] + Quantidade;
                }

            }
        }
        return new PGMImage(ImagemClareada, altura, largura, intensidade, cabecalho, comentario);

    }

    /**
     *
     * @param Quantidade
     * @return
     */
    public PGMImage ClarearMultiplicao(float Quantidade) {
        Integer ImagemClareada[][] = new Integer[altura][largura];
        for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
                float temp = Matriz[linhaMatriz][colunaMatriz] * Quantidade;
                if (temp > intensidade) {
                    temp = intensidade;
                }
                ImagemClareada[linhaMatriz][colunaMatriz] = (int) temp;
            }
        }
        return new PGMImage(ImagemClareada, altura, largura, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @return
     */
    public PGMImage rotateMinus90() {
        Integer ImagemRotacionada[][] = new Integer[largura][altura];
        Comentario("Rotacao -90", largura, altura);
        for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
                System.out.println("linha coluna: %d x %d = %d".formatted(colunaMatriz, linhaMatriz, Matriz[linhaMatriz][colunaMatriz]));
                ImagemRotacionada[largura - 1 - colunaMatriz][linhaMatriz] = Matriz[linhaMatriz][colunaMatriz];
            }
        }
        return new PGMImage(ImagemRotacionada, largura, altura, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @return
     */
    public PGMImage rotate90() {
//Quantidade de linha e coluna inversa
        Integer ImagemRotacionada[][] = new Integer[largura][altura];
        for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
                ImagemRotacionada[colunaMatriz][altura - 1 - linhaMatriz] = Matriz[linhaMatriz][colunaMatriz];
            }
        }
        return new PGMImage(ImagemRotacionada, largura, altura, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @return
     */
    public PGMImage rotate180() {
        Integer ImagemRotacionada[][] = new Integer[altura][largura];
        for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
                ImagemRotacionada[altura - 1 - linhaMatriz][largura - 1 - colunaMatriz] = Matriz[linhaMatriz][colunaMatriz];
            }
        }
        return new PGMImage(ImagemRotacionada, altura, largura, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @return
     */
    public PGMImage test() {
        Integer ImagemTeste[][] = new Integer[altura][largura];
        for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
                ImagemTeste[linhaMatriz][colunaMatriz] = Matriz[linhaMatriz][colunaMatriz];
            }
        }
        return new PGMImage(ImagemTeste, altura, largura, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @param funcao
     * @param colunaModificada
     * @param linhaModificada
     */
    public void Comentario(String funcao, int colunaModificada, int linhaModificada) {
        System.out.println("""
                           %s
                           linha e coluna original: %d x %d
                           linha e coluna modificado: %d x %d
                           """.formatted(funcao, altura, largura, colunaModificada, linhaModificada));
    }

    /**
     *
     * @return
     */
    public PGMImage espelhamentoHorizontal() {
        Integer Espelhada[][] = new Integer[altura][largura];
        Comentario("Espelhamento horizontal", altura, largura);
        for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
                Espelhada[linhaMatriz][largura - 1 - colunaMatriz] = Matriz[linhaMatriz][colunaMatriz];
            }
        }
        return new PGMImage(Espelhada, altura, largura, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @return
     */
    public PGMImage espelhamentoVertical() {
        Integer Espelhada[][] = new Integer[altura][largura];
        for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
                Espelhada[altura - 1 - linhaMatriz][colunaMatriz] = Matriz[linhaMatriz][colunaMatriz];
            }
        }
        return new PGMImage(Espelhada, altura, largura, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @param quantidadeDeNiveis
     * @return
     */
    public PGMImage reducaoNivel(int quantidadeDeNiveis) {
        Integer Reduzida[][] = new Integer[altura][largura];
        for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
                Reduzida[linhaMatriz][colunaMatriz] = (int) Math.ceil(Matriz[linhaMatriz][colunaMatriz] * (quantidadeDeNiveis - 1) / intensidade);
            }
        }
        return new PGMImage(Reduzida, altura, largura, quantidadeDeNiveis, cabecalho, comentario);
    }

    /**
     *
     * @param limiar
     * @return
     */
    public PGMImage binarizacao(int limiar) {
        Integer Reduzida[][] = new Integer[altura][largura];
        for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
                if (Matriz[linhaMatriz][colunaMatriz] <= limiar) {
                    Reduzida[linhaMatriz][colunaMatriz] = intensidade;
                } else {
                    Reduzida[linhaMatriz][colunaMatriz] = 0;
                }
            }
        }
        return new PGMImage(Reduzida, altura, largura, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @param start
     * @param end
     * @param level
     * @param greyLevel
     * @return
     */
    public PGMImage binaryRange(int start, int end, int level, int greyLevel) {
        Integer Reduzida[][] = new Integer[altura][largura];
        for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
                if (Matriz[linhaMatriz][colunaMatriz] > start && Matriz[linhaMatriz][colunaMatriz] < end) {
                    Reduzida[linhaMatriz][colunaMatriz] = level;
                } else {
                    Reduzida[linhaMatriz][colunaMatriz] = greyLevel;
                }
            }
        }
        return new PGMImage(Reduzida, altura, largura, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @param start
     * @param end
     * @param quantidade
     * @return
     */
    public PGMImage rangeHighlight(int start, int end, int quantidade) {
        Integer Reduzida[][] = new Integer[altura][largura];
        for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
                if (Matriz[linhaMatriz][colunaMatriz] > start && Matriz[linhaMatriz][colunaMatriz] < end) {
                    Reduzida[linhaMatriz][colunaMatriz] = quantidade;
                } else {
                    Reduzida[linhaMatriz][colunaMatriz] = Matriz[linhaMatriz][colunaMatriz];
                }
            }
        }
        return new PGMImage(Reduzida, altura, largura, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @param c
     * @param a
     * @return
     */
    public PGMImage subtract(int c, int a) {
        Integer MatrizCorreta[][] = new Integer[altura][largura];
        for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
                double resultadoDivisao = (double) Matriz[linhaMatriz][colunaMatriz] / intensidade,
                        resultadoPotencia = pow(resultadoDivisao, a),
                        resultadoMultiplicacao = resultadoPotencia * c;
                int resultadoArredondamento = (int) (resultadoMultiplicacao * intensidade);
                if (resultadoArredondamento > intensidade) {
                    MatrizCorreta[linhaMatriz][colunaMatriz] = intensidade - Matriz[linhaMatriz][colunaMatriz];
                } else {
                    MatrizCorreta[linhaMatriz][colunaMatriz] = resultadoArredondamento - Matriz[linhaMatriz][colunaMatriz];
                }
            }
        }
        return new PGMImage(MatrizCorreta, altura, largura, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @param c
     * @param beta
     * @return
     */
    public PGMImage transformacaoPotencia(int c, double beta) {
        Integer MatrizCorreta[][] = new Integer[altura][largura];
        for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
                double resultadoDivisao = (double) Matriz[linhaMatriz][colunaMatriz] / intensidade,
                        resultadoPotencia = pow(resultadoDivisao, beta),
                        resultadoMultiplicacao = resultadoPotencia * c;
                int resultadoArredondamento = (int) (resultadoMultiplicacao * intensidade);
                if (resultadoArredondamento > intensidade) {
                    MatrizCorreta[linhaMatriz][colunaMatriz] = intensidade;
                } else {
                    MatrizCorreta[linhaMatriz][colunaMatriz] = resultadoArredondamento;
                }
            }
        }
        return new PGMImage(MatrizCorreta, altura, largura, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @param quantidadeAumento
     * @return
     */
    public PGMImage aumentaResolucao(int quantidadeAumento) {
        int colunaAumentada = altura * quantidadeAumento, linhaAumentada = largura * quantidadeAumento;
        Integer matrizAumentada[][] = new Integer[colunaAumentada][linhaAumentada];
        for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
                for (int quantidadeLinha = 0; quantidadeLinha < quantidadeAumento; quantidadeLinha++) {
                    for (int quantidadeColuna = 0; quantidadeColuna < quantidadeAumento; quantidadeColuna++) {
                        matrizAumentada[linhaMatriz * quantidadeAumento + quantidadeLinha][colunaMatriz * quantidadeAumento + quantidadeColuna] = Matriz[linhaMatriz][colunaMatriz];
                    }
                }
            }
        }
        return new PGMImage(matrizAumentada, colunaAumentada, linhaAumentada, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @param quantidadeReducao
     * @return
     */
    public PGMImage diminuiResolucao(int quantidadeReducao) {
        int colunaReduzida = altura / quantidadeReducao, linhaReduzida = largura / quantidadeReducao, somaPixeis = 0;
        Integer[][] matrizReduzida = new Integer[colunaReduzida][linhaReduzida];
        for (int linhaMatriz = 0; linhaMatriz < colunaReduzida; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < linhaReduzida; colunaMatriz++) {
                for (int variavelColuna = 0; variavelColuna < quantidadeReducao; variavelColuna++) {
                    for (int variavelLinha = 0; variavelLinha < quantidadeReducao; variavelLinha++) {
                        somaPixeis += Matriz[linhaMatriz * quantidadeReducao + variavelColuna][colunaMatriz * quantidadeReducao + variavelLinha];
                    }
                }
//                Realiza a média, como sempre é um quadrado, pode ser realizado quntidade^quantidade para a média
                matrizReduzida[linhaMatriz][colunaMatriz] = somaPixeis / (int) pow(quantidadeReducao, quantidadeReducao);
                somaPixeis = 0;
            }
        }
        return new PGMImage(matrizReduzida, colunaReduzida, linhaReduzida, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @param filepath
     */
    public void histogram(String filepath) {
        ArrayList<Integer> histograma = new ArrayList<>();
        for (int i = 0; i < intensidade; i++) {
            histograma.add(0);
        }
        for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
                histograma.set(Matriz[linhaMatriz][colunaMatriz], histograma.get(Matriz[linhaMatriz][colunaMatriz]) + 1);
            }
        }
        try {
            File ponteiroArquivo = new File(filepath);
            if (ponteiroArquivo.createNewFile()) {
                System.out.println("O histograma criado %s foi criado".formatted(ponteiroArquivo.getName()));
            } else {
                System.out.println("O histograma %s ja existe".formatted(ponteiroArquivo.getName()));
            }
        } catch (IOException e) {
            System.out.println("Ocorreu um erro");
            e.printStackTrace();
        }
        try {
            FileWriter escritor = new FileWriter(filepath);

            escritor.write("""
                           %s,%s
                           """.formatted("c", "h[c]"));
            for (int i = 1; i < histograma.size() + 1; i++) {
                escritor.write("""
                           %d, %d
                           """.formatted(i, histograma.get(i - 1)));
            }
            escritor.close();
            System.out.println("Escrito corretamente no arquivo.");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro na escrita");
            e.printStackTrace();
        }
    }

    public ArrayList<Integer> histogram() {
        ArrayList<Integer> histograma = new ArrayList<>();
        for (int i = 0; i < intensidade + 1; i++) {
            histograma.add(0);
        }
        for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
                histograma.set(Matriz[linhaMatriz][colunaMatriz], histograma.get(Matriz[linhaMatriz][colunaMatriz]) + 1);
            }
        }
        return histograma;
    }

    public ArrayList<Integer> histogram(Integer MatrizInserida[][], int quantidade, int intensidade) {
        ArrayList<Integer> histograma = new ArrayList<>();
        for (int i = 0; i < intensidade + 1; i++) {
            histograma.add(0);
        }
        for (int linhaMatriz = 0; linhaMatriz < quantidade; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < quantidade; colunaMatriz++) {
                histograma.set(MatrizInserida[linhaMatriz][colunaMatriz], histograma.get(MatrizInserida[linhaMatriz][colunaMatriz]) + 1);
            }
        }
        return histograma;
    }

    public PGMImage equalizacao_histograma() {
        ArrayList<Integer> histograma = histogram(), soma = new ArrayList<>(Collections.nCopies(intensidade + 1, 0));
        ArrayList<Double> nk = new ArrayList<>();
        double somaProbabilidade = 0;
        for (int i = 0; i < intensidade + 1; i++) {
            nk.add(0.0);
        }
        for (int i = 0; i < intensidade + 1; i++) {
            nk.set(i, (double) histograma.get(i) / (altura * largura));
            somaProbabilidade += nk.get(i);
            soma.set(i, (int) Math.floor(intensidade * somaProbabilidade));
            System.out.println(soma.get(i));
        }
        Integer matrizModificada[][] = new Integer[altura][largura];
        for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
                matrizModificada[linhaMatriz][colunaMatriz] = soma.get(Matriz[linhaMatriz][colunaMatriz]);
            }
        }
        return new PGMImage(matrizModificada, altura, largura, intensidade, cabecalho, comentario);
    }

    public PGMImage media(int kernelSize) {
        if (kernelSize % 2 == 0) {
            System.out.println("A quantidade precisa ser impar");
            return null;
        } else {
            int metadePositiva = (int) Math.floor(kernelSize / 2.0);
            int metadeNegativa = metadePositiva * -1;
            Integer MatrizModificada[][] = new Integer[altura - metadePositiva][largura - metadePositiva];

            double SomaMedia = 0;
            for (int alturaMatriz = metadePositiva; alturaMatriz < altura - metadePositiva; alturaMatriz++) {
                for (int larguraMatriz = metadePositiva; larguraMatriz < largura - metadePositiva; larguraMatriz++) {
                    for (int subAltura = metadeNegativa; subAltura < metadePositiva; subAltura++) {
                        for (int subLargura = metadeNegativa; subLargura < metadePositiva; subLargura++) {
                            SomaMedia += Matriz[alturaMatriz + subAltura][larguraMatriz + subLargura];
                        }
                    }
                    MatrizModificada[alturaMatriz - metadePositiva][larguraMatriz - metadePositiva] = (int) SomaMedia / (kernelSize * kernelSize);
                    SomaMedia = 0;
                }
            }
            return new PGMImage(MatrizModificada, altura - (int) Math.ceil(kernelSize / 2.0), largura - (int) Math.ceil(kernelSize / 2.0), intensidade, cabecalho, comentario);
        }
    }

    public PGMImage equalizacaoLocalHistograma(int quantidade) {
        if (quantidade % 2 == 0) {
            System.out.println("A quantidade precisa ser impar");
            return null;
        } else {
            Integer subMatriz[][] = new Integer[quantidade][quantidade], MatrizFinal[][] = Matriz;
            for (int indexLinha = 0; indexLinha < altura / quantidade; indexLinha++) {
                for (int indexColuna = 0; indexColuna < largura / quantidade; indexColuna++) {
                    for (int subLinha = 0; subLinha < quantidade; subLinha++) {
                        for (int subColuna = 0; subColuna < quantidade; subColuna++) {
                            //System.out.print("%d %d|".formatted(indexLinha * quantidade + subLinha, indexColuna * quantidade + subColuna));
                            subMatriz[subLinha][subColuna] = Matriz[indexLinha * quantidade + subLinha][indexColuna * quantidade + subColuna];
                        }
                        System.out.println("");

                    }
                    System.out.println("");

                    ArrayList<Integer> histograma = new ArrayList<>();
                    for (int i = 0; i < intensidade + 1; i++) {
                        histograma.add(0);
                    }
                    for (int linhaMatriz = 0; linhaMatriz < quantidade; linhaMatriz++) {
                        for (int colunaMatriz = 0; colunaMatriz < quantidade; colunaMatriz++) {
                            histograma.set(subMatriz[linhaMatriz][colunaMatriz], histograma.get(subMatriz[linhaMatriz][colunaMatriz]) + 1);
                        }
                    }
                    ArrayList<Integer> soma = new ArrayList<>(Collections.nCopies(intensidade + 1, 0));
                    ArrayList<Double> nk = new ArrayList<>();
                    double somaProbabilidade = 0.0;
                    for (int i = 0; i < intensidade + 1; i++) {
                        nk.add(0.0);
                    }
                    for (int i = 0; i < intensidade + 1; i++) {
                        nk.set(i, (double) histograma.get(i) / (quantidade * quantidade));
                        somaProbabilidade += nk.get(i);

                        soma.set(i, (int) Math.floor(intensidade * somaProbabilidade));
                    }
                    Integer matrizModificada[][] = new Integer[quantidade][quantidade];
                    for (int linhaMatriz = 0; linhaMatriz < quantidade; linhaMatriz++) {
                        for (int colunaMatriz = 0; colunaMatriz < quantidade; colunaMatriz++) {
                            matrizModificada[linhaMatriz][colunaMatriz] = soma.get(subMatriz[linhaMatriz][colunaMatriz]);
                        }
                    }
                    for (int subLinha = 0; subLinha < quantidade; subLinha++) {
                        for (int subColuna = 0; subColuna < quantidade; subColuna++) {
                            MatrizFinal[indexLinha * quantidade + subLinha][indexColuna * quantidade + subColuna] = matrizModificada[subLinha][subColuna];
                        }
                    }
                }
            }
            return new PGMImage(MatrizFinal, altura, largura, intensidade, cabecalho, comentario);
        }
    }

    public PGMImage Mediana(int quantidade) {
        Integer MatrizFinal[][] = new Integer[altura][largura];
        int KernelDistance = quantidade / 2;
//        MatrizFinal = Matriz;

        if (quantidade % 2 == 0) {
            System.out.println("Quantidade nao pode ser par");
        } else {
            int kernel = quantidade / 2;
            System.out.println(kernel);
            for (int linhaMatriz = 0; linhaMatriz < altura - 2; linhaMatriz++) {
                for (int colunaMatriz = 0; colunaMatriz < largura - 2; colunaMatriz++) {
                    ArrayList<Integer> vetorPixel = new ArrayList<>();
                    for (int sublinha = 0; sublinha < quantidade; sublinha++) {
                        for (int subcoluna = 0; subcoluna < quantidade; subcoluna++) {

                            vetorPixel.add(Matriz[linhaMatriz + sublinha][colunaMatriz + subcoluna]);
                        }
                    }
                    vetorPixel.sort(Comparator.naturalOrder());
                    MatrizFinal[linhaMatriz][colunaMatriz] = vetorPixel.get((int) quantidade * quantidade / 2);
                }
            }
        }

        return new PGMImage(MatrizFinal, altura - 2, largura - 2, intensidade, cabecalho, comentario);
    }

    public PGMImage laplaciano() {
        Integer[][] matrizFinal = new Integer[altura][largura];
//        matrizFinal = Matriz;
        int kernelSize = 3;
        Integer[][] matrizConvolucao = {{0, -1, 0}, {-1, 4, -1}, {0, -1, 0}};

        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                int sum = 0;

                // Apply the kernel to the current pixel and its neighbors
                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {
                        int rowIndex = i + k - 3 / 2;
                        int colIndex = j + l - 3 / 2;

                        // Ensure the indices are within the image boundaries
                        if (rowIndex >= 0 && rowIndex < altura && colIndex >= 0 && colIndex < largura) {
                            sum += Matriz[rowIndex][colIndex] * matrizConvolucao[k][l];
                        }
                    }
                }

                // Apply the kernel condition
                if (sum > 255) {
                    matrizFinal[i][j] = 255;
                } else if (sum < 0) {
                    matrizFinal[i][j] = 0;
                } else {
                    matrizFinal[i][j] = sum;
                }
            }
        }
        return new PGMImage(matrizFinal, altura - 2, largura - 2, intensidade, cabecalho, comentario);
    }

    public PGMImage nitidez(int kernelMedia, double constante) {
        PGMImage imagemMedia = new PGMImage(Matriz, altura, largura, intensidade, cabecalho, comentario).media(kernelMedia);
        Integer[][] media = imagemMedia.getMatriz();
        altura = imagemMedia.getAltura();
        largura = imagemMedia.getLargura();
        Integer[][] mascara = new Integer[altura][largura];
        Integer[][] matrizComMascara = new Integer[altura][largura];

        for (int alturaMatriz = 0; alturaMatriz < altura; alturaMatriz++) {
            for (int larguraMatriz = 0; larguraMatriz < largura; larguraMatriz++) {
                mascara[alturaMatriz][larguraMatriz] = Matriz[alturaMatriz][larguraMatriz] - media[alturaMatriz][larguraMatriz];
                matrizComMascara[alturaMatriz][larguraMatriz] = (int) (Matriz[alturaMatriz][larguraMatriz] + mascara[alturaMatriz][larguraMatriz] * constante);
            }
        }
        imagemMedia.saveImage("src\\Assets\\todos\\media.pgm");
        new PGMImage(mascara, altura, largura, intensidade, cabecalho, comentario).saveImage("src\\Assets\\todos\\mascara.pgm");
        return new PGMImage(matrizComMascara, altura, largura, intensidade, cabecalho, comentario);
    }

    public String getInformation() {
        return """
            Linha: %d
            coluna: %d
            Intensidade: %d
            Cabecalho: %s
            Comentario: %s
               """.formatted(altura, largura, intensidade, cabecalho, comentario);
    }
}
