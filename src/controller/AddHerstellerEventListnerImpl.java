package controller;

import automat.GeschäftslogikImpl;
import automat.Hersteller;

public class AddHerstellerEventListnerImpl implements AddHerstellerEventListener{
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
            this.gl.löscheHersteller(event.getHerstellerName());
        }
    }
}
