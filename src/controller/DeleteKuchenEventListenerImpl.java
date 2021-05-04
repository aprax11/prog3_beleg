package controller;

import automat.GeschäftslogikImpl;
import events.DeleteKuchenEvent;

public class DeleteKuchenEventListenerImpl implements DeleteKuchenEventListener{

    private GeschäftslogikImpl gl;

    public DeleteKuchenEventListenerImpl(GeschäftslogikImpl gl) {
        this.gl = gl;
    }

    @Override
    public void onDeleteKuchenEvent(DeleteKuchenEvent event) {
        this.gl.löscheKuchen(event.getPosition());
    }
}
