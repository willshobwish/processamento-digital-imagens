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
        PGMImage quadrados = new PGMImage("src\\Assets\\originais\\quadrados.pgm");
        PGMImage placa = new PGMImage("src\\Assets\\originais\\placa_circuito.pgm");
        PGMImage texto = new PGMImage("src\\Assets\\originais\\dipxe.pgm");
        PGMImage moon = new PGMImage("src\\Assets\\originais\\blurry_moon.pgm");
//PPMImage lenaBinary = new PPMImage("src\\Assets\\originais\\lennabinary.ppm");
        //lenaBinary.saveImage("src\\Assets\\todos\\lenabinarypgm.pgm");
        //lenaAscii.saveImage("src\\Assets\\todos\\lenaascii.pgm");
        //lenaBinary.InvertChannels("r", "g", "b").saveImage("src\\Assets\\ppm\\RGB.ppm");
        //        lena.InvertChannels("r", "b", "g").saveImage("src\\Assets\\ppm\\RBG.ppm");
        //        lena.InvertChannels("g", "r", "b").saveImage("src\\Assets\\ppm\\GRB.ppm");
        //        lena.InvertChannels("g", "b", "r").saveImage("src\\Assets\\ppm\\GBR.ppm");
        //        lena.InvertChannels("b", "r", "g").saveImage("src\\Assets\\ppm\\BRG.ppm");
        //        lena.InvertChannels("b", "g", "r").saveImage("src\\Assets\\ppm\\BGR.ppm");
        //        quadrados.equalizacaoLocalHistograma(3).saveImage("src\\Assets\\todos\\quadrados.pgm");;
        //        lenaAscii.media(3).saveImage("src\\Assets\\todos\\lenamedia.pgm");
        //        placa.test().saveImage("src\\Assets\\todos\\placa.pgm");
        placa.Mediana(3).saveImage("src\\Assets\\todos\\medianaaaaaaaaaaaaa.pgm");
        moon.laplaciano().saveImage("src\\Assets\\todos\\laplaciado.pgm");
        lenaAscii.laplaciano().saveImage("src\\Assets\\todos\\lenana.pgm");
        lenaAscii.rotate90().saveImage("src\\Assets\\todos\\lenana2.pgm");
        moon.media(11).saveImage("src\\Assets\\todos\\moonmedia.pgm");
        texto.nitidez(3, 4.5).saveImage("src\\Assets\\todos\\nitidez.pgm");
        //texto.nitidez(3).saveImage("src\\Assets\\todos\\nitide.pgm");
    }
}
