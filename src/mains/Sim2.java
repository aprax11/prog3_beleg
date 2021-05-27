package mains;

import automat.GeschäftslogikImpl;
import beobachterMusterInterfaces.Beobachter;
import sim.Sim2DeleteRunner;
import sim.Sim2EinfügeRunner;
import sim.Sim2UpdateRunner;
import sim.SimLogic;
import view.InspektionsdatumBeobachter;
import view.KuchenHinzufügenBeobachter;

public class Sim2 {
    public static void main(String[] args) {
        GeschäftslogikImpl gl = new GeschäftslogikImpl(4);
        Beobachter dateBeobachter = new InspektionsdatumBeobachter(gl);
        Beobachter einfügeBeobachter = new KuchenHinzufügenBeobachter(gl);
        SimLogic sim = new SimLogic(gl);

        Sim2EinfügeRunner einfügen = new Sim2EinfügeRunner(sim);
        Sim2DeleteRunner delete = new Sim2DeleteRunner(sim);
        Sim2UpdateRunner update = new Sim2UpdateRunner(sim);

        new Thread(einfügen).start();
        new Thread(delete).start();
        new Thread(update).start();
    }
}
