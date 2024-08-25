package br.com.maicon.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.maicon.model.Profissionais;

/**
 * Interface responsável por fornecer métodos de acesso ao banco de dados
 * para a entidade {@link Profissionais}. Esta interface herda as funcionalidades
 * básicas de CRUD (Create, Read, Update, Delete) da interface {@link JpaRepository}.
 * <p>
 * O {@code ProfissionaisRepository} é marcado com {@link Repository} para indicar que
 * é um componente Spring responsável pela interação com a camada de persistência.
 * A implementação dos métodos desta interface é gerada automaticamente pelo
 * Spring Data JPA, facilitando a manipulação dos dados relacionados à entidade
 * {@link Profissionais}.
 * </p>
 *
 * <p>
 * <b>Notas de Uso:</b>
 * <ul>
 * <li>Os métodos padrão do {@link JpaRepository} como {@code findAll()}, {@code findById(Long id)},
 * {@code save(Profissionais profissional)}, entre outros, já estão disponíveis para uso imediato.</li>
 * <li>Métodos personalizados podem ser adicionados conforme necessário para suportar
 * requisitos de consulta específicos da aplicação.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <b>Exemplos de Uso:</b>
 * <pre>{@code
 * // Exemplo de injeção de dependência
 * @Autowired
 * private ProfissionaisRepository profissionaisRepository;
 *
 * // Exemplo de uso de métodos padrão
 * List<Profissionais> allProfessionals = profissionaisRepository.findAll();
 * Optional<Profissionais> professional = profissionaisRepository.findById(1L);
 * Profissionais savedProfessional = profissionaisRepository.save(new Profissionais(...));
 * }</pre>
 * </p>
 *
 * @see JpaRepository
 * @see Profissionais
 */
@Repository
public interface ProfissionaisRepository extends JpaRepository<Profissionais, Long> {
	
    @Query("SELECT p FROM Profissionais p WHERE p.deleted = false")
    List<Profissionais> findAllActive();

    @Query("SELECT p FROM Profissionais p WHERE p.id = :id AND p.deleted = false")
    Optional<Profissionais> findByIdAndActive(@Param("id") Long id);
}