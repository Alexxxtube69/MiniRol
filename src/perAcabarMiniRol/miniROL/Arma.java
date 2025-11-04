package perAcabarMiniRol.miniROL;

import java.util.Random;

public class Arma {

    private static int dauArma;
    private final static int dauAtac = 10;
    private String descripcio;
    private int raca;

    public static int getDauArma() {
        return dauArma;
    }

    public void setDauAtac(int dauAtac) {
        this.dauArma = dauAtac;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public int getRaca() {
        return raca;
    }

    public void setRaca(int raca) {
        this.raca = raca;
    }

    public static int getDauAtac() {
        return dauAtac;
    }

}
