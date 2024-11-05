package dad.ahorcado.controllers;

import dad.ahorcado.model.Puntuaciones;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

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
    private TextField nombreText;

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

        palabraTab.setContent(palabrasController.getRoot());
        puntuacionesTab.setContent(puntuacionesController.getRoot());

        partidaController.palabrasListaProperty().bind(palabrasController.palabrasListProperty());
        palabraTab.disableProperty().bind(partidaController.hasEndedProperty().not());
    }

    public TabPane getRoot() {
        return root;
    }

    public PalabrasController getPalabrasController() {
        return palabrasController;
    }

    public PuntuacionesController getPuntuacionesController() {
        return puntuacionesController;
    }

    @FXML
    void onComenzarAction(ActionEvent event) {
        if (!nombreText.getText().isEmpty()){
            partidaTab.setContent(partidaController.getRoot());
            partidaController.setName(nombreText.getText());
            partidaController.startGame();

            boolean scoreExists = false;
            for (Puntuaciones points : puntuacionesController.getPuntuaciones()){
                if (points.getName().equals(nombreText.getText())){
                    partidaController.nameProperty().bind(points.nameProperty());
                    partidaController.pointsProperty().bindBidirectional(points.pointsProperty());
                    scoreExists = true;
                }
            }
            if (!scoreExists){
                puntuacionesController.getPuntuaciones().add(new Puntuaciones(new SimpleStringProperty(nombreText.getText()) , new SimpleIntegerProperty(0)));
                partidaController.nameProperty().bind(puntuacionesController.getPuntuaciones().get(puntuacionesController.getPuntuaciones().size() - 1).nameProperty());
                partidaController.pointsProperty().bindBidirectional(puntuacionesController.getPuntuaciones().get(puntuacionesController.getPuntuaciones().size() - 1).pointsProperty());
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ahorcado");
            alert.setHeaderText("Error");
            alert.setContentText("El nombre no puede estar vacio");
            alert.show();
        }

    }

}
