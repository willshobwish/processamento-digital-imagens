package Classes;

import java.io.IOException;
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
//        PPMImage lena = new PPMImage("src\\Assets\\ppm\\lenna.ppm");
//        lena.saveImage("src\\Assets\\ppm\\lenappm.ppm");
//        lena.saveImageChannels("src\\Assets\\ppm\\lenappm");
//        lena.Clarear(0, 0, 100).saveImage("src\\Assets\\ppm\\clarearB.ppm");
//        lena.Escurecer(100, 0, 0).saveImage("src\\Assets\\ppm\\escurecerR.ppm");
        PGMImage lenaAscii = new PGMImage("src\\Assets\\originais\\lena256.pgm");

        PGMImage lenaBinary = new PGMImage("src\\Assets\\originais\\lenabinary.pgm");
        lenaBinary.saveImage("src\\Assets\\todos\\lenabinarypgm.pgm");
        lenaAscii.saveImage("src\\Assets\\todos\\lenaascii.pgm");

        //        lena.InvertChannels("r", "g", "b").saveImage("src\\Assets\\ppm\\RGB.ppm");
        //        lena.InvertChannels("r", "b", "g").saveImage("src\\Assets\\ppm\\RBG.ppm");
        //        lena.InvertChannels("g", "r", "b").saveImage("src\\Assets\\ppm\\GRB.ppm");
        //        lena.InvertChannels("g", "b", "r").saveImage("src\\Assets\\ppm\\GBR.ppm");
        //        lena.InvertChannels("b", "r", "g").saveImage("src\\Assets\\ppm\\BRG.ppm");
        //        lena.InvertChannels("b", "g", "r").saveImage("src\\Assets\\ppm\\BGR.ppm");
    }

}
