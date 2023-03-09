package Library;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Willian
 */
public class Matriz {

    protected Integer[][] Matriz;
    protected int coluna;
    protected int linha;

    public Matriz(String filepath) {
        Matriz = Handling.Matriz(filepath);
    }

   public void toStringg(){
        System.out.println("""
                           Numero de colunas: %d
                           Numero de linhas: %d
                           """);
    }
    
}
