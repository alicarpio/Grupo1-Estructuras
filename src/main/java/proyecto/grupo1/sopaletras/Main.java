package proyecto.grupo1.sopaletras;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class Main extends Application {
    public static Parent loadFXML(String resource) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(resource));
        return loader.load();
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = loadFXML("/fxml/inicio.fxml");
            Scene scene = new Scene(root, 800, 500);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (IOException e) {
            System.out.println("Error al cargar pantalla de inicio: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
