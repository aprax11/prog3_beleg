package view;

import automat.Automatenobjekt;
import automat.Hersteller;

import java.util.Map;


public class ViewClass {

    public void printList(Automatenobjekt[] list) {
        for (Automatenobjekt a : list) {
            System.out.println("Fachnummer: " + a.getFachnummer() + System.lineSeparator() + "Inspektionsdatum: "
                    + a.getInspektionsdatum() + System.lineSeparator() + "verbleibende Haltbarkeit in Tagen:" + a.getVerbleibendeHaltbarkeit());
        }
    }
    public void printHersteller(Map<Hersteller, Integer> list) {
        for(Map.Entry<Hersteller, Integer> e : list.entrySet()) {
            System.out.println("Hersteller: " + e.getKey().getName() + " Kuchen Anzahl: " + e.getValue());
        }
    }
}

