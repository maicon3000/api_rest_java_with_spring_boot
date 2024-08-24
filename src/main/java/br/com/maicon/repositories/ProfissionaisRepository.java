package br.com.maicon.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.maicon.model.Profissionais;

@Repository
public interface ProfissionaisRepository extends JpaRepository<Profissionais, Long> {
    Optional<Profissionais> findById(Long id);
}