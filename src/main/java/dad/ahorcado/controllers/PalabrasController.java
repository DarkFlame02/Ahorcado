package dad.ahorcado.controllers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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

    private final ObjectProperty<Palabra> palabra = new SimpleObjectProperty<>();

    // view

    @FXML
    private ListView<String> palabrasList;

    @FXML
    private GridPane root;

    @FXML
    private TextField nuevaPalabraText;

    private ObservableList<String> palabras;

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
        // Inicializa la lista observable y la asocia con palabrasList
        palabras = FXCollections.observableArrayList();
        palabrasList.setItems(palabras);
    }

    public GridPane getRoot() {
        return root;
    }

    @FXML
    void onAddAction(ActionEvent event) {
        String nuevaPalabra = nuevaPalabraText.getText();
        Palabra palabra = new Palabra();
        palabra.setPalabra(nuevaPalabra);
        palabras.add(String.valueOf(palabra));
        palabrasList.getSelectionModel().select(String.valueOf(palabra));
    }

    @FXML
    void onRemoveAction(ActionEvent event) {
        String palabraSeleccionada = palabrasList.getSelectionModel().getSelectedItem();
        if (palabraSeleccionada != null) {
            palabras.remove(palabraSeleccionada);  // Elimina la palabra seleccionada de la lista observable
        }
    }

    public Palabra getFriend() {
        return palabra.get();
    }

    public ObjectProperty<Palabra> friendProperty() {
        return palabra;
    }

    public void setFriend(Palabra palabra) {
        this.palabra.set(palabra);
    }

}
