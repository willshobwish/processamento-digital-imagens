package Classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Math.pow;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Willian
 */
public class Imagem {

    /**
     *
     */
    protected Integer[][] Matriz;

    /**
     *
     */
    protected int coluna;

    /**
     *
     */
    protected int linha;

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
     * @param coluna
     * @param linha
     * @param intensidade
     * @param cabecalho
     * @param comentario
     */
    public Imagem(Integer[][] Matriz, int coluna, int linha, int intensidade, String cabecalho, String comentario) {
        this.Matriz = Matriz;
        this.coluna = coluna;
        this.linha = linha;
        this.intensidade = intensidade;
        this.cabecalho = cabecalho;
        this.comentario = comentario;
    }

    /**
     *
     * @param filepath
     */
    public Imagem(String filepath) {
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
            this.Matriz = new Integer[this.coluna][this.linha];
            System.out.println("""
                           Nome do arquivo: %s
                           Coluna: %d
                           Linha: %d
                           Cabecalho: %s
                           Comentario: %s
                           """.formatted(ArquivoObjeto.getName(), coluna, linha, cabecalho, comentario));
            //Armazenamento do conteudo da imagem
            while (Leitor.hasNext()) {
                for (int colunaVetor = 0; colunaVetor < coluna; colunaVetor++) {
                    for (int linhaVetor = 0; linhaVetor < linha; linhaVetor++) {
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
                                    """.formatted(Matriz[colunaMatriz][linhaMatriz]));
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
    public Imagem Negative() {
        Integer ImagemNegativa[][] = new Integer[coluna][linha];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                ImagemNegativa[colunaMatriz][linhaMatriz] = intensidade - Matriz[colunaMatriz][linhaMatriz];
            }
        }
        return new Imagem(ImagemNegativa, coluna, linha, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @param quantidade
     * @return
     */
    public Imagem Darken(int quantidade) {
        Integer ImagemEscurecida[][] = new Integer[coluna][linha];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                if (Matriz[colunaMatriz][linhaMatriz] - quantidade < 0) {
                    ImagemEscurecida[colunaMatriz][linhaMatriz] = 0;
                } else {
                    ImagemEscurecida[colunaMatriz][linhaMatriz] = Matriz[colunaMatriz][linhaMatriz] - quantidade;
                }
            }
        }
        return new Imagem(ImagemEscurecida, coluna, linha, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @param Quantidade
     * @return
     */
    public Imagem ClarearAdicao(int Quantidade) {
        Integer ImagemClareada[][] = new Integer[coluna][linha];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {

                if (Matriz[colunaMatriz][linhaMatriz] + Quantidade > intensidade) {
                    ImagemClareada[colunaMatriz][linhaMatriz] = intensidade;
                } else {
                    ImagemClareada[colunaMatriz][linhaMatriz] = Matriz[colunaMatriz][linhaMatriz] + Quantidade;
                }

            }
        }
        return new Imagem(ImagemClareada, coluna, linha, intensidade, cabecalho, comentario);

    }

    /**
     *
     * @param Quantidade
     * @return
     */
    public Imagem ClarearMultiplicao(float Quantidade) {
        Integer ImagemClareada[][] = new Integer[coluna][linha];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                float temp = Matriz[colunaMatriz][linhaMatriz] * Quantidade;
                if (temp > intensidade) {
                    temp = intensidade;
                }
                ImagemClareada[colunaMatriz][linhaMatriz] = (int) temp;
            }
        }
        return new Imagem(ImagemClareada, coluna, linha, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @return
     */
    public Imagem rotateMinus90() {
        Integer ImagemRotacionada[][] = new Integer[linha][coluna];
        Comentario("Rotacao -90", linha, coluna);
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                System.out.println("Coluna Linha: %d x %d = %d".formatted(linhaMatriz, colunaMatriz, Matriz[colunaMatriz][linhaMatriz]));
                ImagemRotacionada[linha - 1 - linhaMatriz][colunaMatriz] = Matriz[colunaMatriz][linhaMatriz];
            }
        }
        return new Imagem(ImagemRotacionada, linha, coluna, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @return
     */
    public Imagem rotate90() {
//Quantidade de coluna e linha inversa
        Integer ImagemRotacionada[][] = new Integer[linha][coluna];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                ImagemRotacionada[linhaMatriz][coluna - 1 - colunaMatriz] = Matriz[colunaMatriz][linhaMatriz];
            }
        }
        return new Imagem(ImagemRotacionada, linha, coluna, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @return
     */
    public Imagem rotate180() {
        Integer ImagemRotacionada[][] = new Integer[coluna][linha];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                ImagemRotacionada[coluna - 1 - colunaMatriz][linha - 1 - linhaMatriz] = Matriz[colunaMatriz][linhaMatriz];
            }
        }
        return new Imagem(ImagemRotacionada, coluna, linha, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @return
     */
    public Imagem test() {
        Integer ImagemTeste[][] = new Integer[coluna][linha];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                ImagemTeste[colunaMatriz][linhaMatriz] = Matriz[colunaMatriz][linhaMatriz];
            }
        }
        return new Imagem(ImagemTeste, coluna, linha, intensidade, cabecalho, comentario);
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
                           Coluna e linha original: %d x %d
                           Coluna e linha modificado: %d x %d
                           """.formatted(funcao, coluna, linha, colunaModificada, linhaModificada));
    }

    /**
     *
     * @return
     */
    public Imagem espelhamentoHorizontal() {
        Integer Espelhada[][] = new Integer[coluna][linha];
        Comentario("Espelhamento horizontal", coluna, linha);
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                Espelhada[colunaMatriz][linha - 1 - linhaMatriz] = Matriz[colunaMatriz][linhaMatriz];
            }
        }
        return new Imagem(Espelhada, coluna, linha, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @return
     */
    public Imagem espelhamentoVertical() {
        Integer Espelhada[][] = new Integer[coluna][linha];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                Espelhada[coluna - 1 - colunaMatriz][linhaMatriz] = Matriz[colunaMatriz][linhaMatriz];
            }
        }
        return new Imagem(Espelhada, coluna, linha, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @param quantidadeDeNiveis
     * @return
     */
    public Imagem reducaoNivel(int quantidadeDeNiveis) {
        Integer Reduzida[][] = new Integer[coluna][linha];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                Reduzida[colunaMatriz][linhaMatriz] = (int) Math.ceil(Matriz[colunaMatriz][linhaMatriz] * (quantidadeDeNiveis - 1) / intensidade);
            }
        }
        return new Imagem(Reduzida, coluna, linha, quantidadeDeNiveis, cabecalho, comentario);
    }

