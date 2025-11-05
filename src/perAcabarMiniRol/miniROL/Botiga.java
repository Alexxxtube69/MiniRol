package perAcabarMiniRol.miniROL;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Botiga {

    private JDialog marc;

    private JPanel panellPrinicpal, panellSuperior, panellInferior, panellBotiga;
    private JPanel panellEspasa, panellEscut, panellPocio, panellMapa;

    private ArrayList<Arma> armesDisponibles;

    private JLabel imatgeEspasa, imatgeEscut, imatgePocio, imatgeMapa;
    private JLabel desEspasa, desEscut, desPocio, desMapa;
    private static JButton botEspasa, botEscut, botPocio, botMapa;
    private static boolean agoEspasa = false, agoEscut = false, agoPocio = false, agoMapa = false;

    private JButton botSortir;

    private FinestraPrincipal fp;
    private Personatge pj;

    public Botiga(FinestraPrincipal fp){

        this.fp = fp;

        pj = fp.getPj();

        marc =  new JDialog();

        armesDisponibles = CarregadorArmes.carregarArmes("src/perAcabarMiniRol/miniROL/armes.txt");

        panellPrinicpal = new JPanel(new BorderLayout());
        panellSuperior = fp.getPanellSuperior();
        panellInferior = new JPanel();
        panellBotiga = new JPanel(new GridLayout(2,2));

        panellEspasa = new JPanel();
        panellEscut = new JPanel();
        panellPocio = new JPanel();
        panellMapa = new JPanel();

        imatgeEspasa = new JLabel(new ImageIcon("./imatges/espasa.png"));
        imatgeEscut = new JLabel(new ImageIcon("./imatges/escut.png"));
        imatgePocio = new JLabel(new ImageIcon("./imatges/pocio.png"));
        imatgeMapa = new JLabel(new ImageIcon("./imatges/mapa.png"));

        desEspasa = new JLabel("Espasa - 100 or.");
        desEscut = new JLabel("Escut - 100 or.");
        desPocio = new JLabel("Pocio - 50 or.");
        desMapa = new JLabel("Mapa - 10 or.");

        botEspasa = new JButton("Comprar");
        botEscut = new JButton("Comprar");
        botPocio = new JButton("Comprar");
        botMapa = new JButton("Comprar");

        botSortir = new JButton("Sortir");

    }

    public void obrirBotiga(){
        montarInterficie();
        marc.setVisible(true);
    }

    private void montarInterficie() {

        //Panell superior llest.
        panellPrinicpal.add(panellSuperior, BorderLayout.NORTH);

        //Preparem zona central
        addObjecte(panellEspasa, imatgeEspasa, desEspasa, botEspasa, "espasa", agoEspasa);
        addObjecte(panellEscut, imatgeEscut, desEscut, botEscut, "escut", agoEscut);
        addObjecte(panellPocio, imatgePocio, desPocio, botPocio, "pocio", agoPocio);
        addObjecte(panellMapa, imatgeMapa, desMapa, botMapa, "mapa", agoMapa);

        panellPrinicpal.add(panellBotiga, BorderLayout.CENTER);

        JPanel panellArmes = new JPanel();
        panellArmes.setLayout(new BoxLayout(panellArmes, BoxLayout.Y_AXIS));

        for (Arma a : armesDisponibles) {

            JButton boto = new JButton("Comprar " + a.getNom() + " (d" + a.getDauArma() + ") - " + a.getPreu() + " or");

            boto.addActionListener(e -> comprarArma(a));

            panellArmes.add(boto);
        }

        panellPrinicpal.add(panellArmes, BorderLayout.WEST);


        //Preparar panell inferior
        botSortir.addActionListener(e -> marc.dispose());
        panellInferior.add(botSortir);
        panellPrinicpal.add(panellInferior, BorderLayout.SOUTH);

        marc.setSize(700,600);
        marc.setLocationRelativeTo(null);
        marc.setModal(true);
        marc.add(panellPrinicpal);

    }

    private void comprarArma(Arma arma) {

        int preu = arma.getPreu(); // preu senzill

        if (pj.getOr() < preu) {
            JOptionPane.showMessageDialog(marc, "No tens prou or!");
            return;
        }

        pj.setOr(pj.getOr() - preu);
        fp.getEtOr().setText(" Or: " + pj.getOr());

        pj.afegirArma(arma);

        JOptionPane.showMessageDialog(marc,
                "Has comprat " + arma.getNom() + "\nAra està al teu inventari");

    }

    private void addObjecte(JPanel panObjecte, JLabel imatge, JLabel descripcio, JButton boto, String nom, boolean agotat) {

        imatge.setAlignmentX(Component.CENTER_ALIGNMENT);
        descripcio.setAlignmentX(Component.CENTER_ALIGNMENT);
        boto.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (agotat){
            boto.setEnabled(false);
        }
        boto.addActionListener(e -> comprarObjecte(boto,nom));

        panObjecte.setLayout(new BoxLayout(panObjecte, BoxLayout.Y_AXIS));
        panObjecte.add(imatge);
        panObjecte.add(descripcio);
        panObjecte.add(boto);

        panellBotiga.add(panObjecte);

    }

    private void comprarObjecte(JButton boto, String nom) {
        switch (nom){
            case "espasa":
                if (pj.getOr()>=100){
                    pj.setAtac(pj.getAtac() + 3);
                    fp.getEtAtributs().setText(" Atc:" + pj.getAtac()+ "| Def: "+ pj.getDefensa());
                    pj.setOr(pj.getOr() - 100);
                    fp.getEtOr().setText(" Or: "+ pj.getOr());
                    boto.setEnabled(false);
                    agoEspasa = true;
                }
                break;
            case "escut":
                if (pj.getOr()>=100){
                    pj.setDefensa(pj.getDefensa() + 3);
                    fp.getEtAtributs().setText(" Atc:" + pj.getAtac()+ "| Def: "+ pj.getDefensa());
                    pj.setOr(pj.getOr() - 100);
                    fp.getEtOr().setText(" Or: "+ pj.getOr());
                    boto.setEnabled(false);
                    agoEscut = true;
                }
                break;
            case "pocio":
                if (pj.getOr()>=50){
                    pj.setVidaActual((int)pj.getVidaMax());
                    pj.establirVida(pj.getVidaActual());
                    pj.setOr(pj.getOr() - 50);
                    fp.getEtOr().setText(" Or: "+ pj.getOr());
                    boto.setEnabled(false);
                    agoPocio = true;
                }
                break;
            case "mapa":
                if (pj.getOr()>=10){
                    Exploracio.setNumExploracio(250);
                    pj.setOr(pj.getOr() - 10);
                    fp.getEtOr().setText(" Or: "+ pj.getOr());
                    boto.setEnabled(false);
                    agoMapa = true;
                }
                break;
        }

    }

}
