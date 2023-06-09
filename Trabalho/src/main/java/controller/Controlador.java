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
        mainwindow.atualiza(pgmimage.getInformation());
    }

    /**
     *
     * @param filepath
     */
    public void salvarImagemPGM(String filepath) {
        fileclosefilepath = filepath;
        pgmimage.saveImage(filepath + ".pgm");
    }

//    Imagem PPM
    /**
     *
     * @param filepath
     */
    public void abrirImagemPPM(String filepath) {
        ppmimage = new PPMImage(filepath);
        filepathPPM = filepath;
        mainwindow.atualiza(ppmimage.getInformation());
    }

    /**
     *
     * @param filepath
     */
    public void salvarImagemPPM(String filepath) {
        fileclosefilepath = filepath;
        ppmimage.saveImage(filepath + ".ppm");
    }

    /**
     *
     * @param quantidade
     */
    public void media(int quantidade) {
        pgmimage = pgmimage.media(quantidade);
    }

    /**
     *
     * @param quantidade
     */
    public void mediana(int quantidade) {
        pgmimage = pgmimage.Mediana(quantidade);
    }

    /**
     *
     */
    public void laplaciano() {

    }

    /**
     *
     */
    public void highBoost() {

    }

    /**
     *
     */
    public void equalizacaoGlobal() {

    }

//    public void getExtracaoRGBFilepath(String filepath) {
//        ppmimga
//    }
    public void dialogExtracaoRGB() {
        new SavePPMChannelsFile().setVisible(true);
    }

    /**
     *
     *
     */
    public void extracaoRGB(String fileapath) {
        ppmimage.saveImageChannels(fileapath, mainwindow.getRadioExtracaoRed().isSelected(), mainwindow.getRadioExtracaoGreen().isSelected(), mainwindow.getRadioExtracaoBlue().isSelected(), mainwindow.getRadioExtracaoTodos().isSelected());
    }

    /**
     *
     */
    public void juncaoRGB(String r, String g, String b) {
        ppmimage.InvertChannels(r, g, b);
    }

    public static Controlador getControlador() {
        return controlador;
    }

    public static void setControlador(Controlador controlador) {
        Controlador.controlador = controlador;
    }

    public static PGMImage getPgmimage() {
        return pgmimage;
    }

    public static void setPgmimage(PGMImage pgmimage) {
        Controlador.pgmimage = pgmimage;
    }

    public PPMImage getPpmimage() {
        return ppmimage;
    }

    public void setPpmimage(PPMImage ppmimage) {
        this.ppmimage = ppmimage;
    }

    public MainWindow getMainwindow() {
        return mainwindow;
    }

    public void setMainwindow(MainWindow mainwindow) {
        this.mainwindow = mainwindow;
    }

    public String getFilepathPPM() {
        return filepathPPM;
    }

    public void setFilepathPPM(String filepathPPM) {
        this.filepathPPM = filepathPPM;
    }

    public String getFilepathPGM() {
        return filepathPGM;
    }

    public void setFilepathPGM(String filepathPGM) {
        this.filepathPGM = filepathPGM;
    }

    public String getFileclosefilepath() {
        return fileclosefilepath;
    }

    public void setFileclosefilepath(String fileclosefilepath) {
        this.fileclosefilepath = fileclosefilepath;
    }

}
