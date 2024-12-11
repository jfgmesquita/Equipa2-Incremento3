package Equipa2.Incremento2.GUI.Scenes;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import Equipa2.Incremento2.controller.UtilizadorController;
import Equipa2.Incremento2.model.dto.UtilizadorDTO;
import Equipa2.Incremento2.model.enums.MetodoPagamento;
import Equipa2.Incremento2.model.enums.UserType;

@Component
public class Registo implements Initializable{
    @Autowired
    private ConfigurableApplicationContext applicationContext;
    
    @Autowired
    private UtilizadorController utilizadorController;

    @FXML
    private Button button_finalizarRegisto;

    @FXML
    private Button button_voltarLogin;

    @FXML
    private ChoiceBox<String> choiceBox_metodoPagamento;

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
    private PasswordField tf_password;

    @FXML
    public void initialize(URL location, ResourceBundle resources){


        //Botão de tipo de Utilizador
        ToggleGroup toggleGroup = new ToggleGroup();
        rb_cliente.setToggleGroup(toggleGroup);
        rb_profissional.setToggleGroup(toggleGroup);
        rb_cliente.setSelected(true);

        choiceBox_metodoPagamento.getItems().addAll("MBWAY", "CARTAO_DE_CREDITO","TRANSFERENCIA_BANCARIA","PAYPAL");

        button_voltarLogin.setOnAction(ae -> {
            Stage stage = (Stage) button_voltarLogin.getScene().getWindow();
            System.out.println("Botão VoltarLogin Apertado");
            ScenesController.changeScene(stage, "/Equipa2/Incremento2/GUI/Fxmls/allaround.fxml", null, null, null, applicationContext);
        });

        button_finalizarRegisto.setOnAction(ae -> {
            Stage stage = (Stage) button_finalizarRegisto.getScene().getWindow();
            System.out.println("Botão Finalizar Registo Apertado");
            //Dados
            String tipo = ((RadioButton) toggleGroup.getSelectedToggle()).getText();
            String metodoPagamento = choiceBox_metodoPagamento.getValue();
            String nome = tf_nome.getText();
            String email = tf_email.getText();
            String password = tf_password.getText();
            String morada = tf_morada.getText();

            
            UtilizadorDTO novoCliente = new UtilizadorDTO(nome, email, password, morada, UserType.valueOf(tipo.toUpperCase()), MetodoPagamento.valueOf(metodoPagamento));
            utilizadorController.createUtilizador(novoCliente);
            
            ScenesController.changeScene(stage, "/Equipa2/Incremento2/GUI/Fxmls/allaround.fxml", null, null, null, applicationContext);
        });

        
		}


    }

