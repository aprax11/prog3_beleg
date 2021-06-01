import automat.GeschäftslogikImpl;
import beobachterMusterInterfaces.Beobachter;
import controller.*;
import eventApi.*;
import view.AllergenBeobachter;
import eventApi.ReceiveKuchenListEventListener;
import view.ReceiveKuchenListEventListnenerImpl;
import view.ViewClass;

public class App {


    public static void main(String[] args) {
        GeschäftslogikImpl gl = new GeschäftslogikImpl(3);
        ViewClass view = new ViewClass();
        Beobachter allergenBeobachter = new AllergenBeobachter(gl);

        AddHerstellerEventHandler addHerstellerEventHandler = new AddHerstellerEventHandler();
        AddHerstellerEventListener addHerstellerEventListener = new AddHerstellerEventListnerImpl(gl);

        AddKuchenEventHandler addKuchenEventHandler = new AddKuchenEventHandler();
        AddKuchenEventListener addKuchenEventListener = new AddKuchenEventListenerImpl(gl);

        ReceiveKuchenListEventHandler receiveKuchenListEventHandler = new ReceiveKuchenListEventHandler();
        ReceiveKuchenListEventListener receiveKuchenListEventListener = new ReceiveKuchenListEventListnenerImpl(view);

        GetKuchenListEventHandler getKuchenListEventHandler = new GetKuchenListEventHandler();
        GetKuchenListEventListener getKuchenListEventListener = new GetKuchenEventListenerImpl(gl);

        DeleteKuchenEventHandler deleteKuchenEventHandler = new DeleteKuchenEventHandler();
        DeleteKuchenEventListener deleteKuchenEventListener = new DeleteKuchenEventListenerImpl(gl);

        CliClass cli = new CliClass();

        cli.setHerstellerEventHandler(addHerstellerEventHandler);
        addHerstellerEventHandler.add(addHerstellerEventListener);

        cli.setKuchenEventHandler(addKuchenEventHandler);
        addKuchenEventHandler.add(addKuchenEventListener);

        cli.setGetKuchenListEventHandler(getKuchenListEventHandler);
        getKuchenListEventHandler.add(getKuchenListEventListener);

        cli.setDeleteKuchenEventHandler(deleteKuchenEventHandler);
        deleteKuchenEventHandler.add(deleteKuchenEventListener);

        gl.setReceiveKuchenListEventHandler(receiveKuchenListEventHandler);
        receiveKuchenListEventHandler.add(receiveKuchenListEventListener);

        cli.start();
    }
}
