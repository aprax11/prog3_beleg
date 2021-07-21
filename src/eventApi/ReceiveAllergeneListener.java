package eventApi;

import java.util.EventListener;

public interface ReceiveAllergeneListener extends EventListener {
    void onReceiveAllergeneEvent(ReceiveAllergeneEvent event);
}
