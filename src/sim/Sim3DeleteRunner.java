package sim;

import java.util.Random;

public class Sim3DeleteRunner implements Runnable{
    private SimLogic sim;

    public Sim3DeleteRunner(SimLogic sim) {
        this.sim = sim;
    }

    @Override
    public void run() {
        while(true) {
            try {
                this.sim.deleteSim3(new Random(System.currentTimeMillis()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
