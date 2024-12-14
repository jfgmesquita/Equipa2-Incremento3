package Equipa2.Incremento3.GUI.Scenes;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class ClienteMenu implements Initializable {
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

    public void setClienteInfo(String nome, String email) {
        label_nomeCliente.setText(nome);
        label_emailCliente.setText(email);
    }

    @FXML
	public void initialize(URL location, ResourceBundle resources){

    button_logOut.setOnAction(ae -> {
        Stage stage = (Stage) button_logOut.getScene().getWindow();
        ScenesController.changeScene(stage, "/Equipa2/Incremento3/GUI/Fxmls/allaround.fxml", null, null, null);
    });
    }
}
