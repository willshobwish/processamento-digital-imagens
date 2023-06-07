/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.PGMImage;
import model.PPMImage;
import view.MainWindow;

/**
 *
 * @author Willian
 */
public class Controlador {

    private static Controlador controlador = new Controlador();
    private static PGMImage pgmimage;
    private PPMImage ppmimage;
    private MainWindow mainwindow = new MainWindow();
    private String fileopenfilepath;
    private String fileclosefilepath;

    private Controlador() {
        mainwindow.setVisible(true);
    }

    public static Controlador getInstance() {
        return controlador;
    }

    public void abrirImagemPGM(String filepath) {
        pgmimage = new PGMImage(filepath);
        fileopenfilepath = filepath;
        mainwindow.atualiza(pgmimage.getInformation());
    }

    public void salvarImagemPGM(String filepath) {
        fileclosefilepath = filepath;
        pgmimage.saveImage(filepath + ".pgm");
    }

    public void abrirImagemPPM(String filepath) {
        ppmimage = new PPMImage(filepath);
        fileopenfilepath = filepath;
        mainwindow.atualiza(ppmimage.getInformation());
    }

    public void salvarImagemPPM(String filepath) {
        fileclosefilepath = filepath;
        ppmimage.saveImage(filepath + ".ppm");
    }

    public void media(int quantidade) {
        pgmimage = pgmimage.media(quantidade);
    }

    public void mediana(int quantidade) {
        pgmimage = pgmimage.Mediana(quantidade);
    }
}
