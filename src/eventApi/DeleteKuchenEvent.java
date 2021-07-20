package eventApi;

import java.util.EventObject;

public class DeleteKuchenEvent extends EventObject {
    private int position;
    private boolean update;

    public DeleteKuchenEvent(Object source, int position, boolean update) {
        super(source);
        this.position = position;
        this.update = update;
    }

    public int getPosition() {
        return position;
    }
    public boolean getUpdate() {
        return this.update;
    }
}
