package sim;

public class Sim2DeleteRunner implements  Runnable{
    private SimLogic sim;

    public Sim2DeleteRunner(SimLogic sim) {
        this.sim = sim;
    }

    @Override
    public void run() {
        while(true) {
            try {
                this.sim.löscheÄltesten();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
