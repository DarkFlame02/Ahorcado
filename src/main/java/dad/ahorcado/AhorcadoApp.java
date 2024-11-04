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
import java.util.List;

public class AhorcadoApp extends Application {
    private static final Gson gson = FxGson.fullBuilder().setPrettyPrinting().create();
    private static final File DATA_DIR = new File(System.getProperty("user.home"), ".ahorcado");
    private static final File PALABRAS_FILE = new File("src/main/resources/files/palabras.txt");
    private static final File PUNTUACIONES_FILE = new File(DATA_DIR, "puntuaciones.json");

    private final RootController rootController = new RootController();

    @Override
    public void init() throws Exception {
        super.init();
        loadPalabras();
        loadPuntuaciones();
    }

    private void loadPalabras() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PALABRAS_FILE))) {
            String linea;
            List<String> palabrasList = rootController.getPalabrasController().getPalabrasList();
            while ((linea = reader.readLine()) != null) {
                palabrasList.add(linea);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar palabras del archivo", e);
        }
    }

    private void loadPuntuaciones() {
        if (PUNTUACIONES_FILE.exists()) {
            try {
                String json = Files.readString(PUNTUACIONES_FILE.toPath(), StandardCharsets.UTF_8);
                PuntuacionesList puntuacionesList = gson.fromJson(json, PuntuacionesList.class);
                rootController.getPuntuacionesController().getPuntuaciones().addAll(puntuacionesList);
            } catch (IOException e) {
                throw new RuntimeException("Error al cargar puntuaciones del archivo", e);
            }
        }
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(rootController.getRoot());
        primaryStage.setTitle("Ahorcado");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        ensureDataDirectoryExists();
        savePalabras();
        savePuntuaciones();
    }

    private void ensureDataDirectoryExists() {
        if (!DATA_DIR.exists() && !DATA_DIR.mkdir()) {
            throw new RuntimeException("No se pudo crear el directorio de datos");
        }
    }

    private void savePalabras() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PALABRAS_FILE))) {
            for (String palabra : rootController.getPalabrasController().getPalabrasList()) {
                writer.write(palabra);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar palabras en el archivo", e);
        }
    }

    private void savePuntuaciones() {
        try {
            String json = gson.toJson(rootController.getPuntuacionesController().getPuntuaciones());
            Files.writeString(PUNTUACIONES_FILE.toPath(), json, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar puntuaciones en el archivo", e);
        }
    }
}
