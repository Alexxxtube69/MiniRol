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

        if (!(this instanceof Personatge)){
            enemic.rebreFerida(atac);
            return;
        }

        Personatge pj = (Personatge) this;
        Arma arma = pj.getArmaEquipada();

        // Tirada d20
        int d20 = tirarDau20();
        pj.ultimaTirada = d20;

        // PÍFIA
        if (d20 == 1) {
            pj.ultimDany = 0;
            return;
        }

        // Tirada del dau de l'arma
        int base;

        if (arma != null) {
            base = arma.tirarDauArma();
        } else {
            base = 1;  // si no té arma, fa 1 de dany
        }

        // CRÍTIC
        if (d20 == 20) base *= 2;

        // Defensa
        Entitat e = (Entitat) enemic;
        int danyFinal = base + pj.getAtac() - e.getDefensa();
        if (danyFinal <= 0) danyFinal = 1;

        pj.ultimDany = danyFinal;

        // Aplicar dany real
        enemic.rebreFerida(danyFinal);

    }

    public JProgressBar getBarraVida() {
        return barraVida;
    }

    @Override
    public void rebreFerida(int quantitat) {
        if (estaViu) {
            vidaActual-=quantitat;
            if (vidaActual<=0) {
                estaViu = false;
                vidaActual = 0;
            }
        }

    }

    public int tirarDau20(){
        Random rnd = new Random();
        return rnd.nextInt(20) + 1;
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


