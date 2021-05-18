package sim;

public class Sim3DeleteRunner implements Runnable{
    private SimLogic sim;

    public Sim3DeleteRunner(SimLogic sim) {
        this.sim = sim;
    }

    @Override
    public void run() {
        while(true) {
            try {
                this.sim.deleteSim3();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
