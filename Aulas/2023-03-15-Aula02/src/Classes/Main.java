package Classes;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        Imagem matriz = new Imagem("src/Assets/lena256irfam.pgm");
        Imagem skull = new Imagem("src/Assets/ctskull-256.pgm");
        Imagem aerial = new Imagem("src/Assets/aerial.pgm");
        Imagem spine = new Imagem("src/Assets/spine.pgm");

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
        matriz.aumenta(2).ImprimeArquivo("src/Assets/lenaaumentada.pgm");
        matriz.diminui(2).ImprimeArquivo("src/Assets/lenadiminuida.pgm");

    }

}
