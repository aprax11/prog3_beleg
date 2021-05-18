package controller;

import automat.GeschäftslogikImpl;
import eventApi.DeleteKuchenEvent;
import eventApi.DeleteKuchenEventListener;

public class DeleteKuchenEventListenerImpl implements DeleteKuchenEventListener {

    private GeschäftslogikImpl gl;

    public DeleteKuchenEventListenerImpl(GeschäftslogikImpl gl) {
        this.gl = gl;
    }

    @Override
    public void onDeleteKuchenEvent(DeleteKuchenEvent event) {
        try {
            this.gl.löscheKuchen(event.getPosition());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
