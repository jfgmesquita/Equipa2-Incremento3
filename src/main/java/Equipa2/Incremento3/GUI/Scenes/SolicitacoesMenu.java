package Equipa2.Incremento3.GUI.Scenes;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import Equipa2.Incremento3.models.Pagamento;
import Equipa2.Incremento3.models.Profissional;
import Equipa2.Incremento3.models.Servico;
import Equipa2.Incremento3.models.dto.PagamentoDTO;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class SolicitacoesMenu implements Initializable {
    @FXML
    private AnchorPane anchorpane_servico;

    @FXML
    private DatePicker date;

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
    private Button button_voltarPraSolicitacao;

    @FXML
    private Button button_cancelar;

    @FXML
    private Button button_comecar;

    @FXML
    private Button button_concluir;

    @FXML
    private Button button_pagar;

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
    private TableColumn<ServicoDTO, String> tc_ServicoDescricao;

    @FXML
    private TableColumn<ServicoDTO, String> tc_ServicoProfissional;

    @FXML
    private TableColumn<ServicoDTO, String> tc_ServicoTipo;

    @FXML
    private TableColumn<ServicoDTO, Double> tc_ServicoValor;

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
    private TextField tf_hora;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        tc_ServicoDescricao.setCellValueFactory(new PropertyValueFactory<ServicoDTO, String>("descricao"));
        tc_ServicoTipo.setCellValueFactory(new PropertyValueFactory<ServicoDTO, String>("tipo"));
        tc_ServicoValor.setCellValueFactory(new PropertyValueFactory<ServicoDTO, Double>("valorHora"));
        //tc_profissional.setCellValueFactory(new PropertyValueFactory<UtilizadorDTO, String>("nome"));
        tc_ServicoProfissional.setCellValueFactory(cellData -> {
            String nome = cellData.getValue().getProfissional().getNome();
            return new SimpleObjectProperty<>(nome);
        });

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
        ObservableList<SolicitacaoDTO> listaSolicitacoes = FXCollections.observableArrayList();
       
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
                solicitacaoDTO.setId(UUID.fromString(objeto.getString("id")));
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
    
                listaSolicitacoes.add(solicitacaoDTO);
             
                
            }
            table_solicitacoes.setItems(listaSolicitacoes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Fim de leitura das Solicitações
        //Ler Servicos
        ObservableList<ServicoDTO> listaDeServicos = FXCollections.observableArrayList();
           
        try {
            String response;
            response = apiService.getData("/servicos");
            JSONArray servicosArray = new JSONArray(response);
            for(int i = 0; i < servicosArray.length(); i++){
                JSONObject objeto = servicosArray.getJSONObject(i);
                JSONObject pro = objeto.getJSONObject("profissional");
                
                ServicoDTO servico = new ServicoDTO();
                servico.setId(UUID.fromString(objeto.getString("id")));
                servico.setDescricao(objeto.getString("descricao"));
                servico.setValorHora(objeto.getDouble("valorHora"));
                servico.setTipo(Servicos.valueOf(objeto.getString("tipo")));

                UtilizadorDTO profissional = new UtilizadorDTO();
                profissional.setNome(pro.getString("nome"));
                servico.setProfissional(profissional);

                
                listaDeServicos.add(servico);
             
                
            }
            table_servicos.setItems(listaDeServicos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //

        table_solicitacoes.setOnMouseClicked(ae -> {
            
            SolicitacaoDTO solicitacao = table_solicitacoes.getSelectionModel().getSelectedItem();
            if(tipoUti.equals("PROFISSIONAL")){
                if(solicitacao.getStatus().toString().equals("PENDENTE")){
                    button_recusar.setVisible(true);
                    button_aceitar.setVisible(true);
                    button_comecar.setVisible(false);
                    button_concluir.setVisible(false);
                    button_cancelar.setVisible(false);
                }else if(solicitacao.getStatus().toString().equals("ACEITE")){
                    button_comecar.setVisible(true);
                    button_cancelar.setVisible(true);
                    button_recusar.setVisible(false);
                    button_aceitar.setVisible(false);
                    button_concluir.setVisible(false);
                }else if(solicitacao.getStatus().toString().equals("ANDAMENTO")){
                    button_cancelar.setVisible(true);
                    button_concluir.setVisible(true);
                    button_recusar.setVisible(false);
                    button_aceitar.setVisible(false);
                    button_comecar.setVisible(false);
                }else if(solicitacao.getStatus().toString().equals("CANCELADO") || solicitacao.getStatus().toString().equals("CONCLUIDO")){
                    button_recusar.setVisible(false);
                    button_aceitar.setVisible(false);
                    button_comecar.setVisible(false);
                    button_cancelar.setVisible(false);
                    button_concluir.setVisible(false);
                }
                } else{
                    if(solicitacao.getStatus().toString().equals("CONCLUIDO")){
                        button_pagar.setVisible(true);
                   } else{
                       button_pagar.setVisible(false);
                   }
                }
        });

        button_voltarPraSolicitacao.setOnAction(ae -> {
            anchorpane_servico.setVisible(false);
            table_solicitacoes.setVisible(true);
            button_voltarPraSolicitacao.setVisible(false);
        });

        button_adicionarSolicitacao.setOnAction(ae ->{
            anchorpane_servico.setVisible(true);
            table_solicitacoes.setVisible(false);
            button_voltarPraSolicitacao.setVisible(true);
        });

        button_concluirSolicitacao.setOnAction(ae ->{
            ServicoDTO servicoDTO = table_servicos.getSelectionModel().getSelectedItem();
            if (servicoDTO == null || servicoDTO.getId() == null) {
                System.out.println("Erro: Serviço não selecionado ou ID do serviço é nulo.");
                return;
            }

            SolicitacaoDTO solicitacaoDTO = new SolicitacaoDTO();
            solicitacaoDTO.setServico(servicoDTO);
            solicitacaoDTO.setStatus(StatusServico.PENDENTE);

            // Formatar a data corretamente

            LocalDate localDate = date.getValue();
            LocalTime localTime = LocalTime.parse(tf_hora.getText());
            LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            String formattedDate = localDateTime.format(dtf);

            System.out.println("Data fornecida: " + formattedDate);

            try {
                LocalDateTime.parse(formattedDate, dtf);
            } catch (DateTimeParseException e) {
                System.out.println("Erro no formato da data. Use 'dd-MM-yyyy HH:mm'.");
                e.printStackTrace();
                return;
            }
            solicitacaoDTO.setData(formattedDate);

            // Obter as informações do cliente a partir da API
            ApiService apiService1 = new ApiService();
            Gson gson = new Gson();
            UtilizadorDTO cliente = null;

            try {
                UUID utilizadorID = ScenesController.getUtilizadorID();
                String response = apiService1.getData("/utilizadores", utilizadorID);
                cliente = gson.fromJson(response, UtilizadorDTO.class);
            } catch (IOException | InterruptedException | JsonSyntaxException e) {
                e.printStackTrace();
            }

            // Adicionar o cliente à solicitação
            if (cliente != null) {
                solicitacaoDTO.setCliente(cliente);
            }

            // Criar um objeto Pagamento
            // PagamentoDTO pagamento = new PagamentoDTO();
            // solicitacaoDTO.setPagamento(pagamento);

            listaSolicitacoes.add(solicitacaoDTO);

            // Enviar a solicitação para a API para persistir
            String json = gson.toJson(solicitacaoDTO);
            System.out.println("JSON enviado: " + json);

            try {
                apiService.postData("/solicitacoes", json);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

            anchorpane_servico.setVisible(false);
            table_solicitacoes.setVisible(true);
            button_voltarPraSolicitacao.setVisible(false);
        });

        button_voltar.setOnAction(ae -> {
            ScenesController.changeScene("/Equipa2/Incremento3/GUI/Fxmls/allaround_menucliente.fxml");
        });

        button_aceitar.setOnAction(ae -> {
            SolicitacaoDTO solicitacao = table_solicitacoes.getSelectionModel().getSelectedItem();
            if(solicitacao == null || solicitacao.getId() == null){
                System.out.println("Erro: Solicitação não selecionada ou ID da solicitação é nulo.");
                return;
            }

            solicitacao.setStatus(StatusServico.ACEITE);
            String json = new Gson().toJson(solicitacao);
            System.out.println("JSON enviado: " + json);
            try {
                String response = apiService.putData("/solicitacoes/" + solicitacao.getId(), json);
                System.out.println("Resposta da API: " + response);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            table_solicitacoes.refresh();
        });

        button_recusar.setOnAction(ae -> {
            SolicitacaoDTO solicitacao = table_solicitacoes.getSelectionModel().getSelectedItem();
            if(solicitacao == null || solicitacao.getId() == null){
                System.out.println("Erro: Solicitação não selecionada ou ID da solicitação é nulo.");
                return;
            }

            solicitacao.setStatus(StatusServico.CANCELADO);
            String json = new Gson().toJson(solicitacao);
            System.out.println("JSON enviado: " + json);
            try {
                String response = apiService.putData("/solicitacoes/" + solicitacao.getId(), json);
                System.out.println("Resposta da API: " + response);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            table_solicitacoes.refresh();
        });

        button_comecar.setOnAction(ae -> {
            SolicitacaoDTO solicitacao = table_solicitacoes.getSelectionModel().getSelectedItem();
            if(solicitacao == null || solicitacao.getId() == null){
                System.out.println("Erro: Solicitação não selecionada ou ID da solicitação é nulo.");
                return;
            }

            solicitacao.setStatus(StatusServico.ANDAMENTO);
            String json = new Gson().toJson(solicitacao);
            System.out.println("JSON enviado: " + json);
            try {
                String response = apiService.putData("/solicitacoes/" + solicitacao.getId(), json);
                System.out.println("Resposta da API: " + response);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            table_solicitacoes.refresh();
        });

        button_concluir.setOnAction(ae -> {
            SolicitacaoDTO solicitacao = table_solicitacoes.getSelectionModel().getSelectedItem();
            if(solicitacao == null || solicitacao.getId() == null){
                System.out.println("Erro: Solicitação não selecionada ou ID da solicitação é nulo.");
                return;
            }

            solicitacao.setStatus(StatusServico.CONCLUIDO);
            String json = new Gson().toJson(solicitacao);
            System.out.println("JSON enviado: " + json);
            try {
                String response = apiService.putData("/solicitacoes/" + solicitacao.getId(), json);
                System.out.println("Resposta da API: " + response);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            table_solicitacoes.refresh();
        });

        button_cancelar.setOnAction(ae -> {
            SolicitacaoDTO solicitacao = table_solicitacoes.getSelectionModel().getSelectedItem();
            if(solicitacao == null || solicitacao.getId() == null){
                System.out.println("Erro: Solicitação não selecionada ou ID da solicitação é nulo.");
                return;
            }

            solicitacao.setStatus(StatusServico.CANCELADO);
            String json = new Gson().toJson(solicitacao);
            System.out.println("JSON enviado: " + json);
            try {
                String response = apiService.putData("/solicitacoes/" + solicitacao.getId(), json);
                System.out.println("Resposta da API: " + response);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            table_solicitacoes.refresh();
        });

        button_pagar.setOnAction(ae -> {
            SolicitacaoDTO solicitacao = table_solicitacoes.getSelectionModel().getSelectedItem();
            if(solicitacao == null || solicitacao.getId() == null){
                System.out.println("Erro: Solicitação não selecionada ou ID da solicitação é nulo.");
                return;
            }

            // Criar um objeto Pagamento
            PagamentoDTO pagamento = new PagamentoDTO();
            pagamento.setSolicitacaoId(solicitacao.getId());
            pagamento.setValor(solicitacao.getServico().getValorHora());
            //pagamento.setValor(solicitacao.getServico().getValorHora() * solicitacao.getServico().getHoras());

            // Enviar o pagamento para a API para persistir
            String json = new Gson().toJson(pagamento);
            System.out.println("JSON enviado: " + json);

            try {
                apiService.postData("/pagamentos", json);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

            // Atualizar o status da solicitação
            solicitacao.setStatus(StatusServico.PAGO);
            String jsonSolicitacao = new Gson().toJson(solicitacao);
            System.out.println("JSON enviado: " + jsonSolicitacao);
            try {
                String response = apiService.putData("/solicitacoes/" + solicitacao.getId(), jsonSolicitacao);
                System.out.println("Resposta da API: " + response);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            table_solicitacoes.refresh();

            // Exibir uma mensagem de sucesso
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pagamento efetuado");
            alert.setHeaderText("Pagamento efetuado com sucesso");
            alert.setContentText("O pagamento foi efetuado com sucesso. O serviço foi concluído.");
            alert.showAndWait();
        });

    }

}
