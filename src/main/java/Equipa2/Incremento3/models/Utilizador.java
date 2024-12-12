package Equipa2.Incremento3.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import Equipa2.Incremento3.models.enums.UserType;

import java.io.Serializable;
import java.util.UUID;



/**
 * Classe que representa um utilizador.
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Utilizador implements Serializable {

    private UUID id;

    private String nome;

    private String email;

    private String password;

    private String morada;

    private UserType userType;

    /**
     * Construtor que inicializa um novo utilizador com os dados fornecidos.
     * O ID é gerado automaticamente e aleatoriamente.
     *
     * @param nome     Nome do utilizador.
     * @param email    Email do utilizador.
     * @param password Password do utilizador.
     * @param morada   Morada do utilizador.
     * @param userType Tipo de utilizador.
     */
    public Utilizador(String nome, String email, String password, String morada, UserType userType) {
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.morada = morada;
        this.userType = userType;
    }
    
}
