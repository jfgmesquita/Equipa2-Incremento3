package Equipa2.Incremento3.GUI.Scenes;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONObject;

public class ClienteMenu implements Initializable {
    
    @FXML
    private Button button_logOut;

    @FXML
    private Button button_servicos;

    @FXML
    private Button button_solicitar;

    @FXML
    private Button button_verSolicitacoes;

    @FXML
    private Label label_emailCliente;

    @FXML
    private Label label_nomeCliente;

    public void setClienteInfo(String nome, String email, String tipo) {
        label_nomeCliente.setText(nome + " - " + tipo);
        label_emailCliente.setText(email);
        
        if(tipo.equals("CLIENTE")){ 
            button_servicos.setDisable(true); 
            button_solicitar.setDisable(false);}
        else{ 
            button_servicos.setDisable(false); 
            button_solicitar.setDisable(true);}
    }

    @FXML
	public void initialize(URL location, ResourceBundle resources){
    
    JSONObject utilizador = ScenesController.getUtilizador();
    String nome = utilizador.getString("nome");
    String email = utilizador.getString("email");
    String tipo = utilizador.getString("userType");
    setClienteInfo(nome, email, tipo);


    button_servicos.setOnAction(ae -> {
        ScenesController.changeScene("/Equipa2/Incremento3/GUI/Fxmls/allaround_menuProfissionalServicos.fxml", null, null, null);
    });
    
    button_logOut.setOnAction(ae -> {
        ScenesController.changeScene("/Equipa2/Incremento3/GUI/Fxmls/allaround.fxml", null, null, null);
    });
    }
    
}
