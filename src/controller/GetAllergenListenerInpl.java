package controller;

import automat.Allergen;
import automat.GeschäftslogikImpl;
import eventApi.*;

import java.util.Set;

public class GetAllergenListenerInpl implements GetAllergenListener {
    private GeschäftslogikImpl gl;
    private ReceiveAllergeneHandler handler;

    public GetAllergenListenerInpl(GeschäftslogikImpl gl) {
        this.gl = gl;
    }

    public void setHandler(ReceiveAllergeneHandler handler) {
        this.handler = handler;
    }
    @Override
    public void onGetAllergeneEvent(GetAllergeneEvent event) {
        if(event.getVorhanden()) {
            Set<Allergen> list = this.gl.getAllergenList(true);
            ReceiveAllergeneEvent event1 = new ReceiveAllergeneEvent(this, list);
            this.handler.handle(event1);
        } else if(!event.getVorhanden()) {
            Set<Allergen> list = this.gl.getAllergenList(false);
            ReceiveAllergeneEvent event1 = new ReceiveAllergeneEvent(this, list);
            this.handler.handle(event1);
        }
    }
}
