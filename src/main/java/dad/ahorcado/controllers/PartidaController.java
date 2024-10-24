package dad.ahorcado.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PartidaController implements Initializable {

    @FXML
    private TextField guessText;

    @FXML
    private Label guessedLetters;

    @FXML
    private ImageView hangImage;

    @FXML
    private Label hiddenWord;

    @FXML
    private Button letterButton;

    @FXML
    private Label nameLabel;

    @FXML
    private Label pointsLabel;

    @FXML
    private BorderPane root;

    @FXML
    private Button solveButton;

    public PartidaController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PartidaView.fxml"));
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
    void onLetterAction(ActionEvent event) {

    }

    @FXML
    void onSolveAction(ActionEvent event) {

    }

}
