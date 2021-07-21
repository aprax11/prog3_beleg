package controller;

import automat.GeschäftslogikImpl;
import automat.Hersteller;
import eventApi.AddHerstellerEvent;
import eventApi.AddHerstellerEventListener;
import eventApi.ReceiveHerstellerListEvent;
import eventApi.ReceiveHerstellerListEventHandler;

import java.util.HashMap;
import java.util.Map;

public class AddHerstellerEventListnerImpl implements AddHerstellerEventListener {
    private GlWrapper gl;
    ReceiveHerstellerListEventHandler handler;

    public AddHerstellerEventListnerImpl(GlWrapper gl) {
        this.gl = gl;
    }
    @Override
    public void onAddHerstellerEvent(AddHerstellerEvent event) {
        Hersteller hersteller = event.getHerstellerName();



        if (!event.getShow()) {
            if (event.getBool()) {
                this.gl.getGl().addHersteller(hersteller);
            } else if (!(event.getBool())) {
                this.gl.getGl().löscheHersteller(event.getHerstellerName().getName());
            }
        } else if(event.getShow()) {
            HashMap<Hersteller, Integer> herstellerList = this.gl.getGl().getHerstellerList();
            ReceiveHerstellerListEvent event1 = new ReceiveHerstellerListEvent(this, herstellerList);
            this.handler.handle(event1);

        }
    }
    public void setHandler(ReceiveHerstellerListEventHandler handler) {
        this.handler = handler;
    }
}
