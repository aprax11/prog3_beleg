package gui;

import automat.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import persistence.Jos;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.temporal.Temporal;
import java.util.*;

public class Controller {
    private GeschäftslogikImpl gl;
    private List<Automatenobjekt> lsit = new ArrayList<>();
    private HashMap<Hersteller, Integer> herstellerList = new HashMap<>();
    private String state = "null";

    @FXML
    private TableView tableView;
    @FXML
    private TableView tableView2;
    @FXML
    private Button addHersteller;
    @FXML
    private Button removeHersteller;
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
    private Button vorhandenA;
    @FXML
    private Button fehlendA;
    @FXML
    private Label allergeneLabel;
    @FXML
    private Button inspectButton;
    @FXML
    private Button kuchenType;
    @FXML
    private Button kuchenNormal;
    @FXML
    private Button saveJOS;
    @FXML
    private Button loadJOS;


    @FXML
    public void initialize() {
        this.gl = new GeschäftslogikImpl(12);
        this.löschenButton.setDisable(true);
        this.einfügeButton.setDisable(true);
        this.addHersteller.setDisable(true);
        this.removeHersteller.setDisable(true);
        this.kuchenartGruppe.selectToggle(obstkuchen);

        TableColumn<ShowKuchen, Integer> fachnummerColumn = new TableColumn<>("Fachnummer");
        fachnummerColumn.setCellValueFactory(new PropertyValueFactory<>("fachnummer"));
        TableColumn<ShowKuchen, String> herstellerColumn = new TableColumn<>("Hersteller");
        herstellerColumn.setCellValueFactory(new PropertyValueFactory<>("hersteller"));
        TableColumn<ShowKuchen, Long> durationColumn = new TableColumn<>("Haltbarkeit");
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        TableColumn<ShowKuchen, LocalDate> inspectionColumn = new TableColumn<>("Inspektionsdatum");
        inspectionColumn.setCellValueFactory(new PropertyValueFactory<>("inspektion"));

        TableColumn<ShowHersteller, String> herstellerColumn2 = new TableColumn<>("Hersteller");
        herstellerColumn2.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<ShowHersteller, Integer> anzahlColumn = new TableColumn<>("Anzhal");
        anzahlColumn.setCellValueFactory(new PropertyValueFactory<>("anzahl"));

        this.tableView.getColumns().add(fachnummerColumn);
        this.tableView.getColumns().add(herstellerColumn);
        this.tableView.getColumns().add(durationColumn);
        this.tableView.getColumns().add(inspectionColumn);

        this.tableView2.getColumns().add(herstellerColumn2);
        this.tableView2.getColumns().add(anzahlColumn);
        this.updateKuchenList(this.state);
        this.updateHerstellerList();
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
        this.updateKuchenList(this.state);
        this.updateHerstellerList();
        }
    @FXML
    public void handleKeyReleased() {
        String text = this.positionField.getText();
        boolean disableButtons = text.isEmpty() || text.trim().isEmpty();
        boolean combined = true;
        this.löschenButton.setDisable(disableButtons);
        boolean hersteller = this.herstellerField.getText().isEmpty() || this.herstellerField.getText().trim().isEmpty();
        if(!hersteller) {
            combined = false;
        }
        this.addHersteller.setDisable(combined);
        this.removeHersteller.setDisable(combined);
        boolean duration = this.durationPicker.getText().isEmpty() || this.durationPicker.getText().trim().isEmpty();
        boolean preis = this.preis.getText().isEmpty() || this.preis.getText().trim().isEmpty();

        if(!hersteller && !duration && !preis) {
            combined = false;
        }
        this.einfügeButton.setDisable(combined);
    }
    @FXML
    public void addHersteller(ActionEvent e) {
        if(e.getSource().equals(this.addHersteller)) {
            String text = this.herstellerField.getText();
            Hersteller hersteller = new HerstellerImpl(text);
            this.gl.addHersteller(hersteller);
            this.updateKuchenList(this.state);
            this.updateHerstellerList();
        }
    }
    @FXML
    public void removeHersteller(ActionEvent e) throws InterruptedException {
        if(e.getSource().equals(this.removeHersteller)) {
            String text = this.herstellerField.getText();
            this.gl.löscheHersteller(text);
            this.updateHerstellerList();
            this.updateKuchenList(this.state);
        }
    }
    @FXML
    public void displayVorhandenA(ActionEvent e) {
        if(e.getSource().equals(this.vorhandenA)) {
            Set<Allergen> set = this.gl.getAllergenList(true);
            this.allergeneLabel.setText(set.toString());
        }
    }
    @FXML
    public void displayFehlendA(ActionEvent e) {
        if(e.getSource().equals(this.fehlendA)) {
            Set<Allergen> set = this.gl.getAllergenList(false);
            this.allergeneLabel.setText(set.toString());
        }
    }
    @FXML
    public void showAllKuchen(ActionEvent e) {
        if(e.getSource().equals(this.kuchenNormal)) {
            this.state = "null";
            this.updateHerstellerList();
            this.updateKuchenList(this.state);
        }
    }

