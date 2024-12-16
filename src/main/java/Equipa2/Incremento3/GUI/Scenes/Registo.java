package Equipa2.Incremento3.GUI.Scenes;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import Equipa2.Incremento3.models.dto.UtilizadorDTO;
import Equipa2.Incremento3.models.enums.MetodoPagamento;
import Equipa2.Incremento3.models.enums.Servicos;
import Equipa2.Incremento3.models.enums.UserType;
import Equipa2.Incremento3.services.ApiService;

import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONObject;

public class Registo implements Initializable{
    @FXML
    private Button button_finalizarRegisto;

    @FXML
    private Button button_voltarLogin;

    @FXML
    private ChoiceBox<String> choiceBox_metodoPagamento;

    @FXML
    private ChoiceBox<String> choiceBox_servicos;

    @FXML
    private RadioButton rb_cliente;

    @FXML
    private RadioButton rb_profissional;

    @FXML
    private TextField tf_email;

    @FXML
    private TextField tf_morada;

    @FXML
    private TextField tf_nome;

    @FXML
    private TextField tf_experiencia;

    @FXML
    private PasswordField tf_password;

    private ApiService apiService = new ApiService();

    @FXML
    public void initialize(URL location, ResourceBundle resources){


        //Botão de tipo de Utilizador
        ToggleGroup toggleGroup = new ToggleGroup();
        rb_cliente.setToggleGroup(toggleGroup);
        rb_profissional.setToggleGroup(toggleGroup);
        rb_cliente.setSelected(true);

        choiceBox_metodoPagamento.getItems().addAll("MBWAY", "CARTAO_DE_CREDITO","TRANSFERENCIA_BANCARIA","PAYPAL");
        //Solução Temporaria
        choiceBox_servicos.getItems().addAll("COZINHA");

        button_voltarLogin.setOnAction(ae -> {
            Stage stage = (Stage) button_voltarLogin.getScene().getWindow();
            System.out.println("Botão VoltarLogin Apertado");
            ScenesController.changeScene(stage, "/Equipa2/Incremento3/GUI/Fxmls/allaround.fxml", null, null, null);
        });

        rb_profissional.setOnAction(ae -> {
            tf_experiencia.setDisable(false);
            choiceBox_servicos.setDisable(false);
        });

        rb_cliente.setOnAction(ae -> {
            tf_experiencia.setDisable(true);
            choiceBox_servicos.setDisable(true);
        });

        button_finalizarRegisto.setOnAction(ae -> {
            Stage stage = (Stage) button_finalizarRegisto.getScene().getWindow();
            System.out.println("Botão Finalizar Registo Apertado");
            //Dados
            try{
            JSONObject json = new JSONObject();
            json.put("nome", tf_nome.getText());
            json.put("email", tf_email.getText());
            json.put("password", tf_password.getText());
            json.put("morada", tf_morada.getText());
            json.put("userType", ((RadioButton) toggleGroup.getSelectedToggle()).getText().toUpperCase());
            json.put("formaDePagamento", choiceBox_metodoPagamento.getValue());

            json.put("codigo", 0);

            //Cliente Selecionado
            if(((RadioButton) toggleGroup.getSelectedToggle()).getText().equals("Cliente")){
                json.put("especialidade", (Servicos) null);
                json.put("experiencia", 0);
            } 
            else{
            //Profissional Selecionado
                int experiencia;
                json.put("especialidade", choiceBox_servicos.getValue());
                json.put("experiencia", Integer.parseInt(tf_experiencia.getText()));
            }

            String response = apiService.postData("/utilizadores", json.toString());
            ScenesController.changeScene(stage, "/Equipa2/Incremento3/GUI/Fxmls/allaround.fxml", null, null, null);
            
            }catch(Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Não foi possível realizar o registo, verifique se não há campo em branco.");
                alert.show();
                e.printStackTrace();
            }

            
        });

        
		}


    }

