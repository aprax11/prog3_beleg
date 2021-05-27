package gui;

import automat.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public class Controller {
    private GeschäftslogikImpl gl;
    private Hersteller hersteller = new HerstellerImpl("MaximDerCoole");
    private List<Automatenobjekt> lsit = new ArrayList<>();
    @FXML
    private ListView kuchenListView;
    @FXML
    private TextField positionField;
    @FXML
    private Button einfügeButton;
    @FXML
    private Button löschenButton;

    @FXML
    public void initialize() {
        this.gl = new GeschäftslogikImpl(4);
        this.gl.addHersteller(this.hersteller);
        this.löschenButton.setDisable(true);
        this.updateList();
    }
    @FXML
    public void onButtonClicked(ActionEvent e) {
        if(e.getSource().equals(this.einfügeButton)) {
            try {
                this.gl.addKuchen("Kremkuchen", "krem", this.hersteller, EnumSet.allOf(Allergen.class), 450, Duration.ofDays(32), "Kartoffel", new BigDecimal(12));
            } catch (InterruptedException ignored) {
            }
        }else if (e.getSource().equals(this.löschenButton)) {
            String text = this.positionField.getText();
            try {
                int pos = Integer.parseInt(text);
                this.gl.löscheKuchen(pos);
            } catch (Exception ignore) {
            }
        }
        this.updateList();
    }
    @FXML
    public void handleKeyReleased() {
        String text = this.positionField.getText();
        boolean disableButtons = text.isEmpty() || text.trim().isEmpty();
        this.löschenButton.setDisable(disableButtons);
    }
    @FXML
    public void list() {
        ListView<Automatenobjekt> list = new ListView<>();
        Automatenobjekt[] objekte = this.gl.listKuchen(null);
        ObservableList<Automatenobjekt> obl = FXCollections.observableArrayList();
        obl.addAll(Arrays.asList(objekte));
        list.setItems(obl);
    }
    public void updateList() {
        this.lsit = Arrays.asList(this.gl.listKuchen(null).clone());
        this.kuchenListView.getItems().setAll(this.lsit);
    }
}
