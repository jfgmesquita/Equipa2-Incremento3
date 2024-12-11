package Equipa2.Incremento2.GUI.Scenes;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;


public class ClienteMenu implements Initializable {
    @Autowired
	public ConfigurableApplicationContext applicationContext;
    
    @FXML
    private Button button_logOut;

    @FXML
    private Button button_solicitar;

    @FXML
    private Button button_verSolicitacoes;

    @FXML
    private Label label_emailCliente;

    @FXML
    private Label label_nomeCliente;

    @FXML
	public void initialize(URL location, ResourceBundle resources){

    button_logOut.setOnAction(ae -> {
        Stage stage = (Stage) button_logOut.getScene().getWindow();
        ScenesController.changeScene(stage, "/Equipa2/Incremento2/GUI/Fxmls/allaround.fxml", null, null, null, applicationContext);
    });
    }
}
