package view;

import automat.GeschäftslogikImpl;
import beobachterMusterInterfaces.Beobachter;

public class KapazitätBeobachter implements Beobachter {
    private int größe = 0;
    private GeschäftslogikImpl gl;

    public KapazitätBeobachter(GeschäftslogikImpl gl) {
        this.gl = gl;
        this.gl.meldeAn(this);
        this.größe = this.gl.getListGröße();
    }



    @Override
    public void aktualisiere() {
        int belegt = this.gl.getFachnummer();
        float kapazität = belegt*10/this.größe;

        if(kapazität >= 9) {
            System.out.println("Füllstand von 90% wurde erreicht");
        }
    }
}
