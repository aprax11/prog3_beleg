package controller;

import automat.GeschäftslogikImpl;
import eventApi.GetKuchenListEvent;
import eventApi.GetKuchenListEventListener;

public class GetKuchenEventListenerImpl implements GetKuchenListEventListener {
    private GeschäftslogikImpl gl;

    public  GetKuchenEventListenerImpl(GeschäftslogikImpl gl) {
        this.gl = gl;
    }
    @Override
    public void onGetKuchenEvent(GetKuchenListEvent event) {
        this.gl.listKuchen(event.getCl());
    }
}
