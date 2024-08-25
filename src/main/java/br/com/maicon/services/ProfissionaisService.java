package br.com.maicon.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.maicon.exception.ResourceNotFoundException;
import br.com.maicon.model.Profissionais;
import br.com.maicon.repositories.ProfissionaisRepository;
import br.com.maicon.util.ApiResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.Data;

/**
 * Serviço responsável pelo gerenciamento de operações relacionadas à entidade {@link Profissionais}.
 * 
 * <p>
 * Esta classe fornece métodos para criar, atualizar, buscar e deletar profissionais no sistema.
 * A entidade {@link Profissionais} representa os dados dos profissionais cadastrados.
 * </p>
 * 
 * <b>Métodos principais:</b>
 * <ul>
 *   <li>{@link #findAll()}: Retorna todos os profissionais cadastrados.</li>
 *   <li>{@link #findById(Long)}: Retorna um profissional específico pelo seu ID.</li>
 *   <li>{@link #create(Profissionais)}: Cria um novo profissional.</li>
 *   <li>{@link #update(Profissionais)}: Atualiza os dados de um profissional existente.</li>
 *   <li>{@link #delete(Long)}: Deleta um profissional pelo seu ID.</li>
 * </ul>
 * 
 * <b>Considerações:</b>
 *  <ul>
 *    <li>A classe utiliza o validador de bean {@link Validator} para garantir que os dados dos profissionais estejam corretos antes de serem persistidos.</li>
 *    <li>Operações críticas, como criação, atualização e deleção de profissionais, são registradas via {@link Logger} para facilitar a auditoria e o monitoramento.</li>
 *    <li>Esta classe é anotada com {@link lombok.Data}, o que automaticamente gera os métodos getters, setters, {@code equals()}, {@code hashCode()} e {@code toString()}.
 *    Isso simplifica a classe e reduz a quantidade de código boilerplate, mas também implica que qualquer campo adicional terá esses métodos gerados automaticamente.</li>
 * </ul>
 * 
 * @author Maicon
 * @version 1.0
 */
@Data
@Service
public class ProfissionaisService {

    @Autowired
    private ProfissionaisRepository profissionaisRepository;

    private Logger logger = Logger.getLogger(ProfissionaisService.class.getName());
    
    private final Validator validator;

    /**
     * Retorna todos os profissionais cadastrados no sistema.
     * 
     * <p>Se nenhum profissional for encontrado, uma exceção {@link ResourceNotFoundException} será lançada.</p>
     * 
     * @return Lista de profissionais cadastrados
     * @throws ResourceNotFoundException se nenhum profissional for encontrado
     */
    public List<Profissionais> findAll() {
        logger.info("Finding all professionals");
        List<Profissionais> profissionais = new ArrayList<>();
        profissionais = profissionaisRepository.findAll();

        return profissionais;
    }

    /**
     * Retorna um profissional pelo seu ID.
     * 
     * <p>Se o ID não corresponder a nenhum profissional, uma exceção {@link ResourceNotFoundException} será lançada.</p>
     * 
     * @param id ID do profissional
     * @return Profissional encontrado
     * @throws ResourceNotFoundException se nenhum profissional for encontrado com o ID fornecido
     */
    public Profissionais findById(Long id) {
        logger.info("Finding professional with ID " + id);
        return profissionaisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profissional não encontrado"));
    }

