package sim;


import automat.GeschäftslogikImpl;
import automat.Verkaufsobjekt;

import java.util.List;

public class KuchenLöschenRunner implements Runnable {
    private SimLogic sim;

    public KuchenLöschenRunner(SimLogic sim) {
        this.sim = sim;
    }

    @Override
    public void run() {
        while (true) {
            this.sim.removeRandomKuchen();

        }
    }
}
