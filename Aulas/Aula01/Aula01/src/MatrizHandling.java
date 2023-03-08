
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Willian Murayama
 */
public class MatrizHandling {

    static Integer[][] Matriz(String filepath, int colunas, int linhas) {
        Integer Matriz[][] = new Integer[colunas][linhas];
        try {
            File myObj = new File(filepath);
            Scanner leitor = new Scanner(myObj);
//            Impressao do cabecalho
            System.out.println("""
                   Cabecalho: %s
                   Numero de colunas e linhas: %s
                   Restante do cabecalho: %s
                   """.formatted(leitor.nextLine(), leitor.nextLine(), leitor.nextLine()));
            while (leitor.hasNextLine()) {
                for (int coluna = 0; coluna < colunas; coluna++) {
                    for (int linha = 0; linha < linhas; linha++) {
                        Matriz[coluna][linha] = Integer.parseInt(leitor.nextLine());
                    }
                }
            }
            leitor.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
        return Matriz;
    }

    static Integer[][] Matriz(String filepath) {
        int colunas = 0, linhas = 0;
        try {
            File myObj = new File(filepath);
            Scanner leitor = new Scanner(myObj);
            leitor.nextLine();
            String linhaColunasLinhas[] = leitor.nextLine().split(" ");
            colunas = Integer.parseInt(linhaColunasLinhas[0]);
            linhas = Integer.parseInt(linhaColunasLinhas[1]);
//            Impressao do cabecalho
            System.out.println("""
                   Cabecalho: %s
                   Numero de colunas: %d
                   Numero de linhas: %d
                   """.formatted(leitor.nextLine(), colunas, linhas));
            leitor.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
        Integer Matriz[][] = new Integer[colunas][linhas];
        try {
            File myObj = new File(filepath);
            Scanner leitor = new Scanner(myObj);
            for (int i = 0; i < 3; i++) {
                leitor.nextLine();
            }
            while (leitor.hasNextLine()) {
                for (int coluna = 0; coluna < colunas; coluna++) {
                    for (int linha = 0; linha < linhas; linha++) {
                        Matriz[coluna][linha] = Integer.parseInt(leitor.nextLine());
                    }
                }
            }
            leitor.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
        return Matriz;
    }

    static ArrayList<Integer> MatrizReader(String filepath) {
        ArrayList<Integer> Matriz = new ArrayList<>();
        try {
            File myObj = new File(filepath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                Matriz.add(Integer.parseInt(myReader.nextLine()));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
        return Matriz;
    }
}
