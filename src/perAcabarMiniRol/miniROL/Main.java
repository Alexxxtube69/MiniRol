package perAcabarMiniRol.miniROL;

public class Main {

    public static void main(String[] args) {

        Personatge heroi = new Personatge("Marc", 5, 3, 30);
        heroi.setArmaEquipada(new Arma("Espasa","Una espasa bàsica amb poc de mal",6));

        FinestraPrincipal joc = new FinestraPrincipal(heroi);

        joc.StartJoc();

    }
}