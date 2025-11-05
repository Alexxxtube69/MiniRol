package perAcabarMiniRol.miniROL;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.List;

import marcPanellPersonalitzat.FramePer;
import perAcabarMiniRol.miniROL.Botiga;

public class FinestraPrincipal {


    private FramePer marc;
    private JPanel panellPrincipal, panellSuperior, panellInferior;

    private JLabel etNom, etNivell, etXp, etOr, etAtributs, etArma;
    private JLabel etImatge;

    private JButton botExplorar, botBotiga, botoTriaClasse;
    private JButton botGuardaInfo, botMostraInfo;

    private Personatge pj;

    public FinestraPrincipal(Personatge pj) {

        this.pj = pj;

        marc = new FramePer(600, 500, "Mini ROL", true);
        panellPrincipal = new JPanel(new BorderLayout());
        panellSuperior = new JPanel();
        panellInferior = new JPanel();

        etNom = new JLabel(pj.getNom() + "       ");
        etNivell = new JLabel(" Lvl:" + pj.getNivell());
        etXp = new JLabel(" Xp:" + pj.getXp() + "/" + pj.getXpNecessaria());
        etOr = new JLabel(" Or: " + pj.getOr());

        etAtributs = new JLabel(" Atc:" + pj.getAtac() + "| Def: " + pj.getDefensa() + "| Agl: " + pj.getAgilitat());
        //etArma = new JLabel("| Arma: " + pj.getArma());
        etImatge = new JLabel();
        botExplorar = new JButton("Explorar");
        botBotiga = new JButton("Botiga");
        botoTriaClasse = new JButton("Tria Classe");
        botGuardaInfo = new JButton("Guardar");
        botMostraInfo = new JButton("Carraga");

    }

    public void StartJoc() {
        muntarEscena();
        marc.setVisible(true);

    }

    private void muntarEscena() {

        //Elaborem panell superior amb dades del personatge

        modificarFonts();

        panellSuperior.add(etNom);
        panellSuperior.add(etNivell);
        panellSuperior.add(etXp);
        panellSuperior.add(etOr);
        panellSuperior.add(etAtributs);
        //panellSuperior.add(etArma);

        //Pepito lvl:1 xp:10/25

        panellSuperior.add(pj.getBarraVida());

        //preparar imatge central

        etImatge.setIcon(new ImageIcon("./imatges/castell.jpg"));
        panellPrincipal.add(etImatge, BorderLayout.CENTER);

        //botó panell inferior
        botExplorar.addActionListener(e -> novaExploracio());
        botBotiga.addActionListener(e -> obrirBotiga());
        botoTriaClasse.addActionListener(e -> triaClasse());
        //botons serializar
        botGuardaInfo.addActionListener(e -> serializaPersonatge(pj));
        botMostraInfo.addActionListener(e -> mostraInfoPersonatge());


        panellInferior.add(botExplorar);
        panellInferior.add(botBotiga);
        panellInferior.add(botoTriaClasse);
        panellInferior.add(botMostraInfo);
        panellInferior.add(botGuardaInfo);

        panellPrincipal.add(panellSuperior, BorderLayout.NORTH);
        panellPrincipal.add(panellInferior, BorderLayout.SOUTH);

        marc.add(panellPrincipal);


    }

    private static void serializaPersonatge(Personatge pj) {
        String nomFitxer = JOptionPane.showInputDialog("Escriu el nom del fitxe: ");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomFitxer))) {
            oos.writeObject(pj);
            JOptionPane.showMessageDialog(null, "sa guardad la informcaio del personatge");
            System.out.println(nomFitxer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //void treura string
    private void mostraInfoPersonatge() {
        String nomFitxer = JOptionPane.showInputDialog("Escriu el nom del fitxe que vols recupera: ");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomFitxer))) {

            Personatge carrega = (Personatge) ois.readObject();
            JOptionPane.showMessageDialog(null, "Personatge carregat correctament");

            this.pj = carrega;

            etNom.setText(pj.getNom() + "       ");
            etNivell.setText(" Lvl:" + pj.getNivell());
            etXp.setText(" Xp:" + pj.getXp() + "/" + pj.getXpNecessaria());
            etOr.setText(" Or: " + pj.getOr());
            etAtributs.setText(" Atc:" + pj.getAtac() + " | Def: " + pj.getDefensa() + " | Agl: " + pj.getAgilitat());

            panellSuperior.revalidate();
            panellSuperior.repaint();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    private void triaClasse() {

        CatalegRaces b = new CatalegRaces(this);
        b.obrirCataleg();
        panellPrincipal.add(panellSuperior, BorderLayout.NORTH); //tenia un problema
        marc.repaint();

    }

    private void obrirBotiga() {

        Botiga b = new Botiga(this);
        b.obrirBotiga();
        panellPrincipal.add(panellSuperior, BorderLayout.NORTH); //tenia un problema
        marc.repaint();

    }

    private void modificarFonts() {

        Font mevafont = new Font("Roboto", Font.BOLD, 20);

        etNom.setFont(mevafont);
    }

    private void novaExploracio() {

        Exploracio explora = new Exploracio(this);
        explora.startExploracio();
        panellPrincipal.add(panellSuperior, BorderLayout.NORTH); //tenia un problema
        marc.repaint();

    }

    public Personatge getPj() {
        return pj;
    }

    public JPanel getPanellSuperior() {
        return panellSuperior;
    }

    public FramePer getMarc() {
        return marc;
    }

    public JLabel getEtAtributs() {
        return etAtributs;
    }

    public JLabel getEtXp() {
        return etXp;
    }

    public JLabel getEtNivell() {
        return etNivell;
    }

    public JLabel getEtOr() {
        return etOr;
    }
}
