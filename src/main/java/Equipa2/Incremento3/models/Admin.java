package Equipa2.Incremento3.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import Equipa2.Incremento3.models.enums.UserType;

/**
 * Classe que representa um administrador.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Admin extends Utilizador {

    private String codigo;

    /**
     * Construtor que inicializa um administrador com os dados fornecidos.
     *
     * @param nome o nome do administrador
     * @param email o email do administrador
     * @param password a palavra-passe do administrador
     * @param morada a morada do administrador
     * @param userType o tipo de utilizador (administrador)
     * @param codigo o código do administrador
     */
    public Admin(String nome, String email, String password, String morada, UserType userType, String codigo) {
        super(nome, email, password, morada, userType);
        this.codigo = codigo;
    }

}
