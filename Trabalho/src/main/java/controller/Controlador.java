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
     * @return retorna a instancia do controlador da aplicacao
     */
    public static Controlador getInstance() {
        return controlador;
    }

//    Metodos de abertura e salvamento de imagens
//    Imagem PGM
    /**
     * Metodo para abertura de imagens do tipo PGM
     *
     * @param filepath O caminho do arquivo onde deve ser aberto
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
     * Metodo de escrita de imagens do tipo PGM
     *
     * @param filepath O caminho do arquivo onde deve ser salvo
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
     * Abertura de imagens PPM
     *
     * @param filepath O caminho do arquivo onde deve ser aberto
     */
    public void abrirImagemPPM(String filepath) {
        ppmimage = new PPMImage(filepath);
        filepathPPM = filepath;
        mainwindow.atualiza(ppmimage.getInformacao());
        mainwindow.getTextOperacoes().setText("Abertura de imagem PPM");
    }

    /**
     * Escrita de imagens PPM
     *
     * @param filepath O caminho do arquivo onde deve ser salvo
     */
    public void salvarImagemPPM(String filepath) {
        fileclosefilepath = filepath;
        ppmimage.salvarImagem(filepath + ".ppm");
        mainwindow.getTextOperacoes().setText("""
               Imagem PPM salvo em %s
               """.formatted(filepath + ".ppm"));
    }

    /**
     * Calcula a media de uma imagem PGM
     *
     * @param quantidade O tamanho do filtro (tamanho do kernel)
     * @return Uma string que contem a descricao da operacao realizada
     */
    public String media(int quantidade) {
        pgmimage = pgmimage.media(quantidade);
        return """
               Filtro de média com kernel de %d foi aplicado na imagem
               """.formatted(quantidade);
    }

    /**
     * Calcula a mediana de uma imagem PGM
     *
     * @param quantidade O tamanho do filtro (tamanho do kernel)
     * @return Uma string que contem a descricao da operacao realizada
     */
    public String mediana(int quantidade) {
        pgmimage = pgmimage.mediana(quantidade);
        return """
               Filtro de mediana com kernel de %d foi aplicado na imagem
               """.formatted(quantidade);
    }

    /**
     * Aplica o filtro Laplaciano, caso o aplicar seja selecionado na interface,
     * aplica-se a mascara na imagem, caso contrario retorna a mascara
     *
     * @param tipo1 {{0, 1, 0}, {1, 4, 1}, {0, 1, 0}}
     * @param tipo2 {{1, 1, 1}, {1, 8, 1}, {1, 1, 1}}
     * @param tipo3 {{0, -1, 0}, {-1, 4, -1}, {0, -1, 0}}
     * @param tipo4 {{-1, -1, -1}, {-1, 8, -1}, {-1, -1, -1}}
     * @param aplicar caso seja verdadeiro, aplica a mascara gerado pelo filtro
     * na imagem, caso contrario retorna a mascara
     * @return Uma string que contem a descricao da operacao realizada
     */
    public String laplaciano(boolean tipo1, boolean tipo2, boolean tipo3, boolean tipo4, boolean aplicar) {
        pgmimage = pgmimage.laplaciano(tipo1, tipo2, tipo3, tipo4, aplicar);
        return """
               Fitro Laplaciano aplicado na imagem
               """;
    }

    /**
     * Aplica nitidez na imagem
     *
     * @param kernelMedia tamanho do filtro da media (tamanho do kernel da
     * media)
     * @param constante constante que eh multiplicado antes de aplicar a mascara
     * @return Uma string que contem a descricao da operacao realizada
     */
    public String highBoost(int kernelMedia, double constante) {
        pgmimage = pgmimage.nitidez(kernelMedia, constante);
        return """
               Filtro high boost com média %d e constante %.2f foi aplicado na imagem
               """.formatted(kernelMedia, constante);
    }

    /**
     * Aplica a equalizacao de histograma global
     *
     * @return Uma string que contem a descricao da operacao realizada
     */
    public String equalizacaoGlobal() {
        pgmimage = pgmimage.equalizacao_histograma();
        return """
               Equalização global do histograma foi aplicado na imagem
               """;
    }

    /**
     * Abertura de uma janela que permite que salve cada canal de uma imagem PPM
     * em PGM (RGB em PGM)
     */
    public void dialogExtracaoRGB() {
        new SavePPMChannelsFile().setVisible(true);
    }

    /**
     * Realiza a operacao de extracao de cores dos canais de uma imagem PPM,
     * caso a seja selecionado para salvar, ele chama o metodo para abrir uma
     * outra janela, caso seja selecionado para utilizar o canal especifico em
     * memoria, sera alocado para a variavel PGM e podera ser realizado os
     * processamentos em PGM
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
     * Modificacao de canais de cores de imagens PPM para a entrada que o
     * usuario definir
     *
     * @param r Define qual a cor que o canal Red do PPM deve assumir
     * @param g Define qual a qual cor que o canal Green do PPM deve assumir
     * @param b Define qual a qual cor que o canal Blue do PPM deve assumir
     * @return Uma string que contem a descricao da operacao realizada
     */
    public String juncaoRGB(String r, String g, String b) {
        ppmimage = ppmimage.inverterCanais(r, g, b);
        return """
               Junção de canais de imagem PPM aplicado
               """;
    }

    /**
     * Rotaciona a imagem PGM em 90 graus
     *
     * @return Uma string que contem a descricao da operacao realizada
     */
    public String rotacao90() {
        pgmimage = pgmimage.rotacao90();
        return """
               Rotação em 90º da imagem
               """;
    }

    /**
     * Rotaciona a imagem PGM em 180 graus
     *
     * @return Uma string que contem a descricao da operacao realizada
     */
    public String rotacao180() {
        pgmimage = pgmimage.rotacao180();
        return """
               Rotação em 180º da imagem
               """;
    }

    /**
     * Rotaciona a imagem em menos 90 graus
     *
     * @return Uma string que contem a descricao da operacao realizada
     */
    public String rotacaoMenos90() {
        pgmimage = pgmimage.rotacaoMenos90();
        return """
               Rotação em -90º da imagem
               """;
    }

    /**
     * Espelhamento vertical da imagem PGM
     *
     * @return Uma string que contem a descricao da operacao realizada
     */
    public String espelhamentoVertical() {
        pgmimage = pgmimage.espelhamentoVertical();
        return """
                   Espelhamento vertical aplicado na imagem
                   """;
    }

    /**
     * Espelhamento horizontal da imagem PGM
     *
     * @return Uma string que contem a descricao da operacao realizada
     */
    public String espelhamentoHorizontal() {
        pgmimage = pgmimage.espelhamentoHorizontal();
        return """
               Espelhamento vertical aplicado na imagem
               """;
    }

//    Getters e setters
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
