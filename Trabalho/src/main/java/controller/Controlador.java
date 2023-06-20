/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.PGMImage;
import model.PPMImage;
import view.MainWindow;
import view.PPM.channels.SavePPMChannelsFile;

/**
 *
 * @author Willian
 */
public class Controlador {

    private static Controlador controlador = new Controlador();
    private static PGMImage pgmimage;
    private PPMImage ppmimage;
    private MainWindow mainwindow = new MainWindow();
    private String filepathPPM;
    private String filepathPGM;

    private String fileclosefilepath;

    private Controlador() {
        mainwindow.setVisible(true);
    }

    /**
     * Padrao Singleton
     *
     * @return
     */
    public static Controlador getInstance() {
        return controlador;
    }

//    Metodos de abertura e salvamento de imagens
//    Imagem PGM
    /**
     *
     * @param filepath
     */
    public void abrirImagemPGM(String filepath) {
        pgmimage = new PGMImage(filepath);
        filepathPGM = filepath;
        mainwindow.atualiza(pgmimage.getInformcao());

        mainwindow.getTextOperacoes().setText("""
                                              Imagem PGM aberta
                                              """);
    }

    /**
     *
     * @param filepath
     */
    public void salvarImagemPGM(String filepath) {
        fileclosefilepath = filepath;
        pgmimage.salvarImagem(filepath + ".pgm");
        mainwindow.getTextOperacoes().setText("""
                                              Imagem PGM salvo em %s
                                              """.formatted(filepath + ".pgm"));

    }

//    Imagem PPM
    /**
     *
     * @param filepath
     */
    public void abrirImagemPPM(String filepath) {
        ppmimage = new PPMImage(filepath);
        filepathPPM = filepath;
        mainwindow.atualiza(ppmimage.getInformacao());
        mainwindow.getTextOperacoes().setText("Abertura de imagem PPM");
    }

    /**
     *
     * @param filepath
     */
    public void salvarImagemPPM(String filepath) {
        fileclosefilepath = filepath;
        ppmimage.salvarImagem(filepath + ".ppm");
        mainwindow.getTextOperacoes().setText("""
               Imagem PPM salvo em %s
               """.formatted(filepath + ".ppm"));
    }

    /**
     *
     * @param quantidade
     */
    public String media(int quantidade) {
        pgmimage = pgmimage.media(quantidade);
        return """
               Filtro de média com kernel de %d foi aplicado na imagem
               """.formatted(quantidade);
    }

    /**
     *
     * @param quantidade
     */
    public String mediana(int quantidade) {
        pgmimage = pgmimage.mediana(quantidade);
        return """
               Filtro de mediana com kernel de %d foi aplicado na imagem
               """.formatted(quantidade);
    }

    /**
     *
     * @param tipo1
     * @param tipo2
     * @param tipo2
     * @param tipo4
     * @param tipo3
     * @param tipo4
     */
    public String laplaciano(boolean tipo1, boolean tipo2, boolean tipo3, boolean tipo4) {
        pgmimage = pgmimage.laplaciano(tipo1, tipo2, tipo3, tipo4);
        return """
               Fitro Laplaciano aplicado na imagem
               """;
    }

    /**
     *
     */
    public String highBoost(int kernelMedia, double constante) {
        pgmimage = pgmimage.nitidez(kernelMedia, constante);
        return """
               Filtro high boost com média %d e constante %.2f foi aplicado na imagem
               """.formatted(kernelMedia, constante);
    }

    /**
     *
     */
    public String equalizacaoGlobal() {
        pgmimage = pgmimage.equalizacao_histograma();
        return """
               Equalização global do histograma foi aplicado na imagem
               """;
    }

//    public void getExtracaoRGBFilepath(String filepath) {
//        ppmimga
//    }
    /**
     *
     */
    public void dialogExtracaoRGB() {
        new SavePPMChannelsFile().setVisible(true);
    }

    /**
     *
     *
     * @param fileapath
     */
    public void extracaoRGB(String fileapath) {
        if (mainwindow.getRadioExtracaoSalvarImagem().isSelected()) {
            ppmimage.salvarCanaisImagem(fileapath, mainwindow.getRadioExtracaoRed().isSelected(), mainwindow.getRadioExtracaoGreen().isSelected(), mainwindow.getRadioExtracaoBlue().isSelected(), mainwindow.getRadioExtracaoTodos().isSelected());
        }
        if (mainwindow.getRadioExtracaoUtilizarEmMemoria().isSelected()) {
            pgmimage = ppmimage.salvarImagemCanais(mainwindow.getRadioExtracaoRed().isSelected(), mainwindow.getRadioExtracaoGreen().isSelected(), mainwindow.getRadioExtracaoBlue().isSelected());
        }
    }

    /**
     *
     * @param r
     * @param g
     * @param b
     */
    public String juncaoRGB(String r, String g, String b) {
        ppmimage = ppmimage.inverterCanais(r, g, b);
        return """
               Junção de canais de imagem PPM aplicado
               """;
    }

    /**
     *
     * @return
     */
    public static Controlador getControlador() {
        return controlador;
    }

    /**
     *
     * @param controlador
     */
    public static void setControlador(Controlador controlador) {
        Controlador.controlador = controlador;
    }

    /**
     *
     * @return
     */
    public static PGMImage getPgmimage() {
        return pgmimage;
    }

    /**
     *
     * @param pgmimage
     */
    public static void setPgmimage(PGMImage pgmimage) {
        Controlador.pgmimage = pgmimage;
    }

    /**
     *
     * @return
     */
    public PPMImage getPpmimage() {
        return ppmimage;
    }

    /**
     *
     * @param ppmimage
     */
    public void setPpmimage(PPMImage ppmimage) {
        this.ppmimage = ppmimage;
    }

    /**
     *
     * @return
     */
    public MainWindow getMainwindow() {
        return mainwindow;
    }

    /**
     *
     * @param mainwindow
     */
    public void setMainwindow(MainWindow mainwindow) {
        this.mainwindow = mainwindow;
    }

    /**
     *
     * @return
     */
    public String getFilepathPPM() {
        return filepathPPM;
    }

    /**
     *
     * @param filepathPPM
     */
    public void setFilepathPPM(String filepathPPM) {
        this.filepathPPM = filepathPPM;
    }

    /**
     *
     * @return
     */
    public String getFilepathPGM() {
        return filepathPGM;
    }

    /**
     *
     * @param filepathPGM
     */
    public void setFilepathPGM(String filepathPGM) {
        this.filepathPGM = filepathPGM;
    }

    /**
     *
     * @return
     */
    public String getFileclosefilepath() {
        return fileclosefilepath;
    }

    /**
     *
     * @param fileclosefilepath
     */
    public void setFileclosefilepath(String fileclosefilepath) {
        this.fileclosefilepath = fileclosefilepath;
    }

}
