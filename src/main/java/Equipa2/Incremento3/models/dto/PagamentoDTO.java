package Equipa2.Incremento3.models.dto;

import Equipa2.Incremento3.models.enums.StatusPagamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoDTO {

    private UUID id;

    private double valor;

    private UUID solicitacaoId;

    private UUID clienteId;

    private UUID profissionalId;

    private StatusPagamento status;

    private int horas;
}

