package dad.ahorcado.controllers;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PalabrasController implements Initializable {

    // model

    private final ListProperty<String> palabrasLista = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final StringProperty palabras = new SimpleStringProperty();
    private final StringProperty selectedPalabra = new SimpleStringProperty();

    // view

    @FXML
    private ListView<String> palabrasList;

    @FXML
    private GridPane root;

    @FXML
    private TextField nuevaPalabraText;

    public PalabrasController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PalabraView.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        palabrasList.itemsProperty().bind(palabrasLista);
        palabras.bind(nuevaPalabraText.textProperty());
        selectedPalabra.bind(palabrasList.getSelectionModel().selectedItemProperty());
    }

    public GridPane getRoot() {
        return root;
    }

    @FXML
    void onAddAction(ActionEvent event) {
        palabrasLista.add(palabras.get());
    }

    @FXML
    void onRemoveAction(ActionEvent event) {
        palabrasLista.remove(selectedPalabra.get());
    }

    public ObservableList<String> getPalabrasList() {
        return palabrasLista;  // Devolver la propiedad en lugar de llamar a get()
    }

    public ListProperty<String> palabrasListProperty() {
        return palabrasLista;
    }

    public void setPalabrasList(ObservableList<String> palabrasList) {
        this.palabrasLista.set(palabrasList);
    }

}
