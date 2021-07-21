import automat.GeschäftslogikImpl;
import beobachterMusterInterfaces.Beobachter;
import sim.Sim2UpdateRunner;
import sim.Sim3DeleteRunner;
import sim.Sim3EinfügeRunner;
import sim.SimLogic;
import view.KuchenHinzufügenBeobachter;

public class Sim3 {

    public static void main(String[] args) {
        GeschäftslogikImpl gl = new GeschäftslogikImpl(4);
        SimLogic sim = new SimLogic(gl);
        Beobachter einfügeBeobachter = new KuchenHinzufügenBeobachter(gl);

        Sim3EinfügeRunner einfüger1 = new Sim3EinfügeRunner(sim);
        Sim3EinfügeRunner einfüger2 = new Sim3EinfügeRunner(sim);


        Sim3DeleteRunner delete1 = new Sim3DeleteRunner(sim);
        Sim3DeleteRunner delete2 = new Sim3DeleteRunner(sim);

        Sim2UpdateRunner update = new Sim2UpdateRunner(sim);

        new Thread(einfüger1).start();
        new Thread(einfüger2).start();
        new Thread(delete1).start();
        new Thread(delete2).start();
        new Thread(update).start();
    }
}
