package automat;

import java.util.EventObject;

public class DeleteKuchenEvent extends EventObject {
    private int position;

    public DeleteKuchenEvent(Object source, int position) {
        super(source);
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