    /**
     * Cria um novo profissional no sistema.
     * 
     * <p>O profissional deve passar pela validação antes de ser salvo. Se a validação falhar, 
     * uma exceção {@link IllegalArgumentException} será lançada.</p>
     * 
     * @param profissional Dados do profissional a ser criado
     * @return Profissional criado e salvo no banco de dados
     * @throws IllegalArgumentException se os dados do profissional forem inválidos
     */
    public ApiResponse create(Profissionais profissional) {
        validateProfissional(profissional);
        
        logger.info("Creating one professional");
        Profissionais savedProfissional = profissionaisRepository.save(profissional);
        logger.info("Created professional with ID " + profissional.getId());

        return new ApiResponse(true, "Profissional com ID " + savedProfissional.getId() + " cadastrado com sucesso!");
    }
    /**
     * Atualiza os dados de um profissional existente.
     * 
     * <p>O profissional deve passar pela validação antes de ser atualizado. Se a validação falhar, 
     * uma exceção {@link IllegalArgumentException} será lançada. Se o ID não corresponder a 
     * nenhum profissional existente, uma exceção {@link ResourceNotFoundException} será lançada.</p>
     * 
     * @param profissional Dados do profissional a ser atualizado
     * @return Profissional atualizado e salvo no banco de dados
     * @throws IllegalArgumentException se os dados do profissional forem inválidos
     * @throws ResourceNotFoundException se o profissional não for encontrado para atualização
     */
    public ApiResponse update(Profissionais profissional) {
        validateProfissional(profissional);
        
        if(profissionaisRepository.existsById(profissional.getId())) {
            logger.info("Updating professional with ID " + profissional.getId());
            profissionaisRepository.save(profissional);
            return new ApiResponse(true, "Cadastro alterado com sucesso!");
        } else {
            throw new ResourceNotFoundException("Profissional não encontrado para atualização");
        }
    }

	/**
	 * Marca um profissional como deletado pelo seu ID.
	 * 
	 * <p>Se o ID não corresponder a nenhum profissional existente, uma exceção {@link ResourceNotFoundException} será lançada.</p>
	 * 
	 * @param id ID do profissional a ser marcado como deletado
	 * @return Resposta contendo o sucesso da operação de deleção
	 * @throws ResourceNotFoundException se o profissional não for encontrado
	 */
	/*public ApiResponse delete(Long id) {
	    Profissionais profissional = profissionaisRepository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Profissional não encontrado com o ID " + id));

	    profissional.setDeleted(true);
	    profissional.setDeletedDate(new Date());

	    profissionaisRepository.save(profissional);

	    logger.info("Deleting professional with ID " + id + ": " + profissional.getNome());
	    return new ApiResponse(true, "Profissional excluído com sucesso!");
	}*/
    
    /**
     * Marca um profissional como deletado pelo seu ID.
     * 
     * <p>Se o ID não corresponder a nenhum profissional existente, ou se o profissional já estiver marcado como deletado, uma exceção {@link ResourceNotFoundException} será lançada.</p>
     * 
     * @param id ID do profissional a ser marcado como deletado
     * @return Resposta contendo o sucesso da operação de deleção
     * @throws ResourceNotFoundException se o profissional não for encontrado ou já estiver marcado como deletado
     */
    public ApiResponse delete(Long id) {
        String errorMessage = "Profissional não encontrado com o ID " + id;

        Profissionais profissional = profissionaisRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(errorMessage));
        
        if (profissional.isDeleted()) {
            throw new ResourceNotFoundException(errorMessage);
        }

        profissional.setDeleted(true);
        profissional.setDeletedDate(new Date());

        profissionaisRepository.save(profissional);
        logger.info("Logically deleting professional with ID " + id + ": " + profissional.getNome());
        return new ApiResponse(true, "Profissional excluído com sucesso!");
    }
    
    /**
     * Valida os dados do profissional fornecido.
     * 
     * <p>Se algum campo obrigatório estiver vazio ou nulo, uma exceção {@link IllegalArgumentException} será lançada.</p>
     * 
     * @param profissional Dados do profissional a serem validados
     * @throws IllegalArgumentException se os dados do profissional forem inválidos
     */
    private void validateProfissional(Profissionais profissional) {
        Set<ConstraintViolation<Profissionais>> violations = validator.validate(profissional);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("Nome do profissional não pode ser vazio ou nulo.");
        }
    }
}
