package dad.ahorcado;

import com.google.gson.Gson;
import dad.ahorcado.controllers.RootController;
import dad.ahorcado.model.PuntuacionesList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hildan.fxgson.FxGson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class AhorcadoApp extends Application {

    private static final File DATA_DIR = new File(System.getProperty("user.home") , ".Ahorcado");
        private static final File PALABRAS_FILE = new File("src/main/resources/palabras.txt");
    private static final File PUNTUACIONES_FILE = new File(DATA_DIR, "puntuaciones.json");

    private final Gson gson = FxGson.fullBuilder()
            .setPrettyPrinting()
            .create();

    private RootController rootController = new RootController();

    @Override
    public void init() throws Exception {
        super.init();
        // se lee el txt de las palabras
        try (BufferedReader reader = new BufferedReader(new FileReader(PALABRAS_FILE))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                rootController.getPalabrasController().getPalabrasList().add(linea);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Asegúrate de manejar las excepciones correctamente
        }

        // se serializa el json de puntuaciones
        if (PUNTUACIONES_FILE.exists()) {
            String json = Files.readString(PUNTUACIONES_FILE.toPath(), StandardCharsets.UTF_8);
            PuntuacionesList puntuacionList = gson.fromJson(json, PuntuacionesList.class);
            rootController.getPuntuacionesController().getPuntuaciones().addAll(puntuacionList);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Ahorcado");
        primaryStage.setScene(new Scene(rootController.getRoot()));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        if (!DATA_DIR.exists()){
            DATA_DIR.mkdir();
        }
        // se escriben las palabras en el txt
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(PALABRAS_FILE))){
            for (int i = 0; i < rootController.getPalabrasController().getPalabrasList().size(); i++){
                writer.write(rootController.getPalabrasController().getPalabrasList().get(i));
                writer.write("\n");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // se escriben las puntuaciones en el json
        String json = gson.toJson(rootController.getPuntuacionesController().getPuntuaciones());
        Files.writeString(PUNTUACIONES_FILE.toPath(), json, StandardCharsets.UTF_8);
    }
}
