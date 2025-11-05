package perAcabarMiniRol.miniROL;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        Personatge heroi = new Personatge("Marc", 5, 3, 70);
        ArrayList<String> races = new ArrayList<>();
        races.add("huma");
        races.add("elf");
        races.add("nan");
        races.add("orc");

        Arma espasaInicial = new Arma("Espasa","Una espasa bàsica amb poc de mal",6, races,0);

        heroi.setArmaEquipada(espasaInicial);

        FinestraPrincipal joc = new FinestraPrincipal(heroi);

        joc.StartJoc();

    }
}