package eventApi;

import automat.Container;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

public class AddKuchenEvent extends EventObject {
    List<Container> list = new ArrayList<>();
    Container boden;


   public AddKuchenEvent(Object source, List<Container> list, Container boden) {
        super(source);
       this.list = list;
       this.boden = boden;
   }

    public List<Container> getList() {
       return this.list;
    }
    public Container getContainer() {
       return this.boden;
    }
}
