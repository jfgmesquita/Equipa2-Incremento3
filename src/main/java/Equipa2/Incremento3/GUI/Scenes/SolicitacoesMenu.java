package Equipa2.Incremento3.GUI.Scenes;



import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import Equipa2.Incremento3.models.Profissional;
import Equipa2.Incremento3.models.Servico;
import Equipa2.Incremento3.models.dto.ServicoDTO;
import Equipa2.Incremento3.models.dto.SolicitacaoDTO;
import Equipa2.Incremento3.models.dto.UtilizadorDTO;
import Equipa2.Incremento3.models.enums.Servicos;
import Equipa2.Incremento3.models.enums.StatusServico;
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

public class SolicitacoesMenu implements Initializable {
    @FXML
    private AnchorPane anchorpane_servico;

    @FXML
    private Button button_adicionarServico;

    @FXML
    private Button button_concluirServico;

    @FXML
    private Button button_voltar;

    @FXML
    private TableColumn<SolicitacaoDTO, String> tc_descricao;

    @FXML
    private TableColumn<SolicitacaoDTO, String> tc_profissional;

    @FXML
    private TableColumn<SolicitacaoDTO, String> tc_tipo;

    @FXML
    private TableColumn<SolicitacaoDTO, Double> tc_valor;
    
    @FXML
    private TableColumn<SolicitacaoDTO, String> tc_status;

    @FXML
    private TableColumn<SolicitacaoDTO, String> tc_data;

    @FXML
    private TableView<SolicitacaoDTO> table_solicitacoes;

    @FXML
    private TextField tf_descServico;

    @FXML
    private TextField tf_tipoServico;

    @FXML
    private TextField tf_valorHoraServico;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        tc_status.setCellValueFactory(new PropertyValueFactory<SolicitacaoDTO, String>("status"));
        tc_data.setCellValueFactory(new PropertyValueFactory<SolicitacaoDTO, String>("data"));

        tc_descricao.setCellValueFactory(cellData -> {
            String descricao = cellData.getValue().getServico().getDescricao();
            return new SimpleObjectProperty<>(descricao);
        });
        tc_tipo.setCellValueFactory(cellData -> {
            String tipo = cellData.getValue().getServico().getTipo().toString();
            return new SimpleObjectProperty<>(tipo);
        });
        tc_valor.setCellValueFactory(cellData -> {
            Double valor = cellData.getValue().getServico().getValorHora();
            return new SimpleObjectProperty<>(valor);
        });
        //tc_profissional.setCellValueFactory(new PropertyValueFactory<UtilizadorDTO, String>("nome"));
        tc_profissional.setCellValueFactory(cellData -> {
            String nome = cellData.getValue().getCliente().getNome();
            return new SimpleObjectProperty<>(nome);
        });

        ApiService apiService = new ApiService(); 
        ObservableList<SolicitacaoDTO> listaServicos = FXCollections.observableArrayList();
       
        try {
            String response;
            response = apiService.getData("/solicitacoes/utilizador/" + ScenesController.getUtilizadorID());
            JSONArray servicosArray = new JSONArray(response);
            for(int i = 0; i < servicosArray.length(); i++){
                JSONObject objeto = servicosArray.getJSONObject(i);
                JSONObject cli = objeto.getJSONObject("cliente");
                JSONObject servi = objeto.getJSONObject("servico");
                
                SolicitacaoDTO solicitacaoDTO = new SolicitacaoDTO();
                solicitacaoDTO.setData(objeto.getString("data"));
                solicitacaoDTO.setStatus(StatusServico.valueOf(objeto.getString("status")));

                ServicoDTO servico = new ServicoDTO();
                servico.setDescricao(servi.getString("descricao"));
                servico.setValorHora(servi.getDouble("valorHora"));
                servico.setTipo(Servicos.valueOf(servi.getString("tipo")));

                UtilizadorDTO cliente = new UtilizadorDTO();
                cliente.setNome(cli.getString("nome"));
                solicitacaoDTO.setCliente(cliente);

                solicitacaoDTO.setServico(servico);
    
                listaServicos.add(solicitacaoDTO);
             
                
            }
            table_solicitacoes.setItems(listaServicos);
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        //JSONArray servicosArray = ScenesController.getUtilizador().getJSONArray("servicos");
        //String nomeProfissional = ScenesController.getUtilizador().getString("nome");

       

        button_adicionarServico.setOnAction(ae ->{
            anchorpane_servico.setVisible(true);
        });

        button_concluirServico.setOnAction(ae ->{
           // ServicoDTO servico = new ServicoDTO();
          //  servico.setDescricao(tf_descServico.getText());
           // servico.setTipo(Servicos.valueOf(tf_tipoServico.getText()));
           // Double valor = Double.parseDouble(tf_valorHoraServico.getText());
            //servico.setValorHora(valor);
            //listaServicos.add(servico);
            //falta adicionar à base de dados

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
