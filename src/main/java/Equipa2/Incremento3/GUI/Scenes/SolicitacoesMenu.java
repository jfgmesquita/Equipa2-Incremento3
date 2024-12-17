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
    private Button button_adicionarSolicitacao;

    @FXML
    private Button button_concluirSolicitacao;

    @FXML
    private Button button_voltar;
    
    @FXML
    private Button button_recusar;

    @FXML
    private Button button_aceitar;

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
    private TableView<ServicoDTO> table_servicos;

    @FXML
    private TextField tf_descServico;

    @FXML
    private TextField tf_tipoServico;

    @FXML
    private TextField tf_valorHoraServico;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        JSONObject utilizador = null;
        //Ler Utilizador
        ApiService apiService = new ApiService();
        try{

        utilizador = new JSONObject(apiService.getData("/utilizadores/" + ScenesController.getUtilizadorID()));
       // tipoUti = utilizador.getString("userType");

        }catch(Exception e){
            e.printStackTrace();
        }
        final String tipoUti = utilizador.getString("userType");
        //
        //Ler Solicitacoes da BD
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
        if(tipoUti.equals("PROFISSIONAL")){
            button_adicionarSolicitacao.setVisible(false);
        tc_profissional.setText("Cliente");
        tc_profissional.setCellValueFactory(cellData -> {
            String nome = cellData.getValue().getCliente().getNome();
            return new SimpleObjectProperty<>(nome);
        });
    }else{
            button_adicionarSolicitacao.setVisible(true);
        tc_profissional.setText("Profissional");
        tc_profissional.setCellValueFactory(cellData -> {
            String nome = cellData.getValue().getServico().getProfissional().getNome();
            return new SimpleObjectProperty<>(nome);
    });
    }
        ObservableList<SolicitacaoDTO> listaServicos = FXCollections.observableArrayList();
       
        try {
            String response;
            response = apiService.getData("/solicitacoes/utilizador/" + ScenesController.getUtilizadorID());
            JSONArray servicosArray = new JSONArray(response);
            for(int i = 0; i < servicosArray.length(); i++){
                JSONObject objeto = servicosArray.getJSONObject(i);
                JSONObject cli = objeto.getJSONObject("cliente");
               
                JSONObject servi = objeto.getJSONObject("servico");
                JSONObject pro = servi.getJSONObject("profissional");
                
                SolicitacaoDTO solicitacaoDTO = new SolicitacaoDTO();
                solicitacaoDTO.setData(objeto.getString("data"));
                solicitacaoDTO.setStatus(StatusServico.valueOf(objeto.getString("status")));

                ServicoDTO servico = new ServicoDTO();
                servico.setDescricao(servi.getString("descricao"));
                servico.setValorHora(servi.getDouble("valorHora"));
                servico.setTipo(Servicos.valueOf(servi.getString("tipo")));
                
                UtilizadorDTO profissional = new UtilizadorDTO();
                profissional.setNome(pro.getString("nome"));
                servico.setProfissional(profissional);

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
        //Fim de leitura das Solicitações

        table_solicitacoes.setOnMouseClicked(ae -> {
            SolicitacaoDTO solicitacao = table_solicitacoes.getSelectionModel().getSelectedItem();
            if(tipoUti.equals("PROFISSIONAL")){
                if(solicitacao.getStatus().toString().equals("PENDENTE")){
                    button_recusar.setVisible(true);
                    button_aceitar.setVisible(true);
                } else{
                    button_recusar.setVisible(false);
                    button_aceitar.setVisible(false);
                }
            }
        });

        button_adicionarSolicitacao.setOnAction(ae ->{
            anchorpane_servico.setVisible(true);
        });

        button_concluirSolicitacao.setOnAction(ae ->{

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
