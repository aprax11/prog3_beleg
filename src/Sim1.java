import automat.GeschäftslogikImpl;
import beobachterMusterInterfaces.Beobachter;
import sim.EinfügeRunner;
import sim.KuchenLöschenRunner;
import sim.SimLogic;
import view.KuchenHinzufügenBeobachter;

public class Sim1 {
    public static void main(String[] args) {
        GeschäftslogikImpl gl = new GeschäftslogikImpl(10000);
        Beobachter beobachter = new KuchenHinzufügenBeobachter(gl);
        SimLogic sim = new SimLogic(gl);

        new Thread(new EinfügeRunner(sim)).start();
        new Thread(new KuchenLöschenRunner(sim)).start();
    }
}
