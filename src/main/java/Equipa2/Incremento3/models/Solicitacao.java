package Equipa2.Incremento3.models;

import java.util.UUID;
import java.time.LocalDateTime;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import Equipa2.Incremento3.models.enums.StatusServico;

/**
 * Classe que representa a Solicitação do serviço.
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Solicitacao {

    private UUID id;

    private StatusServico status;

    private Cliente cliente;

    private Profissional profissional;

    private Servico servico;

    private Pagamento pagamento;

    private LocalDateTime data;

    /**
     * Construtor que inicializa uma nova solicitação com os dados fornecidos.
     *
     * @param status       Status do serviço.
     * @param cliente      Cliente que solicitou o serviço.
     * @param profissional Profissional que realizará o serviço.
     * @param servico      Serviço solicitado.
     * @param pagamento    Pagamento associado à solicitação.
     * @param data         Data da solicitação.
     */
    public Solicitacao(StatusServico status, Cliente cliente, Profissional profissional, Servico servico, Pagamento pagamento, LocalDateTime data) {
        this.status = status;
        this.cliente = cliente;
        this.profissional = profissional;
        this.servico = servico;
        this.pagamento = pagamento;
        this.data = data;
    }
    
}
