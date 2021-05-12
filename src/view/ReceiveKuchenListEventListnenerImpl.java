package view;

import events.ReceiveKuchenListEvent;

public class ReceiveKuchenListEventListnenerImpl implements ReceiveKuchenListEventListener{
    private ViewClass view;

    public ReceiveKuchenListEventListnenerImpl(ViewClass view) {
        this.view = view;
    }
    @Override
    public void onReceiveKuchenListEvent(ReceiveKuchenListEvent event) {
        this.view.printList(event.getList());
    }
}
