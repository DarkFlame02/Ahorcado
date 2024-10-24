package dad.ahorcado.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {

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

    }

    public TabPane getRoot() {
        return root;
    }



}
