package Classes;

public class Main {

    public static void main(String[] args) {

        Imagem matriz = new Imagem("src/Assets/lena256irfam.pgm");
        Imagem skull = new Imagem("src/Assets/ctskull-256.pgm");
//        matriz.ImprimeArquivo("src/Assets/teste.pgm");
//        matriz.ClarearAdicao(50).ImprimeArquivo("src/Assets/TesteClareadoAdicao.pgm");
//        matriz.ClarearMultiplicao(2).ImprimeArquivo("src/Assets/TesteClareadoMultiplicacao.pgm");
//        matriz.RotacaoMenos90().ImprimeArquivo("src/Assets/RotacaoMenos90.pgm");
//        matriz.EspelhamentoHorizontal().ImprimeArquivo("src/Assets/EspelhamentoHorizontal.pgm");
        matriz.Rotacao90().ImprimeArquivo("src/Assets/Rotacao90.pgm");
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
        skull.Rotacao90().ImprimeArquivo("src/Assets/skull90.pgm");
        skull.Rotacao180().ImprimeArquivo("src/Assets/skull180.pgm");
        skull.Teste().ImprimeArquivo("src/Assets/SkullTeste.pgm");
        skull.ClarearAdicao(0).ImprimeArquivo("src/Assets/adicaoskull.pgm");

    }

}
