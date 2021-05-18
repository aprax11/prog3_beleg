package controller;

import automat.GeschäftslogikImpl;
import automat.Hersteller;
import eventApi.AddHerstellerEvent;
import eventApi.AddHerstellerEventListener;

public class AddHerstellerEventListnerImpl implements AddHerstellerEventListener {
    private GeschäftslogikImpl gl;

    public AddHerstellerEventListnerImpl(GeschäftslogikImpl gl) {
        this.gl = gl;
    }
    @Override
    public void onAddHerstellerEvent(AddHerstellerEvent event) {
        Hersteller hersteller = event.getHerstellerName();
        if(event.getBool()) {
            this.gl.addHersteller(hersteller);
        }else if(!(event.getBool())) {
            try {
                this.gl.löscheHersteller(event.getHerstellerName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
