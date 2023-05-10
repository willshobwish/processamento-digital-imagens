package Classes;

import java.util.ArrayList;

/**
 *
 * @author Willian
 */
public class Main {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        PGMImage matriz = new PGMImage("src/Assets/lena256irfam.pgm");
        PGMImage skull = new PGMImage("src/Assets/ctskull-256.pgm");
        PGMImage aerial = new PGMImage("src/Assets/aerial.pgm");
        PGMImage spine = new PGMImage("src/Assets/spine.pgm");
        PGMImage h1 = new PGMImage("src\\Assets\\equalizacao_histograma\\phistf1.pgm");
        PGMImage h2 = new PGMImage("src\\Assets\\equalizacao_histograma\\phistf2.pgm");
        PGMImage h3 = new PGMImage("src\\Assets\\equalizacao_histograma\\phistf3.pgm");
        PGMImage h4 = new PGMImage("src\\Assets\\equalizacao_histograma\\phistf4.pgm");

//        matriz.ImprimeArquivo("src/Assets/teste.pgm");
        //        matriz.ClarearAdicao(50).ImprimeArquivo("src/Assets/TesteClareadoAdicao.pgm");
        //        matriz.ClarearMultiplicao(2).ImprimeArquivo("src/Assets/TesteClareadoMultiplicacao.pgm");
        //        matriz.RotacaoMenos90().ImprimeArquivo("src/Assets/RotacaoMenos90.pgm");
        //        matriz.EspelhamentoHorizontal().ImprimeArquivo("src/Assets/EspelhamentoHorizontal.pgm");
        //        matriz.Rotacao90().ImprimeArquivo("src/Assets/Rotacao90.pgm");
        //        matriz.Rotacao180().ImprimeArquivo("src/Assets/Rotacao180.pgm");
        //        matriz.EspelhamentoVertical().ImprimeArquivo("src/Assets/EspelhamentoVertical.pgm");
        //        matriz.ReducaoNivel(16).ImprimeArquivo("src/Assets/ReducaoNivel16.pgm");
        //        matriz.ReducaoNivel(8).ImprimeArquivo("src/Assets/ReducaoNivel8.pgm");
        //        matriz.ReducaoNivel(4).ImprimeArquivo("src/Assets/ReducaoNivel4.pgm");
        //        matriz.ReducaoNivel(2).ImprimeArquivo("src/Assets/ReducaoNivel2.pgm");
        //        skull.Binarizacao(100).ImprimeArquivo("src/Assets/Binarizacao100.pgm");
        //        skull.Binarizacao(150).ImprimeArquivo("src/Assets/Binarizacao150.pgm");
        //        skull.EspelhamentoHorizontal().ImprimeArquivo("src/Assets/espelhado.pgm");
        //
        //        skull.Rotacao90().ImprimeArquivo("src/Assets/skull90.pgm");
        //        skull.Rotacao180().ImprimeArquivo("src/Assets/skull180.pgm");
        //        skull.Teste().ImprimeArquivo("src/Assets/SkullTeste.pgm");
        //        skull.ClarearAdicao(0).ImprimeArquivo("src/Assets/adicaoskull.pgm");
        //        skull.BinaryRange(50, 70, 255, 10).ImprimeArquivo("src/Assets/skullbinaryrange.pgm");
        //        skull.RangeHighlight(50, 70, 255).ImprimeArquivo("src/Assets/rangehighlight.pgm");
        //        skull.Subtract(2, 2).ImprimeArquivo("src/Assets/substract.pgm");
        //        ArrayList<Double> valores = new ArrayList<>();
        //        valores.add(0.04);
        //        valores.add(0.1);
        //        valores.add(0.2);
        //        valores.add(0.4);
        //        valores.add(0.67);
        //        valores.add(1.0);
        //        valores.add(1.5);
        //        valores.add(2.5);
        //        valores.add(5.0);
        //        valores.add(100.);
        //        valores.add(25.0);
        //        for (Double eachValor : valores) {
        //            skull.Estica(1, eachValor).ImprimeArquivo("src/Assets/substract" + eachValor + ".pgm");
        //            aerial.Estica(1, eachValor).ImprimeArquivo("src/Assets/aerial" + eachValor + ".pgm");
        //            spine.Estica(1, eachValor).ImprimeArquivo("src/Assets/spine" + eachValor + ".pgm");
        //
        //        }
        matriz.aumentaResolucao(2).saveImage("src/Assets/lenaaumentada.pgm");
        matriz.diminuiResolucao(2).saveImage("src/Assets/lenadiminuida.pgm");
//        matriz.histogram("src/Assets/histograma.csv");
        h1.equalizacao_histograma().saveImage("src/Assets/correto1.pgm");
        h2.equalizacao_histograma().saveImage("src/Assets/correto2.pgm");
        h3.equalizacao_histograma().saveImage("src/Assets/correto3.pgm");
        h4.equalizacao_histograma().saveImage("src/Assets/correto4.pgm");

    }

}
