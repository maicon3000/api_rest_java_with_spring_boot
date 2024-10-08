package br.com.maicon.services;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.maicon.data.dto.v1.ProfissionaisDTO;
import br.com.maicon.exception.ResourceNotFoundException;
import br.com.maicon.mapper.DozerMapper;
import br.com.maicon.models.Contatos;
import br.com.maicon.models.Profissionais;
import br.com.maicon.repositories.ContatosRepository;
import br.com.maicon.repositories.ProfissionaisRepository;
import br.com.maicon.services.validation.ProfissionaisValidator;
import br.com.maicon.utils.ApiRestResponse;
import jakarta.validation.Validator;

/**
 * Serviço responsável pelo gerenciamento de operações relacionadas à entidade {@link ProfissionaisDTO}.
 * 
 * <p>
 * Esta classe fornece métodos para criar, atualizar, buscar e deletar profissionais no sistema.
 * A entidade {@link ProfissionaisDTO} representa os dados dos profissionais cadastrados.
 * Internamente, utiliza o {@link DozerMapper} para converter entre {@link ProfissionaisDTO} e {@link Profissionais}.
 * </p>
 * 
 * <b>Métodos principais:</b>
 * <ul>
 *   <li>{@link #findAll()}: Retorna todos os profissionais cadastrados.</li>
 *   <li>{@link #findById(Long)}: Retorna um profissional específico pelo seu ID.</li>
 *   <li>{@link #create(ProfissionaisDTO)}: Cria um novo profissional.</li>
 *   <li>{@link #update(ProfissionaisDTO)}: Atualiza os dados de um profissional existente.</li>
 *   <li>{@link #delete(Long)}: Deleta um profissional pelo seu ID.</li>
 * </ul>
 * 
 * <b>Considerações:</b>
 *  <ul>
 *    <li>A classe utiliza o validador de bean {@link Validator} para garantir que os dados dos profissionais estejam corretos antes de serem persistidos.</li>
 *    <li>Operações críticas, como criação, atualização e deleção de profissionais, são registradas via {@link Logger} para facilitar a auditoria e o monitoramento.</li>
 * </ul>
 * 
 * @author Maicon
 * @version 1.0
 */
@Service
public class ProfissionaisService {

    @Autowired
    private ProfissionaisRepository profissionaisRepository;
    private ContatosRepository contatosRepository;
    private final ProfissionaisValidator profissionaisValidator;
    private final Logger logger = Logger.getLogger(ProfissionaisService.class.getName());

    /**
     * Construtor para injeção de dependências.
     * 
     * <p>Este construtor injeta as dependências necessárias para o serviço de gerenciamento de profissionais.</p>
     * 
     * @param profissionaisRepository Repositório para acesso aos dados da entidade {@link Profissionais}.
     * @param profissionaisValidator Validador responsável por garantir a conformidade dos dados dos profissionais.
     */
    public ProfissionaisService(ProfissionaisRepository profissionaisRepository, ContatosRepository contatosRepository, ProfissionaisValidator profissionaisValidator) {
        this.profissionaisRepository = profissionaisRepository;
        this.contatosRepository = contatosRepository;
        this.profissionaisValidator = profissionaisValidator;
    }

    /**
     * Retorna todos os profissionais cadastrados no sistema que não foram deletados.
     * 
     * <p>Esse método utiliza um mapeamento via {@link DozerMapper} para converter a lista de entidades 
     * {@link Profissionais} em uma lista de objetos {@link ProfissionaisDTO}.</p>
     * 
     * @return Lista de profissionais cadastrados e não deletados.
     */
    public List<ProfissionaisDTO> findAll() {
        logger.info("Finding all professionals");
        return DozerMapper.parseListObjects(profissionaisRepository.findAllActive(), ProfissionaisDTO.class);
    }
    
    /**
     * Retorna uma lista de profissionais filtrados por um texto específico em seus atributos.
     * 
     * <p>Se o parâmetro de pesquisa (q) for fornecido, os profissionais cujo nome ou cargo contenham o texto
     * serão retornados. Caso contrário, todos os profissionais não deletados serão retornados.</p>
     * 
     * @param q Texto para filtrar profissionais pelo nome ou cargo (opcional).
     * @return Lista de profissionais filtrados ou todos os profissionais cadastrados, não deletados.
     */
    public List<ProfissionaisDTO> findAll(String q) {
        if (q != null && !q.isEmpty()) {
            return DozerMapper.parseListObjects(
                profissionaisRepository.findByQuery(q), ProfissionaisDTO.class);
        } else {
            return DozerMapper.parseListObjects(
                profissionaisRepository.findAllActive(), ProfissionaisDTO.class);
        }
    }

