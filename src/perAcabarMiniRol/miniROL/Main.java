package perAcabarMiniRol.miniROL;

public class Main {

    public static void main(String[] args) {

        Personatge heroi = new Personatge("Marc", 5, 3, 30);

        FinestraPrincipal joc = new FinestraPrincipal(heroi);

        joc.StartJoc();

    }
}
