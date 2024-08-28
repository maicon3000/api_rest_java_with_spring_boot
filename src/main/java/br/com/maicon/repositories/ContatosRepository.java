package br.com.maicon.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.maicon.models.Contatos;

/**
 * Interface responsável por fornecer métodos de acesso ao banco de dados
 * para a entidade {@link Contatos}. Esta interface herda as funcionalidades
 * básicas de CRUD (Create, Read, Update, Delete) da interface {@link JpaRepository}.
 * <p>
 * O {@code ContatosRepository} é marcado com {@link Repository} para indicar que
 * é um componente Spring responsável pela interação com a camada de persistência.
 * A implementação dos métodos desta interface é gerada automaticamente pelo
 * Spring Data JPA, facilitando a manipulação dos dados relacionados à entidade
 * {@link Contatos}.
 * </p>
 *
 * <p>
 * <b>Notas de Uso:</b>
 * <ul>
 * <li>Os métodos padrão do {@link JpaRepository} como {@code findAll()}, {@code findById(Long id)},
 * {@code save(Contatos contato)}, entre outros, já estão disponíveis para uso imediato.</li>
 * <li>Métodos personalizados podem ser adicionados conforme necessário para suportar
 * requisitos de consulta específicos da aplicação.</li>
 * </ul>
 * 
 * <b>Métodos Personalizados:</b>
 * <ul>
 *   <li>{@link #findByQuery(String)}: Retorna uma lista de contatos cujos nomes, informações de contato ou IDs de profissionais correspondam ao termo de pesquisa fornecido.</li>
 * </ul>
 *
 * @see JpaRepository
 * @see Contatos
 * 
 * @author Maicon
 * @version 1.0
 */
@Repository
public interface ContatosRepository extends JpaRepository<Contatos, Long> {

    /**
     * Retorna uma lista de todos os contatos que não foram deletados logicamente.
     * 
     * <p>Este método realiza uma consulta no banco de dados para buscar todos os registros 
     * de contatos onde a coluna {@code deletedProfissional} seja falsa. O resultado da consulta
     * é ordenado pelo campo {@code id} em ordem crescente.</p>
     * 
     * @return Uma lista de contatos ativos (não deletados logicamente).
     */
    @Query("SELECT c FROM Contatos c WHERE c.deletedProfissional <> true ORDER BY id")
    List<Contatos> findAllActive();

    /**
     * Retorna um contato específico pelo seu ID, desde que não esteja deletado logicamente.
     * 
     * <p>Este método realiza uma consulta no banco de dados para buscar um contato pelo seu ID,
     * garantindo que o registro não esteja marcado como deletado logicamente 
     * (ou seja, onde {@code deletedProfissional} seja falso). Se o contato não for encontrado ou estiver deletado,
     * o método retorna um {@link Optional} vazio.</p>
     * 
     * @param id O ID do contato a ser buscado.
     * @return Um {@link Optional} contendo o contato encontrado, ou vazio se não encontrado.
     */
    @Query("SELECT c FROM Contatos c WHERE c.id = :id AND c.deletedProfissional <> true")
    Optional<Contatos> findByIdAndActive(@Param("id") Long id);
    
    /**
     * Realiza uma busca por contatos com base em um termo de pesquisa.
     * 
     * <p>Este método procura contatos cujos nomes, informações de contato ou IDs de profissionais contenham
     * o termo de pesquisa fornecido. A busca é insensível a maiúsculas/minúsculas e os resultados
     * são ordenados pelo identificador do contato.</p>
     * 
     * @param q O termo de pesquisa a ser usado para a busca.
     * @return Uma lista de contatos que correspondam ao termo de pesquisa fornecido.
     */
	@Query("SELECT c FROM Contatos c WHERE "
		     + "LOWER(c.nome) LIKE LOWER(CONCAT('%', :q, '%')) "
		     + "OR LOWER(c.contato) LIKE LOWER(CONCAT('%', :q, '%')) "
		     + "OR STR(c.profissionalId) LIKE CONCAT('%', :q, '%') "
		     + "AND c.deletedProfissional <> true ORDER BY c.id")
	List<Contatos> findByQuery(@Param("q") String q);
}
