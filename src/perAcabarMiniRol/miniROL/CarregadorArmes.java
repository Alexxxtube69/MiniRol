package perAcabarMiniRol.miniROL;

import java.io.*;
import java.util.*;

public class CarregadorArmes {

    public static ArrayList<Arma> carregarArmes(String ruta) {

        ArrayList<Arma> armes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {

            String linia;
            while ((linia = br.readLine()) != null) {
                if (linia.isBlank()) continue;

                String[] parts = linia.split(";");

                String nom = parts[0].trim();
                String desc = parts[1].trim();
                int dau = Integer.parseInt(parts[2].trim());

                ArrayList<String> races = new ArrayList<>();
                for (String r : parts[3].split(",")) {
                    races.add(r.trim().toLowerCase());
                }

                int preu = Integer.parseInt(parts[4].trim());

                armes.add(new Arma(nom, desc, dau, races,preu));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return armes;
    }
}
