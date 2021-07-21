package eventApi;

import java.util.ArrayList;

public class GetAllergenHandler {
    private ArrayList<GetAllergenListener> list = new ArrayList<>();

    public void add(GetAllergenListener listener) {
        this.list.add(listener);
    }

    public void remove(GetKuchenListEventListener listener) {
        this.list.remove(listener);
    }

    public void handle(GetAllergeneEvent event) {
        for(GetAllergenListener l : this.list) {
            l.onGetAllergeneEvent(event);
        }
    }
}
