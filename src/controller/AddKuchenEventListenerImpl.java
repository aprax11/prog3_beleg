package controller;

import automat.GeschäftslogikImpl;
import eventApi.AddKuchenEvent;
import eventApi.AddKuchenEventListener;

public class AddKuchenEventListenerImpl implements AddKuchenEventListener {
    private GlWrapper gl;

    public AddKuchenEventListenerImpl(GlWrapper gl) {
        this.gl = gl;
    }

    @Override
    public void onAddKuchenEvent(AddKuchenEvent event) {

        this.gl.getGl().addKuchen(event.getName(), event.getKremsorte(), event.getHersteller(), event.getAllergens(), event.getNährwert(), event.getHaltbarkeit(), event.getObstsorte(), event.getPreis());

    }
}
