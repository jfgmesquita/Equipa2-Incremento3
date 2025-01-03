package Equipa2.Incremento3.GUI;

import Equipa2.Incremento3.GUI.Scenes.ScenesController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Principal extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		ScenesController.setStage(primaryStage);
		Parent root = FXMLLoader.load(getClass().getResource("Fxmls/allaround.fxml"));

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
	}

}
