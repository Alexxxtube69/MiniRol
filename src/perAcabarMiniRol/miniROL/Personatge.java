package perAcabarMiniRol.miniROL;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Personatge extends Entitat implements Serializable {

    private int nivell, or, xp, xpNecessaria;
    private String[] armes;
    private HashMap<String,Integer> monstresEliminats;
    private HashMap<String, Integer[]> estadistiquesDany;

    public int ultimaTirada = 0;
    public int ultimDany = 0;
    private Arma armaEquipada;

    private ArrayList<Arma> inventariArmes;

    public Personatge(String nom, int atac, int defensa, double vidaMax) {
        super(nom, atac, defensa, vidaMax);
        nivell = 1;
        or = 0;
        xp = 0;
        xpNecessaria = 10;
        armes = new String[4];

        monstresEliminats = new HashMap<>();
        estadistiquesDany = new HashMap<>();
    }

    public String[] getArmes() {
        return armes;
    }

    public void setArmes(String[] armes) {
        this.armes = armes;
    }

    public int getNivell() {
        return nivell;
    }

    public void pujarNivell() {
        nivell++;
        setAtac(getAtac() + 2);
        setDefensa(getDefensa() + 1);
        setVidaMax(getVidaMax() * 1.1);
        getBarraVida().setMaximum((int) getVidaMax());
        setVidaActual((int) getVidaMax());
        establirVida((int) getVidaMax());
        xpNecessaria += (xpNecessaria + 5);
    }


    public int getXp() {
        return xp;
    }

    public void pujarXp(int quantitat) {
        xp+=quantitat;
        if (xp>=xpNecessaria) pujarNivell();
    }

    public int getXpNecessaria() {
        return xpNecessaria;
    }

    public int getOr() {
        return or;
    }

    public void setOr(int or) {
        this.or = or;
    }

    public HashMap<String, Integer> getMonstresEliminats() {
        return monstresEliminats;
    }

    public HashMap<String, Integer[]> getEstadistiquesDany() {
        return estadistiquesDany;
    }

    public Arma getArmaEquipada() {
        return armaEquipada;
    }

    public void setArmaEquipada(Arma a) {
        armaEquipada = a;
    }

}
