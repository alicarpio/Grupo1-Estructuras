package proyecto.grupo1.sopaletras;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static Parent loadFXML(String resource) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(resource));
        return loader.load();
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = loadFXML("/fxml/inicio.fxml");
            Scene scene = new Scene(root, 1100, 735);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
//            primaryStage.setMaximized(true);
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
