package sim;

public class Sim3EinfügeRunner implements Runnable{
    private SimLogic sim;

    public Sim3EinfügeRunner(SimLogic sim) {
        this.sim = sim;
    }

    @Override
    public void run() {
        while(true) {

            this.sim.einfügenSim2();

        }
    }
}
