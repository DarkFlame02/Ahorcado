package dad.ahorcado.model;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

public class PuntuacionesList extends SimpleListProperty<Puntuaciones> {

    public PuntuacionesList() {
        super(FXCollections.observableArrayList());
    }

}
