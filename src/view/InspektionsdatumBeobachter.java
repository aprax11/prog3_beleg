package view;


import automat.GanzerKuchen;
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
        GanzerKuchen[] list = this.gl.listKuchen(null);
        for (GanzerKuchen a : list) {
            this.oldState.add(a.getInspektionsdatum());
        }
    }

    @Override
    public void aktualisiere() {
        GanzerKuchen[] newState = this.gl.listKuchen(null);
        for(GanzerKuchen a : newState) {
            if (!(this.oldState.contains(a.getInspektionsdatum()))) {
                System.out.println("Inspektionsdatum geändert");
                this.updateHash();
            }
        }
    }
}
