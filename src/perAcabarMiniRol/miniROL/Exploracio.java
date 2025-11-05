package perAcabarMiniRol.miniROL;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Exploracio {

    private JDialog marc;

    private JPanel panellPrincipal, panellSuperior, panellInferior, panellMonstre, panellMonstreSec;

    private JButton botAtacar, botFugir;

    private JTextArea infoExploracio;
    private JScrollPane barraDes;

    private Personatge pj;

    private Monstre enemic;

    private boolean esBoss = false;

    private static int numExploracio = 0;

    private FinestraPrincipal fp; //per poder accedir a les etiquetes

    public Exploracio(FinestraPrincipal fp){

        this.fp = fp;

        pj = fp.getPj();

        marc = new JDialog();

        panellPrincipal = new JPanel(new BorderLayout());
        panellSuperior = fp.getPanellSuperior();
        panellInferior = new JPanel();
        panellMonstre = new JPanel();
        panellMonstreSec = new JPanel();

        infoExploracio = new JTextArea();
        infoExploracio.setEditable(false);


        barraDes = new JScrollPane(infoExploracio);
        barraDes.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        botAtacar = new JButton("Atacar");
        botFugir = new JButton("Fugir");
    }


    public void startExploracio() {
        
        decidirDificultat();
        montarInteficie();
    }

    private void decidirDificultat() {

        int numAlea = (int) (Math.random()*100) + numExploracio;

        numExploracio++;

        //ara aquí instanciarem un objecte de la classe monstre

        enemic = Monstre.generaMontre(numAlea);

        if (enemic.getNom().equals("Boss")){
            esBoss = true;
        }
    }

    private void montarInteficie() {

        //panell superior llest

        //afegir l'area de text al panell princpi

        panellPrincipal.add(barraDes, BorderLayout.CENTER);

        //afegir tot el del mosntre

        panellMonstreSec.add(enemic.getEtNom());
        panellMonstreSec.add(enemic.getBarraVida());

        panellMonstre.setLayout(new BoxLayout(panellMonstre, BoxLayout.Y_AXIS));
        panellMonstre.add(enemic.getImatge());
        panellMonstre.add(panellMonstreSec);


        //fem panell inferior
        botAtacar.addActionListener(e->atacar());
        botFugir.addActionListener(e->intentarFugir());

        panellInferior.add(botAtacar);
        panellInferior.add(new JLabel("          "));
        if (esBoss){
            botFugir.setEnabled(false);
        }
        panellInferior.add(botFugir);
        panellPrincipal.add(panellSuperior, BorderLayout.NORTH);
        panellPrincipal.add(panellInferior, BorderLayout.SOUTH);

        panellPrincipal.add(panellMonstre,BorderLayout.EAST);

        marc.add(panellPrincipal);
        marc.setSize(750,500);
        marc.setLocationRelativeTo(null);
        marc.setModal(true);

        marc.setVisible(true);


    }

    private void intentarFugir() {

        if (esBoss) {
            infoExploracio.append("No pots fugir d'un BOSS!\n\n");
            return;
        }

        // Tirada jugador
        int tiradaJugador = pj.tirarDau20() + pj.getAgilitat();

        // Tirada enemic
        int tiradaMonstre = enemic.tirarDau20() + enemic.getAgilitat();

        infoExploracio.append("Intent de fugida!\n");
        infoExploracio.append("Tirada jugador (d20 + agilitat): " + tiradaJugador + "\n");
        infoExploracio.append("Tirada monstre (d20 + agilitat): " + tiradaMonstre + "\n");

        // RESULTAT
        if (tiradaJugador >= tiradaMonstre) {
            infoExploracio.append("Fugint...\n");

            // Espera 1,5 segons abans de tancar (1500 ms)
            new javax.swing.Timer(2500, e -> marc.dispose()).start();
        } else {
            infoExploracio.append("No aconsegueixes fugir! L'enemic t'ataca!\n\n");

            // el monstre ataca una vegada
            enemic.atacar(pj);

            int damage = Math.max(1, enemic.getAtac() - pj.getDefensa());

            infoExploracio.append(enemic.getNom() + " et fa " + damage + " de mal.\n\n");

            pj.establirVida(pj.getVidaActual());

            if (!pj.isEstaViu()) {
                derrota();
            }
        }

    }

    private void atacar(){

        int damage;
        int danyFet = 0;
        int danyRebut = 0;

        // --- ATACA EL JUGADOR (TOT EL CÀLCUL ES FA A Entitat) ---
        pj.atacar(enemic);

        infoExploracio.append(
                pj.getNom() + " tira un d20 i obté: " + pj.ultimaTirada + "\n"
        );

        if (pj.ultimaTirada == 1) {
            infoExploracio.append("PÍFIA! L'atac falla!\n\n");
        } else {
            infoExploracio.append("Dany causat: " + pj.ultimDany + "\n\n");
        }

        danyFet = pj.ultimDany;

        enemic.establirVida(enemic.getVidaActual());

        if (!enemic.isEstaViu()) {
            enemicDerrotat();

        } else {
            enemic.atacar(pj); //a l'interficie
            infoExploracio.setText(infoExploracio.getText() + enemic.getNom()
                    + " ataca amb una força de " + enemic.getAtac() + ".\n");
            damage = enemic.getAtac() - pj.getDefensa();
            if (damage <= 0) damage = 1;
            danyRebut = damage;

            infoExploracio.setText(infoExploracio.getText() + pj.getNom()
                    + " ha rebut " + damage + " de mal gràcies a la seva defensa.\n\n" );
            pj.establirVida(pj.getVidaActual()); //canvia la barra de vida

            if (!pj.isEstaViu()){
                derrota();
            }
        }

        Integer[] a = new Integer[2];
        if (!pj.getEstadistiquesDany().containsKey(enemic.getNom())){
            a[0] = danyFet;
            a[1] = danyRebut;
        }else {
            a[0] = pj.getEstadistiquesDany().get(enemic.getNom())[0] + danyFet;
            a[1] = pj.getEstadistiquesDany().get(enemic.getNom())[1] + danyRebut;
        }
        pj.getEstadistiquesDany().put(enemic.getNom(),a);

    }

    private void derrota() {

        FinestraFinal f = new FinestraFinal(FinestraFinal.DERROTA,pj);
        f.obrir();
    }

    private void enemicDerrotat() {

        botAtacar.setEnabled(false);
        botFugir.setText("Sortir");

        infoExploracio.setText(infoExploracio.getText() + enemic.getNom() + " ha sigut derrotat \n"
                    + "Has obtingut " + enemic.getPremiOr() + " or.\n"
                    + "Guanyes " + enemic.getPremiXp() + " punts d'experiència.");

        pj.pujarXp(enemic.getPremiOr());
        fp.getEtXp().setText(" Xp:"+ pj.getXp() + "/" + pj.getXpNecessaria());
        fp.getEtNivell().setText(" Lvl:" + pj.getNivell());
        fp.getEtAtributs().setText(" Atc:" + pj.getAtac()+ "| Def: "+ pj.getDefensa());

        pj.setOr(pj.getOr() + enemic.getPremiOr());
        fp.getEtOr().setText(" Or: "+ pj.getOr());

        if (esBoss){
            FinestraFinal f = new FinestraFinal(FinestraFinal.VICTORIA,pj);
            f.obrir();
        }

        if (!pj.getMonstresEliminats().containsKey(enemic.getNom())){
            pj.getMonstresEliminats().put(enemic.getNom(),1);
        }else {
            pj.getMonstresEliminats().put(enemic.getNom(), pj.getMonstresEliminats().get(enemic.getNom()) + 1);
        }

    }


    public static int getNumExploracio() {
        return numExploracio;
    }

    public static void setNumExploracio(int numExploracio) {
        Exploracio.numExploracio = numExploracio;
    }

    public void guardarPersonatge(){

        try (ObjectOutputStream ous = new ObjectOutputStream(new FileOutputStream("personatge.dat"))){
            ous.writeObject(pj);
            ous.close();

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void carregarPersonatge(){

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("personatge.dat"))){


        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
