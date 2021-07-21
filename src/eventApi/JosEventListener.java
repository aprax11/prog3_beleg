package eventApi;

import java.util.EventListener;

public interface JosEventListener extends EventListener {
    void onJosEvent(JosEvent event);
}
