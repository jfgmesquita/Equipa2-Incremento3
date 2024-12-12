package Equipa2.Incremento3.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import Equipa2.Incremento3.models.enums.Servicos;

import java.util.UUID;


/**
 * DTO para o serviço, usado para transferir dados através da API.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServicoDTO {

    private UUID id;
    
    private Servicos tipo;

    private String descricao;

    private String data;

    private double valorHora;

    private UtilizadorDTO profissional;

}
