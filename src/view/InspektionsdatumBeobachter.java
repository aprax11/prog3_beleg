package view;

import automat.Automatenobjekt;
import automat.GeschäftslogikImpl;
import beobachterMusterInterfaces.Beobachter;

import java.util.Date;
import java.util.HashSet;

public class InspektionsdatumBeobachter implements Beobachter {
    private GeschäftslogikImpl gl;
    private HashSet<Date> oldState = new HashSet<>();

    public InspektionsdatumBeobachter(GeschäftslogikImpl gl) {
        this.gl = gl;
        this.gl.meldeAn(this);
    }
    private void updateHash() {
        Automatenobjekt[] list = this.gl.listKuchen(null);
        for (Automatenobjekt a : list) {
            this.oldState.add(a.getInspektionsdatum());
        }
    }

    @Override
    public void aktualisiere() {
        Automatenobjekt[] newState = this.gl.listKuchen(null);
        for(Automatenobjekt a : newState) {
            if (!(this.oldState.contains(a.getInspektionsdatum()))) {
                System.out.println("Inspektionsdatum geändert");
                this.updateHash();
            }
        }
    }
}
