package sim;

import automat.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

public class EinfügeRunner implements Runnable{
    private SimLogic sim;

    public EinfügeRunner(SimLogic sim) {
        this.sim = sim;
    }
    @Override
    public void run() {
        while(true) {
            try {
                this.sim.addRandomKuchen();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
