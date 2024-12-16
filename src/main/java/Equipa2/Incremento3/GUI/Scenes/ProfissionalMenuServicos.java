package Equipa2.Incremento3.GUI.Scenes;



import java.net.URL;

import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import Equipa2.Incremento3.models.Profissional;
import Equipa2.Incremento3.models.Servico;
import Equipa2.Incremento3.models.enums.Servicos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class ProfissionalMenuServicos implements Initializable {
    @FXML
    private AnchorPane anchorpane_servico;

    @FXML
    private Button button_adicionarServico;

    @FXML
    private Button button_concluirServico;

    @FXML
    private Button button_voltar;

    @FXML
    private TableColumn<Servico, String> tc_descricao;

    @FXML
    private TableColumn<Servico, String> tc_profissional;

    @FXML
    private TableColumn<Servico, String> tc_tipo;

    @FXML
    private TableColumn<Servico, Double> tc_valor;
    
    @FXML
    private TableView<Servico> table_servicos;

    @FXML
    private TextField tf_descServico;

    @FXML
    private TextField tf_tipoServico;

    @FXML
    private TextField tf_valorHoraServico;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        tc_descricao.setCellValueFactory(new PropertyValueFactory<Servico, String>("descricao"));
        tc_tipo.setCellValueFactory(new PropertyValueFactory<Servico, String>("tipo"));
        tc_valor.setCellValueFactory(new PropertyValueFactory<Servico, Double>("valorHora"));
        tc_profissional.setCellValueFactory(new PropertyValueFactory<Servico, String>("profissional"));
        
        JSONArray servicosArray = ScenesController.getUtilizador().getJSONArray("servicos");
        //String nomeProfissional = ScenesController.getUtilizador().getString("nome");

        ObservableList<Servico> listaServicos = FXCollections.observableArrayList();
        for(int i = 0; i < servicosArray.length(); i++){
            JSONObject objeto = servicosArray.getJSONObject(i);

            String descricao = objeto.getString("descricao");
            String tipo = objeto.getString("tipo");
            Double valor = objeto.getDouble("valorHora");
            
            Servico servico = new Servico();
            servico.setDescricao(descricao);
            servico.setValorHora(valor);
            servico.setTipo(Servicos.valueOf(tipo));
            

            listaServicos.add(servico);
        }
        
        table_servicos.setItems(listaServicos);

        button_adicionarServico.setOnAction(ae ->{
            anchorpane_servico.setVisible(true);
        });

        button_concluirServico.setOnAction(ae ->{
            Servico servico = new Servico();
            servico.setDescricao(tf_descServico.getText());
            servico.setTipo(Servicos.valueOf(tf_tipoServico.getText()));
            Double valor = Double.parseDouble(tf_valorHoraServico.getText());
            servico.setValorHora(valor);
            listaServicos.add(servico);
            //falta adicionar à base de dados

            tf_descServico.setText("");
            tf_tipoServico.setText("");
            tf_valorHoraServico.setText("");
            anchorpane_servico.setVisible(false);
        });

        button_voltar.setOnAction(ae -> {
            ScenesController.changeScene("/Equipa2/Incremento3/GUI/Fxmls/allaround_menucliente.fxml", null, null, null);
        });

    }

}
