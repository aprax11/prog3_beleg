package sim;

public class Sim2EinfügeRunner implements Runnable{
    private SimLogic sim;

    public Sim2EinfügeRunner(SimLogic sim) {
        this.sim = sim;
    }

    @Override
    public void run() {
        while(true) {
            try {
                this.sim.einfügenSim2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
