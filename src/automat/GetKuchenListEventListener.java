package automat;

import java.util.EventListener;

public interface GetKuchenListEventListener extends EventListener {
    void onGetKuchenEvent(GetKuchenListEvent event);
}
