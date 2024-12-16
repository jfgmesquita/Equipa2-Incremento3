package Equipa2.Incremento3.GUI.Scenes;

import Equipa2.Incremento3.services.ApiService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {

    @FXML
    private Button button_login;

    @FXML
    private Button button_telaRegistar;

    @FXML
    private TextField tf_email;

    @FXML
    private PasswordField tf_password;

    private ApiService apiService = new ApiService();

	@FXML
	public void initialize(URL location, ResourceBundle resources){

		button_login.setOnAction(ae -> {
            Stage stage = (Stage) button_login.getScene().getWindow();
            System.out.println("Botão \"Entrar\" apertado.");

            try {
                JSONObject json = new JSONObject();
                json.put("email", tf_email.getText());
                json.put("password", tf_password.getText());

                String response = apiService.postData("/auth", json.toString());

                //
                JSONObject jsonResponse = new JSONObject(response);
                String nomeCliente = jsonResponse.getString("nome");
                String emailCliente = jsonResponse.getString("email");

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Equipa2/Incremento3/GUI/Fxmls/allaround_menucliente.fxml"));

                Parent root = loader.load();
                ClienteMenu clienteMenuController = loader.getController();
                clienteMenuController.setClienteInfo(nomeCliente, emailCliente);

                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                //

                //System.out.println(response);
                //ScenesController.changeScene(stage, "/Equipa2/Incremento3/GUI/Fxmls/allaround_menucliente.fxml", null, null, null);
            } catch (Exception e) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("As credênciais estão erradas, tente novamente.");
                alert.show();
                e.printStackTrace();
                // Handle the error and show a message to the user
            }
        });

        button_telaRegistar.setOnAction(ae -> {
            Stage stage = (Stage) button_telaRegistar.getScene().getWindow();
            System.out.println("Botão Registar Apertado");
            ScenesController.changeScene(stage, "/Equipa2/Incremento3/GUI/Fxmls/allaround_menuRegisto.fxml", null, null, null);
        });

		}
	}
	


