package perAcabarMiniRol.miniROL;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Random;


public class Entitat implements IAtacable, Serializable {

    private String nom;
    private int vidaActual, atac, defensa, agilitat;
    private double vidaMax;
    private boolean estaViu;
    private JProgressBar barraVida;

    public Entitat(String nom, int atac, int defensa, double vidaMax) {
        this.nom = nom;
        this.atac = atac;
        this.defensa = defensa;
        this.vidaMax = vidaMax;
        vidaActual = (int) vidaMax;

        estaViu = true;
        barraVida = new JProgressBar(0,(int) vidaMax);
        barraVida.setPreferredSize(new Dimension(150,25));

        establirVida(vidaActual);
    }

    public void establirVida(int vida) {

        barraVida.setValue(vida);
        barraVida.setForeground( Color.RED);
        barraVida.setStringPainted(true);
        barraVida.setString(vidaActual + "/" + (int) vidaMax);

    }

    @Override
    public void atacar(IAtacable enemic) {

        enemic.rebreFerida(atac);

    }

    public JProgressBar getBarraVida() {
        return barraVida;
    }

    @Override
    public void rebreFerida(int quantitat) {
        if (estaViu) {
            int quantitatTotal = quantitat - defensa;
            if (quantitatTotal <=0) quantitatTotal = 1;
            vidaActual-=quantitatTotal;
            if (vidaActual<=0) {
                estaViu = false;
                vidaActual = 0;
            }
        }

        /*if (estaViu){
            Random rnd = new Random();
            int dau = rnd.nextInt(20) + 1;
            int quantitatTotal;

            if (dau == 20){
                quantitatTotal = (quantitat * 2) - defensa;
                System.out.println("Cop critic! El dau ha tret 20!");
            }else if (dau == 1){
                quantitatTotal = 0;
                System.out.println("L'atac falla completament (dau = 1)");
            }else {
                quantitatTotal = Arma.getTirada(Arma.getDauArma());
            }
        }*/

    }


    public int getDefensa() {
        return defensa;
    }

    public boolean isEstaViu() {
        return estaViu;
    }

    public int getVidaActual() {
        return vidaActual;
    }

    public double getVidaMax() {
        return vidaMax;
    }

    public int getAtac() {
        return atac;
    }

    public void setAtac(int atac) {
        this.atac = atac;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public void setEstaViu(boolean estaViu) {
        this.estaViu = estaViu;
    }

    public void setVidaActual(int vidaActual) {
        this.vidaActual = vidaActual;
    }

    public String getNom() {
        return nom;
    }

    public void setVidaMax(double vidaMax) {
        this.vidaMax = vidaMax;
    }

    public int getAgilitat() {
        return agilitat;
    }

    public void setAgilitat(int agilitat) {
        this.agilitat = agilitat;
    }
}


