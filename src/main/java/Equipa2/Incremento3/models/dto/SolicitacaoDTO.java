package Equipa2.Incremento3.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import Equipa2.Incremento3.models.enums.StatusServico;

import java.util.UUID;


/**
 * DTO para a solicitação, usado para transferir dados através da API.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SolicitacaoDTO {

    private UUID id;

    private StatusServico status;

    private UtilizadorDTO cliente;

    private ServicoDTO servico;

    private String data;


}
