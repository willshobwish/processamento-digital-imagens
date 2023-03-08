
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
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
//        ArrayList<Integer> matriz1 = MatrizHandling.MatrizReader("src/matriz1.txt");;
//        ArrayList<Integer> matriz2 = MatrizHandling.MatrizReader("src/matriz2.txt");
//        ArrayList<Integer> MatrizSubtracao = new ArrayList<>();
//        ArrayList<Integer> MatrizAdicao = new ArrayList<>();
//
//        Iterator<Integer> iteracao1 = matriz1.iterator();
//        Iterator<Integer> iteracao2 = matriz2.iterator();
//
//        while (iteracao1.hasNext()) {
//            int numero1 = iteracao1.next();
//            int numero2 = iteracao2.next();
//            int ResultadoSubtracao = numero2 - numero1;
//            int ResultadoAdicao = numero1 + numero2;
//            if (ResultadoSubtracao < 0) {
//                ResultadoSubtracao = 0;
//            }
//            if (ResultadoAdicao > 100) {
//                ResultadoAdicao = 100;
//            }
//            MatrizSubtracao.add(ResultadoSubtracao);
//            MatrizAdicao.add(ResultadoAdicao);
//        }
//        FileHandling.CreateWriteFile("src/ResultadoSubtracao.txt", MatrizSubtracao);
//        FileHandling.CreateWriteFile("src/ResultadoAdicao.txt", MatrizAdicao);
        int colunas = 10;
        int linhas = 9;
        Integer Matriz[][] = MatrizHandling.Matriz("src/matriz1.txt");
        System.out.println("Matriz correto:");
        for(int coluna=0;coluna<colunas;coluna++){
            for(int linha=0;linha<linhas;linha++){
                System.out.println(Matriz[coluna][linha]);
            }
        }
    }
}
