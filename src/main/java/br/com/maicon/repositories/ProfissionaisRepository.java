package br.com.maicon.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.maicon.models.Profissionais;

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
 * 
 * <b>Métodos Personalizados:</b>
 * <ul>
 *   <li>{@link #findAllActive()}: Retorna uma lista de todos os profissionais ativos (não deletados).</li>
 *   <li>{@link #findByIdAndActive(Long)}: Retorna um profissional específico pelo seu ID, desde que ele não esteja deletado.</li>
 *   <li>{@link #findByQuery(String)}: Retorna uma lista de profissionais cujos nomes, cargos ou datas de nascimento correspondam ao termo de pesquisa fornecido, e que não estejam deletados.</li>
 * </ul>
 *
 * @see JpaRepository
 * @see Profissionais
 * 
 * @author Maicon
 * @version 1.0
 */
@Repository
public interface ProfissionaisRepository extends JpaRepository<Profissionais, Long> {
	
    /**
     * Retorna uma lista de todos os profissionais que não foram deletados.
     * 
     * <p>Este método realiza uma consulta no banco de dados para buscar todos os registros 
     * de profissionais cuja coluna {@code deleted} seja falsa. O resultado é ordenado pelo campo {@code id}.</p>
     * 
     * @return Uma lista de profissionais ativos (não deletados).
     */
    @Query("SELECT p FROM Profissionais p WHERE p.deleted <> true ORDER BY id")
    List<Profissionais> findAllActive();

    /**
     * Retorna um profissional específico pelo seu ID, desde que ele não esteja deletado.
     * 
     * <p>Este método busca um profissional no banco de dados pelo seu ID, garantindo que o registro
     * não esteja marcado como deletado. Se o profissional não for encontrado ou estiver deletado,
     * o resultado será um {@link Optional} vazio.</p>
     * 
     * @param id O ID do profissional a ser buscado.
     * @return Um {@link Optional} contendo o profissional encontrado, ou vazio se não encontrado.
     */
    @Query("SELECT p FROM Profissionais p WHERE p.id = :id AND p.deleted <> true")
    Optional<Profissionais> findByIdAndActive(@Param("id") Long id);

    /**
     * Realiza uma busca por profissionais com base em um termo de pesquisa.
     * 
     * <p>Este método procura profissionais cujos nomes, cargos ou datas de nascimento contenham
     * o termo de pesquisa fornecido. A busca é insensível a maiúsculas/minúsculas e os resultados
     * são filtrados para excluir registros deletados.</p>
     * 
     * @param q O termo de pesquisa a ser usado para a busca.
     * @return Uma lista de profissionais que correspondam ao termo de pesquisa fornecido.
     */
	@Query("SELECT p FROM Profissionais p WHERE "
		     + "LOWER(p.nome) LIKE LOWER(CONCAT('%', :q, '%')) "
		     + "OR LOWER(p.cargo) LIKE LOWER(CONCAT('%', :q, '%'))"
		     + "OR TO_CHAR(p.nascimento, 'YYYY-MM-DD') LIKE CONCAT('%', :q, '%')"
		     + "AND p.deleted <> true ORDER BY p.id")
	List<Profissionais> findByQuery(@Param("q") String q);
}