package Equipa2.Incremento2.GUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import Equipa2.Incremento2.Incremento2Application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Principal extends Application{
	@Autowired
	public ConfigurableApplicationContext applicationContext;

	@Override
	public void init() throws Exception{
		applicationContext = SpringApplication.run(Incremento2Application.class);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("Fxmls/allaround.fxml"));
		loader.setControllerFactory(applicationContext::getBean);
		Parent root = loader.load();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
	}

}
