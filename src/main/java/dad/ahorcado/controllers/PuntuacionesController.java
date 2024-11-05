package dad.ahorcado.controllers;

import dad.ahorcado.model.Puntuaciones;
import javafx.beans.Observable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

public class PuntuacionesController implements Initializable {

    // model

    private ListProperty<Puntuaciones> puntuaciones = new SimpleListProperty<>(FXCollections.observableArrayList(
            Puntuacion -> new Observable[]{Puntuacion.nameProperty() , Puntuacion.pointsProperty()}
    ));

    // view

    @FXML
    private ListView<Puntuaciones> pointsList;

    @FXML
    private BorderPane root;

    public PuntuacionesController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PuntuacionesView.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pointsList.itemsProperty().bind(new SimpleListProperty<>(puntuaciones.sorted(new PuntuacionesComparator())));
    }

    public BorderPane getRoot() {
        return root;
    }

    public ObservableList<Puntuaciones> getPuntuaciones() {
        return puntuaciones.get();
    }

    public ListProperty<Puntuaciones> puntuacionesProperty() {
        return puntuaciones;
    }

    public void setPuntuaciones(ObservableList<Puntuaciones> puntuaciones) {
        this.puntuaciones.set(puntuaciones);
    }

    private static class PuntuacionesComparator implements Comparator<Puntuaciones> {
        @Override
        public int compare(Puntuaciones p1, Puntuaciones p2) {
            return Integer.compare(p2.getPoints(), p1.getPoints()); // Ordenar de mayor a menor
        }
    }

}
