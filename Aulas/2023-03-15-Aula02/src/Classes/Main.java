package Classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
/**
 *
 * @author Willian Murayama
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Matriz matriz = new Matriz("src/Assets/lena256irfam.pgm");
        Matriz skull = new Matriz("src/Assets/ctskull-256.pgm");
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
//        skull.Rotacao90().ImprimeArquivo("src/Assets/skull90.pgm");
//        skull.Rotacao180().ImprimeArquivo("src/Assets/skull180.pgm");
        skull.Teste().ImprimeArquivo("src/Assets/SkullTeste.pgm");
        skull.ClarearAdicao(0).ImprimeArquivo("src/Assets/adicaoskull.pgm");

    }

}
