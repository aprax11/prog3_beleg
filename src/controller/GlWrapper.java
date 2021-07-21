package controller;

import automat.GeschäftslogikImpl;

public class GlWrapper {
    private GeschäftslogikImpl gl;

    public GlWrapper(GeschäftslogikImpl gl) {
        this.gl = gl;
    }

    void setGl(GeschäftslogikImpl gl) {
        this.gl = gl;
    }

    GeschäftslogikImpl getGl() {
        return this.gl;
    }
}
