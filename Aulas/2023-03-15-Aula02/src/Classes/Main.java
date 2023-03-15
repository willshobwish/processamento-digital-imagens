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
        Matriz matriz = new Matriz("src/Assets/lena256.pgm");
        matriz.ImprimeArquivo("src/Assets/teste.pgm");
        matriz.ClarearAdicao(50, 255).ImprimeArquivo("src/Assets/TesteClareadoAdicao.pgm");
        matriz.ClarearMultiplicao(2, 255).ImprimeArquivo("src/Assets/TesteClareadoMultiplicacao.pgm");
        matriz.RotacionarMenos90().ImprimeArquivo("src/Assets/RotacaoMenos90.pgm");
        matriz.Rotacionar180().ImprimeArquivo("src/Assets/Rotacao180.pgm");
        matriz.Rotacionar90().ImprimeArquivo("src/Assets/Rotacao90.pgm");

    }

}