    /**
     * Retorna um profissional pelo seu ID.
     * 
     * <p>Se o ID não corresponder a nenhum profissional ativo (não deletado), uma exceção {@link ResourceNotFoundException} será lançada.</p>
     * 
     * @param id ID do profissional.
     * @return Profissional encontrado.
     * @throws ResourceNotFoundException se nenhum profissional for encontrado com o ID fornecido.
     */
    public ProfissionaisDTO findById(Long id) {
        logger.info("Finding professional with ID " + id);
        var professional = profissionaisRepository.findByIdAndActive(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profissional não encontrado"));
        
        return DozerMapper.parseObject(professional, ProfissionaisDTO.class);
    }

    /**
     * Cria um novo profissional no sistema.
     * 
     * <p>O profissional deve passar pela validação antes de ser salvo. Se a validação falhar, 
     * uma resposta de erro será retornada indicando a falha de validação.</p>
     * 
     * <p>O campo {@code createdDate} é automaticamente preenchido com a data e hora atuais
     * no fuso horário de São Paulo (GMT-3).</p>
     * 
     * <p>Este método utiliza o {@link DozerMapper} para converter o {@link ProfissionaisDTO} 
     * em uma entidade {@link Profissionais} antes de persistir no banco de dados.</p>
     * 
     * @param professional Dados do profissional a ser criado.
     * @return Resposta contendo o sucesso ou falha da operação de criação, incluindo o ID do profissional criado em caso de sucesso.
     * @throws IllegalArgumentException se os dados do profissional forem inválidos.
     */
    public ApiRestResponse create(ProfissionaisDTO professional) {
        ApiRestResponse validationResponse = profissionaisValidator.validate(professional);
        
        if (!validationResponse.isSuccess()) {
            return validationResponse;
        }
        
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
        professional.setCreatedDate(Date.from(now.toInstant()));        
        var converterProfessional = DozerMapper.parseObject(professional, Profissionais.class);
        profissionaisRepository.save(converterProfessional);

        logger.info("Created professional with ID " + converterProfessional.getId());

        return new ApiRestResponse(true, "Profissional com ID " + converterProfessional.getId() + " cadastrado com sucesso!");
    }

    /**
     * Atualiza os dados de um profissional existente.
     * 
     * <p>O profissional deve passar pela validação antes de ser atualizado. Se a validação falhar, 
     * uma resposta de erro será retornada indicando a falha de validação. Se o ID não corresponder a 
     * nenhum profissional existente, uma exceção {@link ResourceNotFoundException} será lançada.</p>
     * 
     * <p>O campo {@code createdDate} do profissional não é alterado durante a atualização.</p>
     * 
     * <p>Este método utiliza o {@link DozerMapper} para converter o {@link ProfissionaisDTO} 
     * em uma entidade {@link Profissionais} antes de persistir no banco de dados.</p>
     * 
     * @param professional Dados do profissional a ser atualizado.
     * @return Resposta contendo o sucesso ou falha da operação de atualização.
     * @throws IllegalArgumentException se os dados do profissional forem inválidos.
     * @throws ResourceNotFoundException se o profissional não for encontrado para atualização.
     */
    public ApiRestResponse update(ProfissionaisDTO professional) {
        ApiRestResponse validationResponse = profissionaisValidator.validate(professional);
        
        if (!validationResponse.isSuccess()) {
            return validationResponse;
        }
        
        var existingProfessional = profissionaisRepository.findByIdAndActive(professional.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Profissional não encontrado para atualização"));
        
        professional.setCreatedDate(existingProfessional.getCreatedDate());
        
        logger.info("Updating professional with ID " + professional.getId());
        
        var converterProfessional = DozerMapper.parseObject(professional, Profissionais.class);
        profissionaisRepository.save(converterProfessional);
        
        return new ApiRestResponse(true, "Cadastro alterado com sucesso!");
    }
    
    /**
     * Marca um profissional como deletado pelo seu ID e atualiza todos os contatos associados como deletados.
     * 
     * <p>Este método realiza a deleção lógica de um profissional, marcando o campo {@code deleted} como verdadeiro e 
     * atribuindo a data atual ao campo {@code deletedDate}. Além disso, todos os contatos associados ao profissional 
     * terão o campo {@code deletedProfissional} marcado como verdadeiro.</p>
     * 
     * <p>Se o ID fornecido não corresponder a nenhum profissional existente, uma exceção {@link ResourceNotFoundException} 
     * será lançada. O mesmo ocorrerá se o profissional já estiver marcado como deletado.</p>
     * 
     * @param id ID do profissional a ser marcado como deletado.
     * @return Resposta contendo o sucesso da operação de deleção.
     * @throws ResourceNotFoundException se o profissional não for encontrado ou já estiver marcado como deletado.
     */
    public ApiRestResponse delete(Long id) {
        String errorMessage = "Profissional não encontrado com o ID " + id;

        Profissionais profissional = profissionaisRepository.findByIdAndActive(id)
            .orElseThrow(() -> new ResourceNotFoundException(errorMessage));

        profissional.setDeleted(true);
        profissional.setDeletedDate(new Date());
        
        List<Contatos> contatosList = contatosRepository.findAll();
        
        for (Contatos contato : contatosList) {
            if (contato.getProfissionalId().equals(id)) {
                contato.setDeletedProfissional(true);
                contatosRepository.save(contato);
            }
        }
        profissionaisRepository.save(profissional);
        logger.info("Logically deleting professional with ID " + id + ": " + profissional.getNome());
        return new ApiRestResponse(true, "Profissional excluído com sucesso!");
    }
}