    @FXML
    public void inspect(ActionEvent e) {
        if(e.getSource().equals(this.inspectButton)) {
            try{
                String pos = this.positionField.getText();
                int posi = Integer.parseInt(pos);
                this.gl.setInspektionsdatum(posi);
            }catch(Exception ignored){
            }
        }
    }
    @FXML
    public void showType(ActionEvent e) {
        if(e.getSource().equals(this.kuchenType)) {
            RadioButton selected = (RadioButton) this.kuchenartGruppe.getSelectedToggle();
            this.state = selected.getText();
            this.updateHerstellerList();
            this.updateKuchenList(this.state);
        }
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
            String preisStr = this.preis.getText().replace(",",".");
            BigDecimal preis = new BigDecimal(preisStr);
            this.gl.addKuchen(name, this.kremsorte.getText(), hersteller,allergens, nährwert, duration, this.obstsorte.getText(), preis);
        } catch (Exception e) {
        }
        this.updateKuchenList(this.state);
        this.updateHerstellerList();
    }
    @FXML
    public void safeJOS(ActionEvent e) {
        if(e.getSource().equals(this.saveJOS)) {
            Jos.serialize("GUISafe", this.gl);
        }
    }
    @FXML
    public void loadJOS(ActionEvent e) {
        if(e.getSource().equals(this.loadJOS)) {
            this.gl = Jos.deserialize("GUISafe");
            this.updateHerstellerList();
            this.updateKuchenList(this.state);
        }
    }

    public void updateKuchenList(String type) {
        this.tableView.getItems().clear();
        switch(type) {
            case "null":
                this.lsit = Arrays.asList(this.gl.listKuchen(null).clone());
                break;
            case "Obstkuchen":
                this.lsit = Arrays.asList(this.gl.listKuchen(ObstkuchenImpl.class).clone());
                break;
            case "Kremkuchen":
                this.lsit = Arrays.asList(this.gl.listKuchen(KremkuchenImpl.class).clone());
                break;
            case "Obsttorte":
                this.lsit = Arrays.asList(this.gl.listKuchen(ObsttorteImpl.class).clone());
                break;
        }

        ShowKuchen[] showlist = new ShowKuchen[this.lsit.size()];
        int count = 0;
        for(Automatenobjekt a : this.lsit) {
            ShowKuchen obj = new ShowKuchen(a.getHersteller().getName(), a.getVerbleibendeHaltbarkeit(new Date()).toDays(), a.getFachnummer(), a.getInspektionsdatum());
            showlist[count] = obj;
            count++;
        }
        this.tableView.getItems().setAll(showlist);

    }
    public void updateHerstellerList() {
        this.tableView2.getItems().clear();
        this.herstellerList = this.gl.getHerstellerList();
        ShowHersteller[] showHerstellers = new ShowHersteller[this.herstellerList.size()];
        int cnt = 0;
        for(Map.Entry<Hersteller, Integer> e : this.herstellerList.entrySet()) {
            showHerstellers[cnt] = new ShowHersteller(e.getKey().getName(), e.getValue());
            cnt++;
        }
        this.tableView2.getItems().setAll(showHerstellers);
    }
}
