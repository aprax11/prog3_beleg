import automat.Automatenobjekt;
import automat.GeschäftslogikImpl;
import automat.Verkaufsobjekt;

import java.util.List;

public class KuchenLöschenRunner implements Runnable {
    private Automatenobjekt[] list;
    private GeschäftslogikImpl gl;

    public KuchenLöschenRunner(GeschäftslogikImpl gl) {
        this.gl = gl;
    }

    @Override
    public void run() {
        while (true) {
            this.list = this.gl.listKuchen(null);
            System.out.println(this.list.length);
            int pos = (int) (Math.random() * this.list.length);
            try {
                this.gl.löscheKuchen(pos);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
