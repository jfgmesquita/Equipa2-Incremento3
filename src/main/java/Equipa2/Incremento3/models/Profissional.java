package Equipa2.Incremento3.models;

import java.util.List;
import java.util.ArrayList;


import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import Equipa2.Incremento3.models.enums.MetodoPagamento;
import Equipa2.Incremento3.models.enums.Servicos;
import Equipa2.Incremento3.models.enums.UserType;

/**
 * A classe Profissional representa um utilizador que é um profissional com especialidade, experiência e valor por hora.
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Profissional extends Utilizador {

    private Servicos especialidade;

    private int experiencia;

    private MetodoPagamento formaDePagamento;

    private List<Servico> servicos;

    private List<Solicitacao> solicitacoes;

    private List<Avaliacao> avaliacoes;

    /**
     * Construtor que inicializa um profissional com os dados fornecidos.
     *
     * @param nome o nome do profissional
     * @param email o email do profissional
     * @param password a palavra-passe do profissional
     * @param morada a morada do profissional
     * @param especialidade a especialidade do profissional
     * @param experiencia a experiência do profissional
     * @param formaDePagamento a forma de pagamento do profissional
     */
    public Profissional(String nome, String email, String password, String morada, Servicos especialidade, int experiencia, MetodoPagamento formaDePagamento) {
        super(nome, email, password, morada, UserType.PROFISSIONAL);
        this.especialidade = especialidade;
        this.experiencia = experiencia;
        this.formaDePagamento = formaDePagamento;
        servicos = new ArrayList<>();
        solicitacoes = new ArrayList<>();
        avaliacoes = new ArrayList<>();
    }
    
}
