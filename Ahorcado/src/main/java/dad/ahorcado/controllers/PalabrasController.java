package dad.ahorcado.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PalabrasController implements Initializable {

    @FXML
    private Button addWordButton;

    @FXML
    private ListView<?> palabrasList;

    @FXML
    private Button removeWordButton;

    @FXML
    private BorderPane root;

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

    }

    public BorderPane getRoot() {
        return root;
    }

    @FXML
    void onAddAction(ActionEvent event) {

    }

    @FXML
    void onRemoveAction(ActionEvent event) {

    }

}
