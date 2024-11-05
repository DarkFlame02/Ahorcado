package dad.ahorcado.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.text.Normalizer;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

public class PartidaController implements Initializable {

    // model

    private final ListProperty<String> palabrasLista = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final BooleanProperty hasEnded = new SimpleBooleanProperty(true);
    private final IntegerProperty fallos = new SimpleIntegerProperty();
    private final ObjectProperty<Image> hangmanImage = new SimpleObjectProperty<>();
    private final StringProperty name = new SimpleStringProperty();
    private final IntegerProperty points = new SimpleIntegerProperty();
    private final StringProperty guessWord = new SimpleStringProperty();
    private final StringProperty palabraOculta = new SimpleStringProperty("");
    private final StringProperty guessLetters = new SimpleStringProperty("");

    // view

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
        nameLabel.textProperty().bind(name.concat(":"));
        pointsLabel.textProperty().bind(points.asString());
        guessedLetters.textProperty().bind(guessLetters);
        guessWord.bind(guessText.textProperty());
        hiddenWord.textProperty().bind(Bindings.createStringBinding(this::hideWord, palabraOculta , guessLetters));
        hangImage.imageProperty().bind(hangmanImage);

        guessText.disableProperty().bind(hasEnded);
        letterButton.disableProperty().bind(hasEnded);
        solveButton.disableProperty().bind(hasEnded);

        fallos.addListener((o , ov , nv) -> {
            if (!nv.equals(9)){
                hangmanImage.set(new Image(getClass().getResource("/images/" + nv + ".png").toExternalForm()));
            }
            else {
                endGame(false);
            }
        });
    }

    public BorderPane getRoot() {
        return root;
    }

    @FXML
    void onLetterAction(ActionEvent event) {
        if (guessWord.get().length() == 1){
            if (!guessLetters.get().contains(guessWord.get().toUpperCase())){
                guessLetters.set(guessLetters.get() + " " + guessWord.get().toUpperCase());
                if (!Normalizer.normalize(palabraOculta.get() , Normalizer.Form.NFD).toUpperCase().contains(guessWord.get().toUpperCase())){
                    fallos.set(fallos.get() + 1);
                }
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ahorcado");
            alert.setHeaderText("Error");
            alert.setContentText("Debes introducir un unico caracter al adivinar una letra");
            alert.show();
        }
    }

    @FXML
    void onSolveAction(ActionEvent event) {
        if (guessWord.get().equals(palabraOculta.get())){
            points.set(points.get() + palabraOculta.get().length() + 9 - fallos.get());
            endGame(true);
        }
        else {
            fallos.set(fallos.get() + 1);
        }
    }

    public void startGame(){
        if (palabrasLista.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ahorcado");
            alert.setHeaderText("No hay palabras en la lista");
            alert.setContentText("Añade algunas palabras primero");
            alert.showAndWait();
            return;
        }


        Random random = new Random();
        int randomIndex = random.nextInt(palabrasLista.size() - 1);
        palabraOculta.set(palabrasLista.get().get(randomIndex));
        guessLetters.set("");
        hasEnded.set(false);

        fallos.set(0);
    }

    private String hideWord() {
        String letras = guessLetters.get().toLowerCase() + guessLetters.get().toUpperCase();
        String regex = guessLetters.get().isEmpty() ? "\\w" : "[^" + letras + "]";
        return palabraOculta.get().replaceAll(regex, "_");

    }

    private void endGame(Boolean partidaGanada) {
        Alert alertClose = new Alert(Alert.AlertType.CONFIRMATION);
        alertClose.setTitle("Ahorcado");
        alertClose.setHeaderText(partidaGanada ? "Has ganado" : "Has perdido");
        alertClose.setContentText("Quieres iniciar una nueva partida?");
        ButtonType botonAfirmativo = new ButtonType("Si");
        ButtonType botonNegativo = new ButtonType("No" );
        alertClose.getButtonTypes().setAll(botonNegativo , botonAfirmativo);

        Optional<ButtonType> result = alertClose.showAndWait();
        if (result.get() == botonAfirmativo){
            startGame();
        }
        else if (result.get() == botonNegativo){
            hasEnded.set(true);
        }
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

    public BooleanProperty hasEndedProperty() {
        return hasEnded;
    }

    public void setHasEnded(boolean hasEnded) {
        this.hasEnded.set(hasEnded);
    }

    public ObservableList<String> getPalabrasLista() {
        return palabrasLista.get();
    }

    public ListProperty<String> palabrasListaProperty() {
        return palabrasLista;
    }

    public void setPalabrasLista(ObservableList<String> palabrasLista) {
        this.palabrasLista.set(palabrasLista);
    }
}
