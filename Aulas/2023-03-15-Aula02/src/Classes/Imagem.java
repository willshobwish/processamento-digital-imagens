package Classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Math.pow;
import java.util.Scanner;

public class Imagem {

    protected Integer[][] Matriz;
    protected int coluna;
    protected int linha;
    protected int intensidade;
    protected String cabecalho;
    protected String comentario;

    public Imagem(Integer[][] Matriz, int coluna, int linha, int intensidade, String cabecalho, String comentario) {
        this.Matriz = Matriz;
        this.coluna = coluna;
        this.linha = linha;
        this.intensidade = intensidade;
        this.cabecalho = cabecalho;
        this.comentario = comentario;
    }

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

    public Imagem Negative() {
        Integer ImagemNegativa[][] = new Integer[coluna][linha];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                ImagemNegativa[colunaMatriz][linhaMatriz] = intensidade - Matriz[colunaMatriz][linhaMatriz];
            }
        }
        return new Imagem(ImagemNegativa, coluna, linha, intensidade, cabecalho, comentario);
    }

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

    public Imagem RotacaoMenos90() {
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

    public Imagem Rotacao90() {
//Quantidade de coluna e linha inversa
        Integer ImagemRotacionada[][] = new Integer[linha][coluna];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                ImagemRotacionada[linhaMatriz][coluna - 1 - colunaMatriz] = Matriz[colunaMatriz][linhaMatriz];
            }
        }
        return new Imagem(ImagemRotacionada, linha, coluna, intensidade, cabecalho, comentario);
    }

    public Imagem Rotacao180() {
        Integer ImagemRotacionada[][] = new Integer[coluna][linha];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                ImagemRotacionada[coluna - 1 - colunaMatriz][linha - 1 - linhaMatriz] = Matriz[colunaMatriz][linhaMatriz];

            }
        }
        return new Imagem(ImagemRotacionada, coluna, linha, intensidade, cabecalho, comentario);
    }

    public Imagem Teste() {
        Integer ImagemTeste[][] = new Integer[coluna][linha];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                ImagemTeste[colunaMatriz][linhaMatriz] = Matriz[colunaMatriz][linhaMatriz];
            }
        }
        return new Imagem(ImagemTeste, coluna, linha, intensidade, cabecalho, comentario);
    }

    public void Comentario(String funcao, int colunaModificada, int linhaModificada) {
        System.out.println("""
                           %s
                           Coluna e linha original: %d x %d
                           Coluna e linha modificado: %d x %d
                           """.formatted(funcao, coluna, linha, colunaModificada, linhaModificada));
    }

    public Imagem EspelhamentoHorizontal() {
        Integer Espelhada[][] = new Integer[coluna][linha];
        Comentario("Espelhamento horizontal", coluna, linha);
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                Espelhada[colunaMatriz][linha - 1 - linhaMatriz] = Matriz[colunaMatriz][linhaMatriz];
            }
        }
        return new Imagem(Espelhada, coluna, linha, intensidade, cabecalho, comentario);
    }

    public Imagem EspelhamentoVertical() {
        Integer Espelhada[][] = new Integer[coluna][linha];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                Espelhada[coluna - 1 - colunaMatriz][linhaMatriz] = Matriz[colunaMatriz][linhaMatriz];
            }
        }
        return new Imagem(Espelhada, coluna, linha, intensidade, cabecalho, comentario);
    }

    public Imagem ReducaoNivel(int quantidadeDeNiveis) {
        Integer Reduzida[][] = new Integer[coluna][linha];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                Reduzida[colunaMatriz][linhaMatriz] = (int) Math.ceil(Matriz[colunaMatriz][linhaMatriz] * (quantidadeDeNiveis - 1) / intensidade);
            }
        }
        return new Imagem(Reduzida, coluna, linha, quantidadeDeNiveis, cabecalho, comentario);
    }

    public Imagem Binarizacao(int limiar) {
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

    public Imagem BinaryRange(int start, int end, int level, int greyLevel) {
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

    public Imagem RangeHighlight(int start, int end, int quantidade) {
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

    public Imagem Subtract(int c, int a) {
        Double MatrizPontoFlutuante[][] = new Double[coluna][linha];
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
    
        public Imagem Estica(int c, double beta) {
        Double MatrizPontoFlutuante[][] = new Double[coluna][linha];
        Integer MatrizCorreta[][] = new Integer[coluna][linha];
        for (int colunaMatriz = 0; colunaMatriz < coluna; colunaMatriz++) {
            for (int linhaMatriz = 0; linhaMatriz < linha; linhaMatriz++) {
                double resultadoDivisao = (double) Matriz[colunaMatriz][linhaMatriz] / intensidade, resultadoPotencia = pow(resultadoDivisao, beta), resultadoMultiplicacao = resultadoPotencia * c;
                int resultadoArredondamento = (int) (resultadoMultiplicacao * intensidade);
                if (resultadoArredondamento > intensidade) {
                    MatrizCorreta[colunaMatriz][linhaMatriz] = intensidade ;
                } else {
                    MatrizCorreta[colunaMatriz][linhaMatriz] = resultadoArredondamento ;
                }
            }
        }
        return new Imagem(MatrizCorreta, coluna, linha, intensidade, cabecalho, comentario);
    }
}
