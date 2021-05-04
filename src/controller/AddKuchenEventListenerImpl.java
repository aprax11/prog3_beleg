package controller;

import automat.GeschäftslogikImpl;
import events.AddKuchenEvent;

public class AddKuchenEventListenerImpl implements AddKuchenEventListener{
    private GeschäftslogikImpl gl;

    public AddKuchenEventListenerImpl(GeschäftslogikImpl gl) {
        this.gl = gl;
    }

    @Override
    public void onAddKuchenEvent(AddKuchenEvent event) {
        this.gl.addKuchen(event.getName(), event.getKremsorte(), event.getHersteller(), event.getAllergens(), event.getNährwert(), event.getHaltbarkeit(), event.getObstsorte(), event.getPreis());
    }
}
