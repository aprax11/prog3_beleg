package automat;

import automat.gl.GeschäftslogikImpl;
import automat.gl.Hersteller;
import automat.gl.HerstellerImpl;

public class AddHerstellerEventListnerImpl implements AddHerstellerEventListener{
    private GeschäftslogikImpl gl;

    public AddHerstellerEventListnerImpl(GeschäftslogikImpl gl) {
        this.gl = gl;
    }
    @Override
    public void onAddHerstellerEvent(AddHerstellerEvent event) {
        Hersteller hersteller = event.getHerstellerName();
        if(event.getBool()) {
            this.gl.addHersteller(hersteller);
        }else if(!(event.getBool())) {
            this.gl.löscheHersteller(event.getHerstellerName());
        }
    }
}
