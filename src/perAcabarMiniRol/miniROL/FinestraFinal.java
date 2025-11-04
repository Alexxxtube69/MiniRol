package perAcabarMiniRol.miniROL;

import javax.swing.*;
import java.awt.*;

public class FinestraFinal {

    private JTextArea areaText;
    private JLabel imatge;
    private JButton botoSortir;
    private ImageIcon rutaImatge;

    private JDialog marc;
    private JPanel panellPrincipal;

    //contants de classe
    //si són static es veuen des de fora
    public static final int VICTORIA = 0;
    public static final int DERROTA = 1;

    private int condicio;
    private Personatge pj;

    public FinestraFinal(int condicio, Personatge pj) {

        marc= new JDialog();
        panellPrincipal = new JPanel(new BorderLayout());

        areaText = new JTextArea();
        botoSortir = new JButton("Finalitzar");;

        this.condicio = condicio;

        if (condicio == VICTORIA)  rutaImatge = new ImageIcon("./imatges/victoria.png");
        else rutaImatge = new ImageIcon("./imatges/derrota.png");

        imatge = new JLabel(rutaImatge);
        this.pj = pj;
    }

    public void obrir() {

        prepararMissatge();
        muntarEscena();
        marc.setVisible(true);
    }

    private void prepararMissatge() {

        String missatgeFinal;

        if (condicio==VICTORIA) {

            missatgeFinal = "Has aconseguit derrotar al senyor del castell. Tornes a casa" +
                    "teva amb una victòria.\n"
                    + pj.getNom() + " Nivell: " +pj.getNivell() + " has portat amb tu "
                    + pj.getOr() + " monedes d'or \n";

        } else {

            missatgeFinal = "Has sigut aniquilat en el castell. Els teus sers estimats " +
                    " han enterrat el que quedava de tu.\n"
                    + "Torna a intentartar-ho si t'atreveixes. \n"
                    + "Informacio dels monstres eliminats: ";
        }

        missatgeFinal += generarEstadistiquesEliminacions();
        missatgeFinal += generarEstadistiquesDany();
        areaText.setText(missatgeFinal);
    }

    private void muntarEscena() {

        //afegim imatge part nord
        panellPrincipal.add(imatge, BorderLayout.NORTH);

        //afegim area text al centre de la pantalla
        panellPrincipal.add(areaText, BorderLayout.CENTER);

        //afegim botó a la part sud
        botoSortir.addActionListener(e-> System.exit(0));


        panellPrincipal.add(botoSortir, BorderLayout.SOUTH);

        marc.add(panellPrincipal);
        marc.setSize(600,500);
        marc.setLocationRelativeTo(null); //queda al centre
        marc.setModal(true);


    }

    private String generarEstadistiquesEliminacions(){
        String missatge = "Els monstres que has derrotat son: \n";

        for (String clau : pj.getMonstresEliminats().keySet()){
            missatge += "- " + clau + ": " + pj.getMonstresEliminats().get(clau) + " vegades \n";
        }

        return missatge;

    }

    private String generarEstadistiquesDany(){
        String missatge = "Estadistiques del dany que has fet i t'han fet: \n";
        for (String clau : pj.getEstadistiquesDany().keySet()){
            missatge += "Al monstre " + clau + ",  li has fet " + pj.getEstadistiquesDany().get(clau)[0] + " de mal" + " i ell t'ha fet " + pj.getEstadistiquesDany().get(clau)[1] + " de mal. \n";
        }
        return missatge;

    }


}
