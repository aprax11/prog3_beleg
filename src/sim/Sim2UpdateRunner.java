package sim;

public class Sim2UpdateRunner implements Runnable{
    private SimLogic sim;

    public Sim2UpdateRunner(SimLogic sim) {
        this.sim = sim ;
    }

    @Override
    public void run() {
        while(true) {
            this.sim.updateRandomKuchen();
        }
    }
}
