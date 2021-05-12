package view;

import automat.GeschäftslogikImpl;
import beobachterMusterInterfaces.Beobachter;

public class KuchenHinzufügenBeobachter implements Beobachter {
    private GeschäftslogikImpl gl;
    private int oldState;

    public KuchenHinzufügenBeobachter(GeschäftslogikImpl gl) {
        this.gl = gl;
        this.gl.meldeAn(this);
        this.oldState = this.gl.getFachnummer();
    }

    @Override
    public void aktualisiere() {
        int newState = this.gl.getFachnummer();
        if(this.oldState > newState) {
            System.out.println("Kuchen wurde entfernt");
        }else  if(this.oldState < newState) {
            System.out.println("Kuchen wurde hinzugefügt");
        }
    }
}
