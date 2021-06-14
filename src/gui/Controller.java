package gui;

import automat.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.temporal.Temporal;
import java.util.*;

public class Controller {
    private static final Hersteller[] HERSTELLERS = {new HerstellerImpl("Paul"), new HerstellerImpl("Peter"), new HerstellerImpl("Hans")};
    private GeschäftslogikImpl gl;
    private List<Automatenobjekt> lsit = new ArrayList<>();

    @FXML
    private TableView tableView;
    @FXML
    private TextField positionField;
    @FXML
    private ToggleGroup kuchenartGruppe;
    @FXML
    private Button einfügeButton;
    @FXML
    private Button löschenButton;
    @FXML
    private TextField herstellerField;
    @FXML
    private RadioButton obstkuchen;
    @FXML
    private RadioButton obsttorte;
    @FXML
    private RadioButton kremkuchen;
    @FXML
    private TextField durationPicker;
    @FXML
    private CheckBox gluten;
    @FXML
    private CheckBox haselnuss;
    @FXML
    private CheckBox sesamen;
    @FXML
    private CheckBox erdnuss;
    @FXML
    private TextField preis;
    @FXML
    private Spinner kalorien;
    @FXML
    private TextField kremsorte;
    @FXML
    private TextField obstsorte;


    @FXML
    public void initialize() {
        this.gl = new GeschäftslogikImpl(12);
        this.löschenButton.setDisable(true);
        this.einfügeButton.setDisable(true);
        this.kuchenartGruppe.selectToggle(obstkuchen);
        for(Hersteller h : HERSTELLERS) {
            this.gl.addHersteller(h);
        }
        TableColumn<ShowKuchen, Integer> fachnummerColumn = new TableColumn<>("Fachnummer");
        fachnummerColumn.setCellValueFactory(new PropertyValueFactory<>("fachnummer"));
        TableColumn<ShowKuchen, String> herstellerColumn = new TableColumn<>("Hersteller");
        herstellerColumn.setCellValueFactory(new PropertyValueFactory<>("hersteller"));
        TableColumn<ShowKuchen, Long> durationColumn = new TableColumn<>("Haltbarkeit");
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        TableColumn<ShowKuchen, LocalDate> inspectionColumn = new TableColumn<>("Inspektionsdatum");
        inspectionColumn.setCellValueFactory(new PropertyValueFactory<>("inspektion"));
        this.tableView.getColumns().add(fachnummerColumn);
        this.tableView.getColumns().add(herstellerColumn);
        this.tableView.getColumns().add(durationColumn);
        this.tableView.getColumns().add(inspectionColumn);
        this.updateList();
    }
    @FXML
    public void onButtonClicked(ActionEvent e) {
        if (e.getSource().equals(this.löschenButton)) {
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
        boolean hersteller = this.herstellerField.getText().isEmpty() || this.herstellerField.getText().trim().isEmpty();
        boolean duration = this.durationPicker.getText().isEmpty() || this.durationPicker.getText().trim().isEmpty();
        boolean preis = this.preis.getText().isEmpty() || this.preis.getText().trim().isEmpty();
        boolean combined = true;
        if(!hersteller && !duration && !preis) {
            combined = false;
        }
        this.einfügeButton.setDisable(combined);
    }
    @FXML
    public void list() {
//        ListView<Automatenobjekt> list = new ListView<>();
//        Automatenobjekt[] objekte = this.gl.listKuchen(null);
//        ObservableList<Automatenobjekt> obl = FXCollections.observableArrayList();
//        obl.addAll(Arrays.asList(objekte));
//        list.setItems(obl);
//        TableColumn<Automatenobjekt, Integer> fachnummerColumn = new TableColumn<>("Fachnummer");
//        fachnummerColumn.setCellValueFactory(new PropertyValueFactory<>("fachnummer"));
    }
    @FXML
    public void addeKuchen() {
        RadioButton selected = (RadioButton) this.kuchenartGruppe.getSelectedToggle();
        String name = selected.getText();

        HashSet<Allergen> allergens = new HashSet<>();
        if (this.gluten.isSelected()) {
            allergens.add(Allergen.Gluten);
        }if (this.sesamen.isSelected()) {
            allergens.add(Allergen.Sesamsamen);
        }if (this.erdnuss.isSelected()) {
            allergens.add(Allergen.Erdnuss);
        }if (this.haselnuss.isSelected()) {
            allergens.add(Allergen.Haselnuss);
        }
        try {
            Hersteller hersteller = new HerstellerImpl(this.herstellerField.getText());
            String split = this.kalorien.getValue().toString();
            String[] pars = split.split("\\.");
            int nährwert = Integer.parseInt(pars[0]);
            String tage = this.durationPicker.getText();
            Duration duration = Duration.ofDays(Long.parseLong(tage));
            BigDecimal preis = new BigDecimal(this.preis.getText());
            this.gl.addKuchen(name, this.kremsorte.getText(), hersteller,allergens, nährwert, duration, this.obstsorte.getText(), preis);
        } catch (Exception e) {
        }
        this.updateList();
    }

    public void updateList() {
        this.tableView.getItems().clear();
        this.lsit = Arrays.asList(this.gl.listKuchen(null).clone());
        ShowKuchen[] showlist = new ShowKuchen[this.lsit.size()];
        int count = 0;
        for(Automatenobjekt a : this.lsit) {
            ShowKuchen obj = new ShowKuchen(a.getHersteller().getName(), a.getHaltbarkeit().toDays(), a.getFachnummer(), a.getInspektionsdatum());
            showlist[count] = obj;
            count++;
        }
        this.tableView.getItems().setAll(showlist);
    }
}
