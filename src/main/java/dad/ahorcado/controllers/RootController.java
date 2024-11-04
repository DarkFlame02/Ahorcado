package dad.ahorcado.controllers;

import javafx.beans.Observable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {

    // controllers

    private final PartidaController partidaController = new PartidaController();
    private final PalabrasController palabrasController = new PalabrasController();
    private final PuntuacionesController puntuacionesController = new PuntuacionesController();

    // view

    @FXML
    private Tab palabraTab;

    @FXML
    private Tab partidaTab;

    @FXML
    private Tab puntuacionesTab;

    @FXML
    private TabPane root;

    public RootController() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RootView.fxml"));
                loader.setController(this);
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        partidaTab.setContent(partidaController.getRoot());
        palabraTab.setContent(palabrasController.getRoot());
        puntuacionesTab.setContent(puntuacionesController.getRoot());
    }

    public TabPane getRoot() {
        return root;
    }

    public ObservableList<Palabra> getPalabras() {
        return palabras.get();
    }

    public ListProperty<Palabra> friendsProperty() {
        return palabras;
    }

}
