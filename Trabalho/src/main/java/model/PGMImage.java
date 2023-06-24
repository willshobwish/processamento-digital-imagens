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
     * Construtor da classe PGM
     *
     * @param Matriz Vertor de vetor que armazena os pixels de uma imagem
     * @param altura Altura da imagem
     * @param lagura Largura da imagem
     * @param intensidade Quantidade de bits que representa a imagem
     * @param cabecalho Cabecalho da imagem
     * @param comentario Comentarios da imagem
     */
    public PGMImage(Integer[][] Matriz, int altura, int lagura, int intensidade, String cabecalho, String comentario) {
        this.Matriz = Matriz;
        this.altura = altura;
        this.largura = lagura;
        this.intensidade = intensidade;
        this.cabecalho = cabecalho;
        this.comentario = comentario;
    }

    /**
     *
     * @param filepath
     */
    public PGMImage(String filepath) {
        try {
            System.out.println("Abertura de imagem PGM");
            FileInputStream fileInputStream = new FileInputStream(filepath);
            String codificacao = leituraLinhaBinaria(fileInputStream);
            fileInputStream.close();
            if (codificacao.equals("P5")) {
//                Para tratar arquivos binários é necessário utilizar um método mais "primitivo" do java que tem controle sobre a quantidade de leitura de bytes
                try {
                    fileInputStream = new FileInputStream(filepath);
//                    Leitura do cabeçalho
//                    Define o objeto matriz para P2 porque será escrito em ASCII posteriormente
                    cabecalho = "P5";
//                    Pula o número mágico para o próxima linha
                    leituraLinhaBinaria(fileInputStream);
                    comentario = leituraLinhaBinaria(fileInputStream);
//                    Como é possível ler somente uma linha direta, é necessário dividir a string e definir cada parte como linha e coluna
                    String[] width_height = leituraLinhaBinaria(fileInputStream).split(" ");
                    altura = Integer.parseInt(width_height[1]);
                    largura = Integer.parseInt(width_height[0]);
                    intensidade = leituraInteiro(fileInputStream);
                    Matriz = new Integer[altura][largura];
                    System.out.println("""
                           Linha: %d
                           coluna: %d
                           Intensidade: %d
                           Cabecalho: %s
                           Comentario: %s
                           """.formatted(altura, largura, intensidade, cabecalho, comentario));
                    // Cria um array de bytes
                    byte[] pixels = new byte[altura * largura];
                    // Cria um contador de quantidades de bytes lidos
                    int bytesLidos = 0;
                    while (bytesLidos < pixels.length) {
                        int count = fileInputStream.read(pixels, bytesLidos, pixels.length - bytesLidos);
                        if (count == -1) {
                            break;
                        }
                        bytesLidos += count;
                    }
                    int auxiliar = 0;
                    for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
                        for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
//                            Transforma os bytes em numeros
                            Matriz[linhaMatriz][colunaMatriz] = pixels[auxiliar] & 0xFF;
                            auxiliar++;
                        }
                    }
                    fileInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (codificacao.equals("P2")) {
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

//    Para ler a quantidade de bits de imagens binarias
    private static int leituraInteiro(FileInputStream fileInputStream) {
        String s = "";
        try {
            s = leituraLinhaBinaria(fileInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Integer.parseInt(s);
    }

//    Para leitura de imagens em formato binario
    private static String leituraLinhaBinaria(FileInputStream fileInputStream) {
//        Permite a alocacao de strings dinamicamente
        StringBuilder stringBuilder = new StringBuilder();
        try {
            int c;
//            Concatena as informacoes (char) da imagem em formato binario
            while ((c = fileInputStream.read()) != -1 && (c != '\n')) {
//                Junta a informacao lida no final
                stringBuilder.append((char) c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        O metodo trim seria para remover os espacos em brancos
        return stringBuilder.toString().trim();
    }

    /**
     *
     * @return
     */
    public Integer[][] getMatriz() {
        return Matriz;
    }

    /**
     *
     * @return
     */
    public int getAltura() {
        return altura;
    }

    /**
     *
     * @return
     */
    public int getLargura() {
        return largura;
    }

    /**
     *
     * @return
     */
    public int getIntensidade() {
        return intensidade;
    }

    /**
     *
     * @return
     */
    public String getCabecalho() {
        return cabecalho;
    }

    /**
     *
     * @return
     */
    public String getComentario() {
        return comentario;
    }

    /**
     *
     * @param filepath
     */
    public void salvarImagem(String filepath) {
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
            String cabecalhoGravacao = "P2";
            escritor.write("""
                           %s
                           %s
                           %d %d
                           %d
                           """.formatted(cabecalhoGravacao, comentario + " gerado pelo java", largura, altura, intensidade));
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
     * Torna a imagem negativa
     *
     * @return Um objeto do tipo PGM com o processamento aplicado
     */
    public PGMImage negativo() {
        Integer ImagemNegativa[][] = new Integer[altura][largura];
        for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
//                Sera o inverso do pixel atual
                ImagemNegativa[linhaMatriz][colunaMatriz] = intensidade - Matriz[linhaMatriz][colunaMatriz];
            }
        }
        return new PGMImage(ImagemNegativa, altura, largura, intensidade, cabecalho, comentario);
    }

    /**
     * Subtrai a constante dos pixels da imagem
     *
     * @param quantidade Constante que sera subtraido de cada pixel
     * @return Um objeto do tipo PGM com o processamento aplicado
     */
    public PGMImage escurecer(int quantidade) {
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
     * Clareia a imagem somando os pixels a constante
     *
     * @param Quantidade Constante que sera somado a cada pixel da imagem
     * @return Um objeto do tipo PGM com o processamento aplicado
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
     * Clareia uma imagem multiplicando uma constante pelos pixels da imagem
     *
     * @param Quantidade Constante que sera multiplicado
     * @return Um objeto do tipo PGM com o processamento aplicado
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
     * Aplica a rotacao em -90 graus em uma imagem
     *
     * @return Um objeto do tipo PGM com o processamento aplicado
     */
    public PGMImage rotacaoMenos90() {
        Integer ImagemRotacionada[][] = new Integer[largura][altura];
        for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
                System.out.println("linha coluna: %d x %d = %d".formatted(colunaMatriz, linhaMatriz, Matriz[linhaMatriz][colunaMatriz]));
                ImagemRotacionada[largura - 1 - colunaMatriz][linhaMatriz] = Matriz[linhaMatriz][colunaMatriz];
            }
        }
        return new PGMImage(ImagemRotacionada, largura, altura, intensidade, cabecalho, comentario);
    }

    /**
     * Aplica a rotacao em 90 graus em uma imagem
     *
     * @return Um objeto do tipo PGM com o processamento aplicado
     */
    public PGMImage rotacao90() {
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
     * Aplica a rotacao em 180 graus em uma imagem
     *
     * @return Um objeto do tipo PGM com o processamento aplicado
     */
    public PGMImage rotacao180() {
        Integer ImagemRotacionada[][] = new Integer[altura][largura];
        for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
                ImagemRotacionada[altura - 1 - linhaMatriz][largura - 1 - colunaMatriz] = Matriz[linhaMatriz][colunaMatriz];
            }
        }
        return new PGMImage(ImagemRotacionada, altura, largura, intensidade, cabecalho, comentario);
    }

    /**
     * Aplica o espelhamento horizontal em uma imagem
     *
     * @return Um objeto do tipo PGM com o processamento aplicado
     */
    public PGMImage espelhamentoHorizontal() {
        Integer Espelhada[][] = new Integer[altura][largura];
        for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
                Espelhada[linhaMatriz][largura - 1 - colunaMatriz] = Matriz[linhaMatriz][colunaMatriz];
            }
        }
        return new PGMImage(Espelhada, altura, largura, intensidade, cabecalho, comentario);
    }

    /**
     * Aplica o espelhamento vertical em uma imagem
     *
     * @return Um objeto do tipo PGM com o processamento aplicado
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
     * Reduz a quantidade de bits para representacao de uma imagem
     *
     * @param quantidadeDeNiveis Quantidade de bits total no final
     * @return Um objeto do tipo PGM com o processamento aplicado
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
     * @return Um objeto do tipo PGM com o processamento aplicado
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
     * @return Um objeto do tipo PGM com o processamento aplicado
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
     * @return Um objeto do tipo PGM com o processamento aplicado
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
     * @return Um objeto do tipo PGM com o processamento aplicado
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
     * @return Um objeto do tipo PGM com o processamento aplicado
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
     * @return Um objeto do tipo PGM com o processamento aplicado
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
     * @return Um objeto do tipo PGM com o processamento aplicado
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
    public void histograma(String filepath) {
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

    /**
     *
     * @return
     */
    public ArrayList<Integer> histograma() {
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

    /**
     *
     * @param MatrizInserida
     * @param quantidade
     * @param intensidade
     * @return
     */
    public ArrayList<Integer> histograma(Integer MatrizInserida[][], int quantidade, int intensidade) {
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

    /**
     *
     * @return Um objeto do tipo PGM com o processamento aplicado
     */
    public PGMImage equalizacaoHistograma() {
//        Pega o histograma da imagem atual
        ArrayList<Integer> histograma = PGMImage.this.histograma(), soma = new ArrayList<>(Collections.nCopies(intensidade + 1, 0));
//        Instanciacao do vetor de probabilidades
        ArrayList<Double> nk = new ArrayList<>();
//        Deve-se somar as probabilidades, segundo a formula
        double somaProbabilidade = 0;
//        Inicializa o vetor de probabilidade, caso contrario sera armazenado null
        for (int i = 0; i < intensidade + 1; i++) {
            nk.add(0.0);
        }
//        Realiza o calculo de probabilidade de pixels
        for (int i = 0; i < intensidade + 1; i++) {
//            Pixel atual/Quantidade do pixel atual presente/(altura da imagem * largura da imagem)
            nk.set(i, (double) histograma.get(i) / (altura * largura));
//            Soma-se a probabilidade anterior com o total
            somaProbabilidade += nk.get(i);
            soma.set(i, (int) Math.floor(intensidade * somaProbabilidade));
//            System.out.println(soma.get(i));
        }
//        Aplica a probabilidade na matriz
        Integer matrizModificada[][] = new Integer[altura][largura];
        for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
                matrizModificada[linhaMatriz][colunaMatriz] = soma.get(Matriz[linhaMatriz][colunaMatriz]);
            }
        }
        return new PGMImage(matrizModificada, altura, largura, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @param kernelSize
     * @return Um objeto do tipo PGM com o processamento aplicado
     */
    public PGMImage media(int kernelSize) {
        if (kernelSize % 2 == 0) {
            System.out.println("A quantidade precisa ser impar");
            return null;
        } else {
            int kernel = kernelSize / 2;
            Integer MatrizModificada[][] = Matriz;
            double SomaMedia = 0;
            for (int alturaMatriz = kernel; alturaMatriz < altura - kernel; alturaMatriz++) {
                for (int larguraMatriz = kernel; larguraMatriz < largura - kernel; larguraMatriz++) {
                    for (int subAltura = kernel * -1; subAltura <= kernel; subAltura++) {
                        for (int subLargura = kernel * -1; subLargura <= kernel; subLargura++) {
                            SomaMedia += Matriz[alturaMatriz + subAltura][larguraMatriz + subLargura];
                        }
                    }
                    MatrizModificada[alturaMatriz][larguraMatriz] = (int) SomaMedia / (kernelSize * kernelSize);
                    SomaMedia = 0;
                }
            }
            return new PGMImage(MatrizModificada, altura, largura, intensidade, cabecalho, comentario);
        }
    }

    /**
     *
     * @param quantidade
     * @return Um objeto do tipo PGM com o processamento aplicado
     */
    public PGMImage equalizacaoLocalHistograma(int quantidade) {
//        Checa se a quantidade eh impar
        if (quantidade % 2 == 0) {
            System.out.println("A quantidade precisa ser impar");
            return null;
        } else {
            Integer subMatriz[][] = new Integer[quantidade][quantidade], MatrizFinal[][] = Matriz;
            for (int indexLinha = 0; indexLinha < altura / quantidade; indexLinha++) {
                for (int indexColuna = 0; indexColuna < largura / quantidade; indexColuna++) {
                    for (int subLinha = 0; subLinha < quantidade; subLinha++) {
                        for (int subColuna = 0; subColuna < quantidade; subColuna++) {
                            subMatriz[subLinha][subColuna] = Matriz[indexLinha * quantidade + subLinha][indexColuna * quantidade + subColuna];
                        }
                    }
//                    Inicializa o vetor de histograma com 0
                    ArrayList<Integer> histograma = new ArrayList<>();
                    for (int i = 0; i < intensidade + 1; i++) {
                        histograma.add(0);
                    }
//                    Define a quantidade de pixels presentes, onde a intensidade eh definido pelo indice e a quantidade eh definido pelo valor que o indice possui
                    for (int linhaMatriz = 0; linhaMatriz < quantidade; linhaMatriz++) {
                        for (int colunaMatriz = 0; colunaMatriz < quantidade; colunaMatriz++) {
                            histograma.set(subMatriz[linhaMatriz][colunaMatriz], histograma.get(subMatriz[linhaMatriz][colunaMatriz]) + 1);
                        }
                    }
//                    Faz o calculo de probabilidade e adiciona no vetor
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

    /**
     * Realiza o processamento de mediana na imagem
     *
     * @param quantidade
     * @return Um objeto do tipo PGM com o processamento aplicado
     */
    public PGMImage mediana(int quantidade) {
        Integer MatrizFinal[][] = Matriz;
        int KernelDistance = quantidade / 2;
        if (quantidade % 2 == 0) {
            System.out.println("Quantidade nao pode ser par");
        } else {
//            Os dois primeiros lacos de repeticao para percorrer toda a imagem
            for (int linhaMatriz = KernelDistance; linhaMatriz < altura - KernelDistance; linhaMatriz++) {
                for (int colunaMatriz = KernelDistance; colunaMatriz < largura - KernelDistance; colunaMatriz++) {
//                    Instanciacao nova cada vez que realizar a filtragem em um outro pixel
                    ArrayList<Integer> vetorPixel = new ArrayList<>();
                    for (int sublinha = KernelDistance * -1; sublinha <= KernelDistance; sublinha++) {
                        for (int subcoluna = KernelDistance * -1; subcoluna <= KernelDistance; subcoluna++) {
//                            Percorre o tamanho do kernel definido pelo usuario e adiciona no vetor
                            vetorPixel.add(Matriz[linhaMatriz + sublinha][colunaMatriz + subcoluna]);
                        }
                    }
//                    Utiliza o metodo da propria linguagem para ordenar em ordem crescente
                    vetorPixel.sort(Comparator.naturalOrder());
//                    Pega a mediana do vetor
                    MatrizFinal[linhaMatriz][colunaMatriz] = vetorPixel.get((int) quantidade * quantidade / 2);
                }
            }
        }

        return new PGMImage(MatrizFinal, altura, largura, intensidade, cabecalho, comentario);
    }

    /**
     * Aplica o filtro Laplaciano em uma imagem PGM
     *
     * @param tipo1
     * @param tipo3
     * @param tipo2
     * @param tipo4
     * @param aplicar Caso seja verdadeiro, um objeto PGM com a mascara
     * aplicada, caso contrario retorna um objeto PGM que contem somente a
     * mascara
     * @return
     */
    public PGMImage laplaciano(boolean tipo1, boolean tipo2, boolean tipo3, boolean tipo4, boolean aplicar) {
        Integer[][] mascara = new Integer[altura][largura], matrizMascara = Matriz;
//        Criacao dos tipos de convolucoes exibidos em aula
        Integer[][] matrizConvolucao1 = {{0, 1, 0}, {1, 4, 1}, {0, 1, 0}};
        Integer[][] matrizConvolucao2 = {{1, 1, 1}, {1, 8, 1}, {1, 1, 1}};
        Integer[][] matrizConvolucao3 = {{0, -1, 0}, {-1, 4, -1}, {0, -1, 0}};
        Integer[][] matrizConvolucao4 = {{-1, -1, -1}, {-1, 8, -1}, {-1, -1, -1}};
        for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
                mascara[linhaMatriz][colunaMatriz] = 0;
            }
        }
        if (tipo1) {
//            Percorre a imagem
            for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
                for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
                    int soma = 0;
//                    Percorre o kernel dentro da imagem
                    for (int linhaKernel = 0; linhaKernel < 3; linhaKernel++) {
                        for (int colunaKernel = 0; colunaKernel < 3; colunaKernel++) {
//                            Realiza o calculo da localizacao do kernel na imagem
                            int sublinha = linhaMatriz + linhaKernel - 3 / 2;
                            int subcoluna = colunaMatriz + colunaKernel - 3 / 2;
//                            Aplica a matriz conforme os indices das matrizes do filtro laplaciano sobre a imagem
                            if (sublinha >= 0 && sublinha < altura && subcoluna >= 0 && subcoluna < largura) {
                                soma += Matriz[sublinha][subcoluna] * matrizConvolucao1[linhaKernel][colunaKernel];
                            }
                        }
                    }
//                    Checa se está dentro do limite de bits
                    if (soma > intensidade) {
                        mascara[linhaMatriz][colunaMatriz] = intensidade;
                    } else if (soma < 0) {
                        mascara[linhaMatriz][colunaMatriz] = 0;
                    } else {
                        mascara[linhaMatriz][colunaMatriz] = soma;
                    }
                }
            }
        }
        if (tipo2) {
            for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
                for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
                    int soma = 0;
                    for (int linhaKernel = 0; linhaKernel < 3; linhaKernel++) {
                        for (int colunaKernel = 0; colunaKernel < 3; colunaKernel++) {
                            int sublinha = linhaMatriz + linhaKernel - 3 / 2;
                            int subcoluna = colunaMatriz + colunaKernel - 3 / 2;
                            if (sublinha >= 0 && sublinha < altura && subcoluna >= 0 && subcoluna < largura) {
                                soma += Matriz[sublinha][subcoluna] * matrizConvolucao2[linhaKernel][colunaKernel];
                            }
                        }
                    }
                    if (soma > intensidade) {
                        mascara[linhaMatriz][colunaMatriz] = intensidade;
                    } else if (soma < 0) {
                        mascara[linhaMatriz][colunaMatriz] = 0;
                    } else {
                        mascara[linhaMatriz][colunaMatriz] = soma;
                    }
                }
            }
        }
        if (tipo3) {
            for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
                for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
                    int soma = 0;
                    for (int linhaKernel = 0; linhaKernel < 3; linhaKernel++) {
                        for (int colunaKernel = 0; colunaKernel < 3; colunaKernel++) {
                            int sublinha = linhaMatriz + linhaKernel - 3 / 2;
                            int subcoluna = colunaMatriz + colunaKernel - 3 / 2;
                            if (sublinha >= 0 && sublinha < altura && subcoluna >= 0 && subcoluna < largura) {
                                soma += Matriz[sublinha][subcoluna] * matrizConvolucao3[linhaKernel][colunaKernel];
                            }
                        }
                    }
                    if (soma > intensidade) {
                        mascara[linhaMatriz][colunaMatriz] = intensidade;
                    } else if (soma < 0) {
                        mascara[linhaMatriz][colunaMatriz] = 0;
                    } else {
                        mascara[linhaMatriz][colunaMatriz] = soma;
                    }
                }
            }
        }
        if (tipo4) {
            for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
                for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
                    int soma = 0;
                    for (int linhaKernal = 0; linhaKernal < 3; linhaKernal++) {
                        for (int colunaKernel = 0; colunaKernel < 3; colunaKernel++) {
                            int sublinha = linhaMatriz + linhaKernal - 3 / 2;
                            int subcoluna = colunaMatriz + colunaKernel - 3 / 2;
                            if (sublinha >= 0 && sublinha < altura && subcoluna >= 0 && subcoluna < largura) {
                                soma += Matriz[sublinha][subcoluna] * matrizConvolucao4[linhaKernal][colunaKernel];
                            }
                        }
                    }
                    if (soma > intensidade) {
                        mascara[linhaMatriz][colunaMatriz] = intensidade;
                    } else if (soma < 0) {
                        mascara[linhaMatriz][colunaMatriz] = 0;
                    } else {
                        mascara[linhaMatriz][colunaMatriz] = soma;
                    }
                }
            }
        }
        if (aplicar) {
            for (int linhaMatriz = 0; linhaMatriz < altura; linhaMatriz++) {
                for (int colunaMatriz = 0; colunaMatriz < largura; colunaMatriz++) {
                    if (mascara[linhaMatriz][colunaMatriz] + Matriz[linhaMatriz][colunaMatriz] > intensidade) {
                        matrizMascara[linhaMatriz][colunaMatriz] = intensidade;
                    } else {
                        matrizMascara[linhaMatriz][colunaMatriz] = mascara[linhaMatriz][colunaMatriz] + Matriz[linhaMatriz][colunaMatriz];
                    }
                }
            }
            return new PGMImage(matrizMascara, altura, largura, intensidade, cabecalho, comentario);
        }
        return new PGMImage(mascara, altura, largura, intensidade, cabecalho, comentario);
    }

    /**
     * Realiza o processamento de high boost em uma immagem
     *
     * @param kernelMedia
     * @param constante
     * @return
     */
    public PGMImage nitidez(int kernelMedia, double constante) {
        PGMImage imagemMedia = new PGMImage(Matriz, altura, largura, intensidade, cabecalho, comentario).media(kernelMedia);
        Integer[][] media = imagemMedia.getMatriz();
        Integer[][] mascara = new Integer[altura][largura];
        Integer[][] matrizComMascara = new Integer[altura][largura];

        for (int alturaMatriz = 0; alturaMatriz < altura; alturaMatriz++) {
            for (int larguraMatriz = 0; larguraMatriz < largura; larguraMatriz++) {
                mascara[alturaMatriz][larguraMatriz] = Matriz[alturaMatriz][larguraMatriz] - media[alturaMatriz][larguraMatriz];
                matrizComMascara[alturaMatriz][larguraMatriz] = (int) (Matriz[alturaMatriz][larguraMatriz] + mascara[alturaMatriz][larguraMatriz] * constante);
            }
        }
        return new PGMImage(matrizComMascara, altura, largura, intensidade, cabecalho, comentario);
    }

    /**
     * Retorna as informacoes de uma imagem
     *
     * @return Uma string com todas as informacoes de uma imagem
     */
    public String getInformcao() {
        return """
            Altura: %d
            Largura: %d
            Quantidade de bits: %d
            Cabeçalho: %s
            Comentário: %s
               """.formatted(altura, largura, intensidade, cabecalho, comentario);
    }
}
