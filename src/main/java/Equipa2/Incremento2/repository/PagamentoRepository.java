package Equipa2.Incremento2.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Equipa2.Incremento2.model.Pagamento;

/**
 * Repositório para a entidade Pagamento.
 * Fornece operações de acesso a dados para a entidade Pagamento.
 */
@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, UUID> {}
