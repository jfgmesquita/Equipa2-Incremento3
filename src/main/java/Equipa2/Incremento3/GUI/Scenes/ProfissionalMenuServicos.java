package Equipa2.Incremento3.GUI.Scenes;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import com.google.gson.Gson;

import Equipa2.Incremento3.models.Profissional;
import Equipa2.Incremento3.models.Servico;
import Equipa2.Incremento3.models.dto.ServicoDTO;
import Equipa2.Incremento3.models.dto.UtilizadorDTO;
import Equipa2.Incremento3.models.enums.Servicos;
import Equipa2.Incremento3.services.ApiService;
import javafx.beans.property.SimpleObjectProperty;
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
//import lombok.launch.PatchFixesHider.Util;

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
    private TableColumn<ServicoDTO, String> tc_descricao;

    @FXML
    private TableColumn<ServicoDTO, String> tc_profissional;

    @FXML
    private TableColumn<ServicoDTO, String> tc_tipo;

    @FXML
    private TableColumn<ServicoDTO, Double> tc_valor;
    
    @FXML
    private TableView<ServicoDTO> table_servicos;

    @FXML
    private TextField tf_descServico;

    @FXML
    private TextField tf_tipoServico;

    @FXML
    private TextField tf_valorHoraServico;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        tc_descricao.setCellValueFactory(new PropertyValueFactory<ServicoDTO, String>("descricao"));
        tc_tipo.setCellValueFactory(new PropertyValueFactory<ServicoDTO, String>("tipo"));
        tc_valor.setCellValueFactory(new PropertyValueFactory<ServicoDTO, Double>("valorHora"));
        //tc_profissional.setCellValueFactory(new PropertyValueFactory<UtilizadorDTO, String>("nome"));
        tc_profissional.setCellValueFactory(cellData -> {
            String nome = cellData.getValue().getProfissional().getNome();
            return new SimpleObjectProperty<>(nome);
        });

        ApiService apiService = new ApiService(); 
        ObservableList<ServicoDTO> listaServicos = FXCollections.observableArrayList();
       
        try {
            String response;
            response = apiService.getData("/servicos/profissional/" + ScenesController.getUtilizadorID());
            JSONArray servicosArray = new JSONArray(response);
            for(int i = 0; i < servicosArray.length(); i++){
                JSONObject objeto = servicosArray.getJSONObject(i);
                JSONObject pro = objeto.getJSONObject("profissional");
                
                ServicoDTO servico = new ServicoDTO();
                servico.setDescricao(objeto.getString("descricao"));
                servico.setValorHora(objeto.getDouble("valorHora"));
                servico.setTipo(Servicos.valueOf(objeto.getString("tipo")));

                UtilizadorDTO profissional = new UtilizadorDTO();
                profissional.setNome(pro.getString("nome"));
                servico.setProfissional(profissional);

                listaServicos.add(servico);
            }
            
            table_servicos.setItems(listaServicos);
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        //JSONArray servicosArray = ScenesController.getUtilizador().getJSONArray("servicos");
        //String nomeProfissional = ScenesController.getUtilizador().getString("nome");

        button_adicionarServico.setOnAction(ae ->{
            anchorpane_servico.setVisible(true);
        });

        button_concluirServico.setOnAction(ae ->{
            // Criar um novo serviço
            ServicoDTO servico = new ServicoDTO();
            servico.setDescricao(tf_descServico.getText());
            servico.setTipo(Servicos.valueOf(tf_tipoServico.getText()));
            Double valor = Double.parseDouble(tf_valorHoraServico.getText());
            servico.setValorHora(valor);

            // Obter as informações do profissional a partir da API
            ApiService apiService1 = new ApiService();
            Gson gson = new Gson();
            UtilizadorDTO profissional = null;
            
            // Obter o profissional
            try {
                String response = apiService1.getData("/utilizadores/" + ScenesController.getUtilizadorID());
                JSONObject profissionalJson = new JSONObject(response);
                profissional = gson.fromJson(profissionalJson.toString(), UtilizadorDTO.class);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

            // Adicionar o profissional ao serviço
            if(profissional != null){
                servico.setProfissional(profissional);
            }
            
            // Adicionar o serviço à lista de serviços
            listaServicos.add(servico);
            
            // Enviar o serviço para a API para persistir
            String json = gson.toJson(servico);
            try {
                apiService1.postData("/servicos", json);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

            // Limpar os campos
            tf_descServico.setText("");
            tf_tipoServico.setText("");
            tf_valorHoraServico.setText("");
            anchorpane_servico.setVisible(false);
        });

        button_voltar.setOnAction(ae -> {
            ScenesController.changeScene("/Equipa2/Incremento3/GUI/Fxmls/allaround_menucliente.fxml");
        });

    }

}
