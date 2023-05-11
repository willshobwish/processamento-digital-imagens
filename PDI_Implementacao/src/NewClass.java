
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Willian Murayama
 */
public class NewClass {

    public static void main(String[] args) {
        try {
            // Open the binary PGM file for reading
            FileInputStream in = new FileInputStream("src\\Assets\\todos\\lena256.pgm");

            // Read the header information
            String magicNumber = readLine(in);
            System.out.println(magicNumber);
            String commentary = readLine(in);
            System.out.println(commentary);
            String[] width_height = readLine(in).split(" ");
            int width = Integer.parseInt(width_height[0]);
            int height = Integer.parseInt(width_height[1]);
            int maxGrayValue = readInt(in);

            // Create a new FileWriter to write the ASCII PGM file
            FileWriter out = new FileWriter("src\\Assets\\todos\\output.pgm");

            // Write the ASCII PGM header information to the new file
            out.write("P2\n");
            out.write(width + " " + height + "\n");
            out.write(maxGrayValue + "\n");

            // Read the pixel data from the binary file and write to the ASCII file
            for (int i = 0; i < width * height; i++) {
                int grayValue = in.read() & 0xFF; // convert byte to unsigned int
                out.write(grayValue + "\n");
            }

            // Close the input and output streams
            in.close();
            out.close();

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Read a line of text from a FileInputStream
    private static String readLine(FileInputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        int c;
        while ((c = in.read()) != -1 && c != '\n') {
            sb.append((char) c);
        }
        return sb.toString();
    }

    // Read an integer value from a FileInputStream
    private static int readInt(FileInputStream in) throws IOException {
        String line = readLine(in);
        return Integer.parseInt(line.trim());
    }

}
