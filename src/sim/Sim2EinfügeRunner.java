package sim;

public class Sim2EinfügeRunner implements Runnable{
    private SimLogic sim;

    public Sim2EinfügeRunner(SimLogic sim) {
        this.sim = sim;
    }

    @Override
    public void run() {
        while(true) {
            this.sim.einfügenSim2();

        }
    }
}
