package perAcabarMiniRol.miniROL;

import java.util.ArrayList;
import java.util.Random;

public class Arma {

    private String nom;
    private String descripcio;
    private int dauArma;
    private ArrayList<String> races;
    private boolean esTrencada = false;

    public Arma(String nom, String descripcio, int dauArma) {
        this.nom = nom;
        this.descripcio = descripcio;
        this.dauArma = dauArma;
        //this.races = new ArrayList<>(races);
    }

    public String getDescripcio() {
        return descripcio;
    }

    public int getDauArma() {
        return dauArma;
    }

    public String getNom() {
        return nom;
    }

    public boolean racaUtilitzable(String raca) {
        return races.contains(raca.toLowerCase());
    }

    public boolean estaTrencada() {
        return esTrencada;
    }

    public void trencar() {
        esTrencada = true;
    }

    public int tirarDauArma() {
        Random rnd = new Random();
        return rnd.nextInt(dauArma) + 1;
    }

}
