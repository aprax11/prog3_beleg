import automat.GeschäftslogikImpl;
import automat.GetKuchenEventListenerImpl;
import beobachterMusterInterfaces.Beobachter;
import controller.*;
import eventApi.*;
import view.*;
import eventApi.ReceiveKuchenListEventListener;

public class App {


    public static void main(String[] args) {
        GeschäftslogikImpl gl = new GeschäftslogikImpl(3);
        ViewClass view = new ViewClass();
        Beobachter allergenBeobachter = new AllergenBeobachter(gl);

        AddHerstellerEventHandler addHerstellerEventHandler = new AddHerstellerEventHandler();
        AddHerstellerEventListnerImpl addHerstellerEventListener = new AddHerstellerEventListnerImpl(gl);

        ReceiveHerstellerListEventHandler receiveHerstellerListEventHandler = new ReceiveHerstellerListEventHandler();
        ReceiveHerstellerListEventListener receiveHerstellerListEventListener = new ReceiveHerstellerListEventListenerImpl(view);

        AddKuchenEventHandler addKuchenEventHandler = new AddKuchenEventHandler();
        AddKuchenEventListener addKuchenEventListener = new AddKuchenEventListenerImpl(gl);

        ReceiveKuchenListEventHandler receiveKuchenListEventHandler = new ReceiveKuchenListEventHandler();
        ReceiveKuchenListEventListener receiveKuchenListEventListener = new ReceiveKuchenListEventListnenerImpl(view);

        GetKuchenListEventHandler getKuchenListEventHandler = new GetKuchenListEventHandler();
        GetKuchenListEventListener getKuchenListEventListener = new GetKuchenEventListenerImpl(gl);

        DeleteKuchenEventHandler deleteKuchenEventHandler = new DeleteKuchenEventHandler();
        DeleteKuchenEventListener deleteKuchenEventListener = new DeleteKuchenEventListenerImpl(gl);

        GetAllergenHandler getAllergenHandler = new GetAllergenHandler();
        GetAllergenListenerInpl getAllergenListener = new GetAllergenListenerInpl(gl);

        ReceiveAllergeneHandler receiveAllergeneHandler = new ReceiveAllergeneHandler();
        getAllergenListener.setHandler(receiveAllergeneHandler);

        ReceiveAllergeneListener receiveAllergeneListener = new ReceiveAllergenListenerImpl(view);


        CliClass cli = new CliClass();

        cli.setHerstellerEventHandler(addHerstellerEventHandler);
        addHerstellerEventHandler.add(addHerstellerEventListener);

        cli.setKuchenEventHandler(addKuchenEventHandler);
        addKuchenEventHandler.add(addKuchenEventListener);

        cli.setGetKuchenListEventHandler(getKuchenListEventHandler);
        getKuchenListEventHandler.add(getKuchenListEventListener);

        cli.setDeleteKuchenEventHandler(deleteKuchenEventHandler);
        deleteKuchenEventHandler.add(deleteKuchenEventListener);

        cli.setAllergenHandler(getAllergenHandler);
        getAllergenHandler.add(getAllergenListener);

        receiveAllergeneHandler.add(receiveAllergeneListener);

        getAllergenListener.setHandler(receiveAllergeneHandler);

        addHerstellerEventListener.setHandler(receiveHerstellerListEventHandler);

        receiveHerstellerListEventHandler.add(receiveHerstellerListEventListener);

        gl.setReceiveKuchenListEventHandler(receiveKuchenListEventHandler);
        receiveKuchenListEventHandler.add(receiveKuchenListEventListener);

        cli.start();
    }
}
