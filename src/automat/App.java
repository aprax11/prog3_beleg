package automat;

import automat.gl.GeschäftslogikImpl;

public class App {


    public static void main(String[] args) {
        GeschäftslogikImpl gl = new GeschäftslogikImpl(3);
        AddHerstellerEventHandler addHerstellerEventHandler = new AddHerstellerEventHandler();
        AddHerstellerEventListener addHerstellerEventListener = new AddHerstellerEventListnerImpl(gl);
        CliClass cli = new CliClass();

        cli.setHerstellerEventHandler(addHerstellerEventHandler);
        addHerstellerEventHandler.add(addHerstellerEventListener);
        cli.start();
    }
}
