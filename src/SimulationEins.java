import automat.GeschäftslogikImpl;
import beobachterMusterInterfaces.Beobachter;
import view.KuchenHinzufügenBeobachter;

public class SimulationEins {
    public static void main(String[] args) {
        GeschäftslogikImpl gl = new GeschäftslogikImpl(4);
        Beobachter beobachter = new KuchenHinzufügenBeobachter(gl);

        new Thread(new EinfügeRunner(gl)).start();
        new Thread(new KuchenLöschenRunner(gl)).start();
    }
}
