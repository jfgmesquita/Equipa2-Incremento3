package Equipa2.Incremento3.GUI.Scenes;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable{
    @FXML
    private Button button_login;

    @FXML
    private Button button_telaRegistar;

    @FXML
    private TextField tf_email;

    @FXML
    private PasswordField tf_password;

	@FXML
	public void initialize(URL location, ResourceBundle resources){

		button_login.setOnAction(ae -> {
            Stage stage = (Stage) button_login.getScene().getWindow();
            System.out.println("Botão Entrar Apertado");
            ScenesController.changeScene(stage, "/Equipa2/Incremento3/GUI/Fxmls/allaround_menucliente.fxml", null, null, null);
        });

        button_telaRegistar.setOnAction(ae -> {
            Stage stage = (Stage) button_telaRegistar.getScene().getWindow();
            System.out.println("Botão Registar Apertado");
            ScenesController.changeScene(stage, "/Equipa2/Incremento3/GUI/Fxmls/allaround_menuRegisto.fxml", null, null, null);
        });




		}
	}
	


