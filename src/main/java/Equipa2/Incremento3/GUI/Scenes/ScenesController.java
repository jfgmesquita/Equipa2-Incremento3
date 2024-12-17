package Equipa2.Incremento3.GUI.Scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.UUID;

import org.json.JSONObject;

import Equipa2.Incremento3.services.ApiService;

public class ScenesController {

    static Stage stage;
    static JSONObject utilizador;
    static UUID utilizadorID;
        
        public static void changeScene(String fxmlFile, String titulo, String email, String password) {
            Parent root = null;
            
            try{
            //root = FXMLLoader.load(ScenesController.class.getResource(fxmlFile));
            FXMLLoader loader = new FXMLLoader(ScenesController.class.getResource(fxmlFile));
            root = loader.load();
    
            } catch(IOException e){
                e.printStackTrace();
            }
            //Stage stage = (Stage) ((Node) evento.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
    }

    public static void setStage(Stage primaryStage){
        stage = primaryStage;
    }

    public static void setUtilizadorID(UUID ID){
        utilizadorID = ID;
    }
    
    public static UUID getUtilizadorID(){
        return utilizadorID;
    }
}