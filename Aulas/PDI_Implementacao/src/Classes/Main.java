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
        PPMImage lena = new PPMImage("src\\Assets\\ppm\\lenna.ppm");
        lena.saveImage("src\\Assets\\ppm\\lenappm.ppm");
        lena.saveImageChannels("src\\Assets\\ppm\\lenappm");
        lena.Clarear(0, 0, 100).saveImage("src\\Assets\\ppm\\clarearB.ppm");
        lena.Escurecer(100, 0, 0).saveImage("src\\Assets\\ppm\\escurecerR.ppm");
        lena.InvertChannels("b", "g", "r").saveImage("src\\Assets\\ppm\\Lena.ppm");
    }

}
