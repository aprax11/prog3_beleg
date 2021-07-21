package eventApi;

import java.util.EventListener;

public interface GetAllergenListener extends EventListener {
    void onGetAllergeneEvent(GetAllergeneEvent event);
}
