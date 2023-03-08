
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Willian Murayama
 */
public class FileHandling {

    static void CreateWriteFile(String filepath, ArrayList<Integer> Matriz) {
        try {
            File myObj = new File(filepath);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try {
            FileWriter escritor = new FileWriter(filepath);
            Iterator MatrizIterator = Matriz.iterator();
            while (MatrizIterator.hasNext()) {
                String teste = MatrizIterator.next().toString();
                System.out.println(teste);
                escritor.write(teste+"\n");
            }
            escritor.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
