package Equipa2.Incremento3.models;

import java.util.UUID;


import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import Equipa2.Incremento3.models.enums.MetodoPagamento;
import Equipa2.Incremento3.models.enums.StatusPagamento;

/**
 * Classe que representa um pagamento.
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Pagamento {

    private UUID id;

    private double valor;

    private Cliente origemCliente;

    private Profissional destinoProfissional;

    private MetodoPagamento metodo;

    private StatusPagamento status;

    /**
     * Construtor que inicializa um pagamento com os dados fornecidos.
     *
     * @param valor o valor do pagamento
     * @param origemCliente o cliente que realiza o pagamento
     * @param destinoProfissional o profissional que recebe o pagamento
     * @param metodo o método de pagamento utilizado
     */
    public Pagamento(double valor, Cliente origemCliente, Profissional destinoProfissional, MetodoPagamento metodo) {
        this.valor = valor;
        this.origemCliente = origemCliente;
        this.destinoProfissional = destinoProfissional;
        this.metodo = metodo;
        status = StatusPagamento.PENDENTE;
    }
	
}
