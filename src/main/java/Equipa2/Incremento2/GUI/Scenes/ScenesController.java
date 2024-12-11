package Equipa2.Incremento2.GUI.Scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import org.springframework.context.ConfigurableApplicationContext;


public class ScenesController {

    
    public static void changeScene(Stage stage, String fxmlFile, String titulo, String email, String password, ConfigurableApplicationContext applicationContext) {
        Parent root = null;
        
        try{
        //root = FXMLLoader.load(ScenesController.class.getResource(fxmlFile));
        FXMLLoader loader = new FXMLLoader(ScenesController.class.getResource(fxmlFile));
		loader.setControllerFactory(applicationContext::getBean);
		root = loader.load();

        } catch(IOException e){
            e.printStackTrace();
        }
        //Stage stage = (Stage) ((Node) evento.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }
}