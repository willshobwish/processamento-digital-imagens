/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.PGMImage;
import view.MainWindow;
import view.SaveFile;

/**
 *
 * @author Willian
 */
public class Controlador {

    private static Controlador controlador = new Controlador();
    private static PGMImage pgmimage;
    private MainWindow mainwindow = new MainWindow();
    private String fileopenfilepath;
    private String fileclosefilepath;

    private Controlador() {
        mainwindow.setVisible(true);
    }

    public static Controlador getInstance() {
        return controlador;
    }

    public void abrirImagem(String filepath) {
        pgmimage = new PGMImage(filepath);
        fileopenfilepath = filepath;
        mainwindow.atualiza(pgmimage.getInformation());
    }

    public void salvarImagem(String filepath) {
        fileclosefilepath = filepath;
        pgmimage.saveImage(filepath + ".pgm");
    }

    public void media(int quantidade) {
        pgmimage = pgmimage.media(quantidade);
    }
}