    /**
     *
     * @param limiar
     * @return
     */
    public Imagem binarizacao(int limiar) {
        Integer Reduzida[][] = new Integer[coluna][linha];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                if (Matriz[colunaMatriz][linhaMatriz] <= limiar) {
                    Reduzida[colunaMatriz][linhaMatriz] = intensidade;
                } else {
                    Reduzida[colunaMatriz][linhaMatriz] = 0;
                }
            }
        }
        return new Imagem(Reduzida, coluna, linha, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @param start
     * @param end
     * @param level
     * @param greyLevel
     * @return
     */
    public Imagem binaryRange(int start, int end, int level, int greyLevel) {
        Integer Reduzida[][] = new Integer[coluna][linha];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                if (Matriz[colunaMatriz][linhaMatriz] > start && Matriz[colunaMatriz][linhaMatriz] < end) {
                    Reduzida[colunaMatriz][linhaMatriz] = level;
                } else {
                    Reduzida[colunaMatriz][linhaMatriz] = greyLevel;
                }
            }
        }
        return new Imagem(Reduzida, coluna, linha, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @param start
     * @param end
     * @param quantidade
     * @return
     */
    public Imagem rangeHighlight(int start, int end, int quantidade) {
        Integer Reduzida[][] = new Integer[coluna][linha];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                if (Matriz[colunaMatriz][linhaMatriz] > start && Matriz[colunaMatriz][linhaMatriz] < end) {
                    Reduzida[colunaMatriz][linhaMatriz] = quantidade;
                } else {
                    Reduzida[colunaMatriz][linhaMatriz] = Matriz[colunaMatriz][linhaMatriz];
                }
            }
        }
        return new Imagem(Reduzida, coluna, linha, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @param c
     * @param a
     * @return
     */
    public Imagem subtract(int c, int a) {
        Integer MatrizCorreta[][] = new Integer[coluna][linha];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                double resultadoDivisao = (double) Matriz[colunaMatriz][linhaMatriz] / intensidade, resultadoPotencia = pow(resultadoDivisao, a), resultadoMultiplicacao = resultadoPotencia * c;
                int resultadoArredondamento = (int) (resultadoMultiplicacao * intensidade);
                if (resultadoArredondamento > intensidade) {
                    MatrizCorreta[colunaMatriz][linhaMatriz] = intensidade - Matriz[colunaMatriz][linhaMatriz];
                } else {
                    MatrizCorreta[colunaMatriz][linhaMatriz] = resultadoArredondamento - Matriz[colunaMatriz][linhaMatriz];
                }
            }
        }
        return new Imagem(MatrizCorreta, coluna, linha, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @param c
     * @param beta
     * @return
     */
    public Imagem transformacaoPotencia(int c, double beta) {
        Integer MatrizCorreta[][] = new Integer[coluna][linha];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                double resultadoDivisao = (double) Matriz[colunaMatriz][linhaMatriz] / intensidade, resultadoPotencia = pow(resultadoDivisao, beta), resultadoMultiplicacao = resultadoPotencia * c;
                int resultadoArredondamento = (int) (resultadoMultiplicacao * intensidade);
                if (resultadoArredondamento > intensidade) {
                    MatrizCorreta[colunaMatriz][linhaMatriz] = intensidade;
                } else {
                    MatrizCorreta[colunaMatriz][linhaMatriz] = resultadoArredondamento;
                }
            }
        }
        return new Imagem(MatrizCorreta, coluna, linha, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @param quantidadeAumento
     * @return
     */
    public Imagem aumentaResolucao(int quantidadeAumento) {
        int colunaAumentada = coluna * quantidadeAumento, linhaAumentada = linha * quantidadeAumento;
        System.out.println("""
                           Quantidade de linhas: %d
                           Quantidade de colunas: %d
                           Quantidade de linhas: %d
                           Quantidade de colunas: %d
                           """.formatted(linhaAumentada, colunaAumentada, linha, coluna));
        Integer matrizAumentada[][] = new Integer[colunaAumentada][linhaAumentada];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                for (int quantidadeColuna = 0; quantidadeColuna < quantidadeAumento; quantidadeColuna++) {
                    for (int quantidadeLinha = 0; quantidadeLinha < quantidadeAumento; quantidadeLinha++) {
                        matrizAumentada[colunaMatriz * quantidadeAumento + quantidadeColuna][linhaMatriz * quantidadeAumento + quantidadeLinha] = Matriz[colunaMatriz][linhaMatriz];
                    }
                }
            }
        }
        return new Imagem(matrizAumentada, colunaAumentada, linhaAumentada, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @param quantidadeReducao
     * @return
     */
    public Imagem diminuiResolucao(int quantidadeReducao) {
        int colunaReduzida = coluna / quantidadeReducao, linhaReduzida = linha / quantidadeReducao, somaPixeis = 0;
        System.out.println("""
                           Quantidade de linhas: %d
                           Quantidade de colunas: %d
                           Quantidade de linhas: %d
                           Quantidade de colunas: %d
                           """.formatted(linhaReduzida, colunaReduzida, linha, coluna));
        Integer[][] matrizReduzida = new Integer[colunaReduzida][linhaReduzida];
        for (int colunaMatriz = 0; colunaMatriz < colunaReduzida; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linhaReduzida; linhaMatriz++) {
                for (int variavelColuna = 0; variavelColuna < quantidadeReducao; variavelColuna++) {
                    for (int variavelLinha = 0; variavelLinha < quantidadeReducao; variavelLinha++) {
                        somaPixeis += Matriz[colunaMatriz * quantidadeReducao + variavelColuna][linhaMatriz * quantidadeReducao + variavelLinha];
                    }
                }
//                Realiza a média, como sempre é um quadrado, pode ser realizado quntidade^quantidade para a média
                matrizReduzida[colunaMatriz][linhaMatriz] = somaPixeis / (int) pow(quantidadeReducao, quantidadeReducao);
                somaPixeis = 0;
            }
        }
        return new Imagem(matrizReduzida, colunaReduzida, linhaReduzida, intensidade, cabecalho, comentario);
    }

    /**
     *
     * @param filepath
     */
    public void histogram(String filepath) {
        ArrayList<Integer> histograma = new ArrayList<>();
        for (int i = 0; i < intensidade; i++) {
            histograma.add(1);
        }
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                histograma.set(Matriz[colunaMatriz][linhaMatriz], histograma.get(Matriz[colunaMatriz][linhaMatriz]) + 1);
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

    public int[] histogram() {
        int histograma[] = new int[intensidade];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                histograma[Matriz[coluna][linha]] += 1;
            }
        }
        for (int i = 0; i < intensidade; i++) {
            System.out.println("""
                               %d: %d
                               """.formatted(i, histograma[i]));

        }
        return histograma;
    }

    public void equalizacao_histograma() {
        int histograma[] = histogram();

    }
}
