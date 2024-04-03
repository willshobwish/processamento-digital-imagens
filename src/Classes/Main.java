package Classes;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Willian Murayama
 */
public class Main {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Autoria do projeto de Willian Murayama");
        PGMImage lenaAscii = new PGMImage("src\\Assets\\originais\\lena256.pgm");
        PGMImage quadrados = new PGMImage("src\\Assets\\originais\\quadrados.pgm");
        PGMImage placa = new PGMImage("src\\Assets\\originais\\placa_circuito.pgm");
        PGMImage texto = new PGMImage("src\\Assets\\originais\\dipxe.pgm");
        PGMImage moon = new PGMImage("src\\Assets\\originais\\blurry_moon.pgm");
        placa.Mediana(3).saveImage("src\\Assets\\todos\\medianaaaaaaaaaaaaa.pgm");
        moon.laplaciano().saveImage("src\\Assets\\todos\\laplaciado.pgm");
        lenaAscii.laplaciano().saveImage("src\\Assets\\todos\\lenana.pgm");
        lenaAscii.rotate90().saveImage("src\\Assets\\todos\\lenana2.pgm");
        moon.media(3).saveImage("src\\Assets\\todos\\moonmedia.pgm");
        texto.nitidez(3, 4.5).saveImage("src\\Assets\\todos\\nitidez.pgm");
    }
}
