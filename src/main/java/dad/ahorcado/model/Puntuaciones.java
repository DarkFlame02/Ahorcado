package dad.ahorcado.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Puntuaciones {

    private final StringProperty name;
    private final IntegerProperty points;

    public Puntuaciones(StringProperty name, IntegerProperty points) {
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getPoints() {
        return points.get();
    }

    public IntegerProperty pointsProperty() {
        return points;
    }

    public void setPoints(int points) {
        this.points.set(points);
    }

    @Override
    public String toString() {
        return nameProperty().get() + ": " + pointsProperty().get();
    }
}
