package Classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Math.pow;
import java.util.ArrayList;
import java.util.Collections;
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
    protected int linha;

    /**
     *
     */
    protected int coluna;

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
     * @param linha
     * @param coluna
     * @param intensidade
     * @param cabecalho
     * @param comentario
     */
    public PGMImage(Integer[][] Matriz, int linha, int coluna, int intensidade, String cabecalho, String comentario) {
        this.Matriz = Matriz;
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
    public PGMImage(String filepath) {
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
            this.linha = Integer.parseInt(ColunaLinhaArquivo[0]);
            this.coluna = Integer.parseInt(ColunaLinhaArquivo[1]);
            this.Matriz = new Integer[this.linha][this.coluna];
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
                        Matriz[colunaVetor][linhaVetor] = Integer.valueOf(Leitor.next());
                    }
                }
            }
            Leitor.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ocorreu um erro no fechamento do arquivo");
            e.printStackTrace();
        }
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
                                    %d
                                    """.formatted(Matriz[linhaMatriz][colunaMatriz]));
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
     * @return
     */
    public PGMImage Negative() {
        Integer ImagemNegativa[][] = new Integer[linha][coluna];
        for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
                ImagemNegativa[linhaMatriz][colunaMatriz] = intensidade - Matriz[linhaMatriz][colunaMatriz];
            }
        }
        return new PGMImage(ImagemNegativa, linha, coluna, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @param quantidade
     * @return
     */
    public PGMImage Darken(int quantidade) {
        Integer ImagemEscurecida[][] = new Integer[linha][coluna];
        for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
                if (Matriz[linhaMatriz][colunaMatriz] - quantidade < 0) {
                    ImagemEscurecida[linhaMatriz][colunaMatriz] = 0;
                } else {
                    ImagemEscurecida[linhaMatriz][colunaMatriz] = Matriz[linhaMatriz][colunaMatriz] - quantidade;
                }
            }
        }
        return new PGMImage(ImagemEscurecida, linha, coluna, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @param Quantidade
     * @return
     */
    public PGMImage ClarearAdicao(int Quantidade) {
        Integer ImagemClareada[][] = new Integer[linha][coluna];
        for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {

                if (Matriz[linhaMatriz][colunaMatriz] + Quantidade > intensidade) {
                    ImagemClareada[linhaMatriz][colunaMatriz] = intensidade;
                } else {
                    ImagemClareada[linhaMatriz][colunaMatriz] = Matriz[linhaMatriz][colunaMatriz] + Quantidade;
                }

            }
        }
        return new PGMImage(ImagemClareada, linha, coluna, intensidade, cabecalho, comentario);

    }

    /**
     *
     * @param Quantidade
     * @return
     */
    public PGMImage ClarearMultiplicao(float Quantidade) {
        Integer ImagemClareada[][] = new Integer[linha][coluna];
        for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
                float temp = Matriz[linhaMatriz][colunaMatriz] * Quantidade;
                if (temp > intensidade) {
                    temp = intensidade;
                }
                ImagemClareada[linhaMatriz][colunaMatriz] = (int) temp;
            }
        }
        return new PGMImage(ImagemClareada, linha, coluna, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @return
     */
    public PGMImage rotateMinus90() {
        Integer ImagemRotacionada[][] = new Integer[coluna][linha];
        Comentario("Rotacao -90", coluna, linha);
        for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
                System.out.println("linha coluna: %d x %d = %d".formatted(colunaMatriz, linhaMatriz, Matriz[linhaMatriz][colunaMatriz]));
                ImagemRotacionada[coluna - 1 - colunaMatriz][linhaMatriz] = Matriz[linhaMatriz][colunaMatriz];
            }
        }
        return new PGMImage(ImagemRotacionada, coluna, linha, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @return
     */
    public PGMImage rotate90() {
//Quantidade de linha e coluna inversa
        Integer ImagemRotacionada[][] = new Integer[coluna][linha];
        for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
                ImagemRotacionada[colunaMatriz][linha - 1 - linhaMatriz] = Matriz[linhaMatriz][colunaMatriz];
            }
        }
        return new PGMImage(ImagemRotacionada, coluna, linha, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @return
     */
    public PGMImage rotate180() {
        Integer ImagemRotacionada[][] = new Integer[linha][coluna];
        for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
                ImagemRotacionada[linha - 1 - linhaMatriz][coluna - 1 - colunaMatriz] = Matriz[linhaMatriz][colunaMatriz];
            }
        }
        return new PGMImage(ImagemRotacionada, linha, coluna, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @return
     */
    public PGMImage test() {
        Integer ImagemTeste[][] = new Integer[linha][coluna];
        for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
                ImagemTeste[linhaMatriz][colunaMatriz] = Matriz[linhaMatriz][colunaMatriz];
            }
        }
        return new PGMImage(ImagemTeste, linha, coluna, intensidade, cabecalho, comentario);
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
                           """.formatted(funcao, linha, coluna, colunaModificada, linhaModificada));
    }

    /**
     *
     * @return
     */
    public PGMImage espelhamentoHorizontal() {
        Integer Espelhada[][] = new Integer[linha][coluna];
        Comentario("Espelhamento horizontal", linha, coluna);
        for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
                Espelhada[linhaMatriz][coluna - 1 - colunaMatriz] = Matriz[linhaMatriz][colunaMatriz];
            }
        }
        return new PGMImage(Espelhada, linha, coluna, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @return
     */
    public PGMImage espelhamentoVertical() {
        Integer Espelhada[][] = new Integer[linha][coluna];
        for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
                Espelhada[linha - 1 - linhaMatriz][colunaMatriz] = Matriz[linhaMatriz][colunaMatriz];
            }
        }
        return new PGMImage(Espelhada, linha, coluna, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @param quantidadeDeNiveis
     * @return
     */
    public PGMImage reducaoNivel(int quantidadeDeNiveis) {
        Integer Reduzida[][] = new Integer[linha][coluna];
        for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
                Reduzida[linhaMatriz][colunaMatriz] = (int) Math.ceil(Matriz[linhaMatriz][colunaMatriz] * (quantidadeDeNiveis - 1) / intensidade);
            }
        }
        return new PGMImage(Reduzida, linha, coluna, quantidadeDeNiveis, cabecalho, comentario);
    }

    /**
     *
     * @param limiar
     * @return
     */
    public PGMImage binarizacao(int limiar) {
        Integer Reduzida[][] = new Integer[linha][coluna];
        for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
                if (Matriz[linhaMatriz][colunaMatriz] <= limiar) {
                    Reduzida[linhaMatriz][colunaMatriz] = intensidade;
                } else {
                    Reduzida[linhaMatriz][colunaMatriz] = 0;
                }
            }
        }
        return new PGMImage(Reduzida, linha, coluna, intensidade, cabecalho, comentario);
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
        Integer Reduzida[][] = new Integer[linha][coluna];
        for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
                if (Matriz[linhaMatriz][colunaMatriz] > start && Matriz[linhaMatriz][colunaMatriz] < end) {
                    Reduzida[linhaMatriz][colunaMatriz] = level;
                } else {
                    Reduzida[linhaMatriz][colunaMatriz] = greyLevel;
                }
            }
        }
        return new PGMImage(Reduzida, linha, coluna, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @param start
     * @param end
     * @param quantidade
     * @return
     */
    public PGMImage rangeHighlight(int start, int end, int quantidade) {
        Integer Reduzida[][] = new Integer[linha][coluna];
        for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
                if (Matriz[linhaMatriz][colunaMatriz] > start && Matriz[linhaMatriz][colunaMatriz] < end) {
                    Reduzida[linhaMatriz][colunaMatriz] = quantidade;
                } else {
                    Reduzida[linhaMatriz][colunaMatriz] = Matriz[linhaMatriz][colunaMatriz];
                }
            }
        }
        return new PGMImage(Reduzida, linha, coluna, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @param c
     * @param a
     * @return
     */
    public PGMImage subtract(int c, int a) {
        Integer MatrizCorreta[][] = new Integer[linha][coluna];
        for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
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
        return new PGMImage(MatrizCorreta, linha, coluna, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @param c
     * @param beta
     * @return
     */
    public PGMImage transformacaoPotencia(int c, double beta) {
        Integer MatrizCorreta[][] = new Integer[linha][coluna];
        for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
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
        return new PGMImage(MatrizCorreta, linha, coluna, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @param quantidadeAumento
     * @return
     */
    public PGMImage aumentaResolucao(int quantidadeAumento) {
        int colunaAumentada = linha * quantidadeAumento, linhaAumentada = coluna * quantidadeAumento;
        Integer matrizAumentada[][] = new Integer[colunaAumentada][linhaAumentada];
        for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
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
        int colunaReduzida = linha / quantidadeReducao, linhaReduzida = coluna / quantidadeReducao, somaPixeis = 0;
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
        for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
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
        for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
                histograma.set(Matriz[linhaMatriz][colunaMatriz], histograma.get(Matriz[linhaMatriz][colunaMatriz]) + 1);
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
            nk.set(i, (double) histograma.get(i) / (linha * coluna));
            somaProbabilidade += nk.get(i);
            soma.set(i, (int) Math.floor(intensidade * somaProbabilidade));
            System.out.println(soma.get(i));
        }
        Integer matrizModificada[][] = new Integer[linha][coluna];
        for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
            for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
                matrizModificada[linhaMatriz][colunaMatriz] = soma.get(Matriz[linhaMatriz][colunaMatriz]);
            }
        }
        return new PGMImage(matrizModificada, linha, coluna, intensidade, cabecalho, comentario);
    }
}
