package br.com.maicon.services;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.maicon.data.dto.v1.ContatosDTO;
import br.com.maicon.exception.ResourceNotFoundException;
import br.com.maicon.mapper.DozerMapper;
import br.com.maicon.models.Contatos;
import br.com.maicon.models.Profissionais;
import br.com.maicon.repositories.ContatosRepository;
import br.com.maicon.repositories.ProfissionaisRepository;
import br.com.maicon.services.validation.base.ValidatorBase;
import br.com.maicon.utils.ApiRestResponse;
import jakarta.validation.Validator;


/**
 * Serviço responsável pelo gerenciamento de operações relacionadas à entidade {@link Contatos}.
 * 
 * <p>
 * Esta classe fornece métodos para criar, atualizar, buscar e deletar contatos no sistema.
 * A entidade {@link ContatosDTO} representa os dados dos contatos cadastrados.
 * Internamente, utiliza o {@link DozerMapper} para converter entre {@link ContatosDTO} e {@link Contatos}.
 * </p>
 * 
 * <b>Métodos principais:</b>
 * <ul>
 *   <li>{@link #findAll()}: Retorna todos os contatos cadastrados.</li>
 *   <li>{@link #findAll(String)}: Retorna uma lista de contatos filtrados por um texto específico em seus atributos.</li>
 *   <li>{@link #findById(Long)}: Retorna um contato específico pelo seu ID.</li>
 *   <li>{@link #create(ContatosDTO)}: Cria um novo contato.</li>
 *   <li>{@link #update(ContatosDTO)}: Atualiza os dados de um contato existente.</li>
 *   <li>{@link #delete(Long)}: Deleta um contato pelo seu ID.</li>
 * </ul>
 * 
 * <b>Considerações:</b>
 *  <ul>
 *    <li>A classe utiliza o validador de bean {@link Validator} para garantir que os dados dos contatos estejam corretos antes de serem persistidos.</li>
 *    <li>Operações críticas, como criação, atualização e deleção de contatos, são registradas via {@link Logger} para facilitar a auditoria e o monitoramento.</li>
 * </ul>
 * 
 * @author Maicon
 * @version 1.0
 */
@Service
public class ContatosService {

    @Autowired
    private ContatosRepository contatosRepository;
    private ProfissionaisRepository profissionaisRepository;
    private final ValidatorBase<ContatosDTO> validator;
    private final Logger logger = Logger.getLogger(ContatosService.class.getName());

    /**
     * Construtor para injeção de dependências.
     * 
     * <p>Este construtor injeta as dependências necessárias para o serviço de gerenciamento de contatos.</p>
     * 
     * @param contatosRepository Repositório para acesso aos dados da entidade {@link Contatos}.
     * @param profissionaisRepository Repositório para acesso aos dados da entidade {@link Profissionais}.
     * @param validator Validador responsável por garantir a conformidade dos dados dos contatos.
     */
    public ContatosService(ContatosRepository contatosRepository, ProfissionaisRepository profissionaisRepository, ValidatorBase<ContatosDTO> validator) {
        this.contatosRepository = contatosRepository;
        this.profissionaisRepository = profissionaisRepository;
        this.validator = validator;
    }

    /**
     * Retorna todos os contatos cadastrados no sistema.
     * 
     * <p>Esse método utiliza um mapeamento via {@link DozerMapper} para converter a lista de entidades 
     * {@link Contatos} em uma lista de objetos {@link ContatosDTO}.</p>
     * 
     * @return Lista de contatos cadastrados.
     */
    public List<ContatosDTO> findAll() {
        logger.info("Finding all contacts");
        return DozerMapper.parseListObjects(contatosRepository.findAll(), ContatosDTO.class);
    }
    
    /**
     * Retorna uma lista de contatos filtrados por um texto específico em seus atributos.
     * 
     * <p>Se o parâmetro de pesquisa (q) for fornecido, os contatos cujo nome ou informação de contato contenham o texto
     * serão retornados. Caso contrário, todos os contatos serão retornados.</p>
     * 
     * @param q Texto para filtrar contatos pelo nome ou informação de contato (opcional).
     * @return Lista de contatos filtrados ou todos os contatos cadastrados.
     */
    public List<ContatosDTO> findAll(String q) {
        if (q != null && !q.isEmpty()) {
            return DozerMapper.parseListObjects(
                contatosRepository.findByQuery(q), ContatosDTO.class);
        } else {
            return DozerMapper.parseListObjects(
                contatosRepository.findAll(), ContatosDTO.class);
        }
    }

    /**
     * Retorna um contato pelo seu ID.
     * 
     * <p>Se o ID não corresponder a nenhum contato, uma exceção {@link ResourceNotFoundException} será lançada.</p>
     * 
     * @param id ID do contato.
     * @return Contato encontrado.
     * @throws ResourceNotFoundException se nenhum contato for encontrado com o ID fornecido.
     */
    public ContatosDTO findById(Long id) {
        logger.info("Finding contato with ID " + id);
        var contato = contatosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado"));
        
        return DozerMapper.parseObject(contato, ContatosDTO.class);
    }

    /**
     * Cria um novo contato no sistema.
     * 
     * <p>O contato deve passar pela validação antes de ser salvo. Se a validação falhar, 
     * uma resposta de erro será retornada indicando a falha de validação.</p>
     * 
     * <p>O campo {@code createdDate} é automaticamente preenchido com a data e hora atuais
     * no fuso horário de São Paulo (GMT-3).</p>
     * 
     * <p>O campo {@code deletedProfissional} é automaticamente preenchido com false.</p>
     * 
     * <p>Este método utiliza o {@link DozerMapper} para converter o {@link ContatosDTO} 
     * em uma entidade {@link Contatos} antes de persistir no banco de dados.</p>
     * 
     * @param contato Dados do contato a ser criado.
     * @return Resposta contendo o sucesso ou falha da operação de criação, incluindo o ID do contato criado em caso de sucesso.
     * @throws IllegalArgumentException se os dados do contato forem inválidos.
     */
    public ApiRestResponse create(ContatosDTO contato) {
        ApiRestResponse validationResponse = validator.validateBase(contato);
        
        if (!validationResponse.isSuccess()) {
            return validationResponse;
        }
        
        profissionaisRepository.findByIdAndActive(contato.getProfissionalId())
                .orElseThrow(() -> new ResourceNotFoundException("Profissional não encontrado para adição de contato"));
        
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
        contato.setCreatedDate(Date.from(now.toInstant()));  
        contato.setDeletedProfissional(false);
        var converterContato = DozerMapper.parseObject(contato, Contatos.class);
        contatosRepository.save(converterContato);

        logger.info("Created contato with ID " + converterContato.getId());

        return new ApiRestResponse(true, "Contato com ID " + converterContato.getId() + " cadastrado com sucesso!");
    }

    /**
     * Atualiza os dados de um contato existente.
     * 
     * <p>O contato deve passar pela validação antes de ser atualizado. Se a validação falhar, 
     * uma resposta de erro será retornada indicando a falha de validação. Se o ID não corresponder a 
     * nenhum contato existente, uma exceção {@link ResourceNotFoundException} será lançada.</p>
     * 
     * <p>O campo {@code createdDate} do contato não é alterado durante a atualização.</p>
     * 
     * <p>Este método utiliza o {@link DozerMapper} para converter o {@link ContatosDTO} 
     * em uma entidade {@link Contatos} antes de persistir no banco de dados.</p>
     * 
     * @param contato Dados do contato a ser atualizado.
     * @return Resposta contendo o sucesso ou falha da operação de atualização.
     * @throws IllegalArgumentException se os dados do contato forem inválidos.
     * @throws ResourceNotFoundException se o contato não for encontrado para atualização.
     */
    public ApiRestResponse update(ContatosDTO contato) {
        ApiRestResponse validationResponse = validator.validateBase(contato);
        
        if (!validationResponse.isSuccess()) {
            return validationResponse;
        }
        
        var existingContact = contatosRepository.findByIdAndActive(contato.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado para atualização"));
        
        contato.setCreatedDate(existingContact.getCreatedDate());
        
        logger.info("Updating contact with ID " + contato.getId());
        
        var converterContact = DozerMapper.parseObject(contato, Contatos.class);
        contatosRepository.save(converterContact);
        
        return new ApiRestResponse(true, "Cadastro alterado com sucesso!");
    }
    
    /**
     * Deleta um contato pelo seu ID.
     * 
     * <p>Este método verifica se o contato existe e não está marcado como deletado utilizando o método 
     * {@link br.com.maicon.repositories.ContatosRepository#findByIdAndActive(Long)}. Se o contato não for encontrado ou já estiver deletado, 
     * uma exceção {@link ResourceNotFoundException} será lançada. No entanto, ele realiza a deleção física do banco.</p>
     * 
     * @param id ID do contato a ser deletado.
     * @return Resposta contendo o sucesso da operação de deleção.
     * @throws ResourceNotFoundException se o contato não for encontrado ou já estiver marcado como deletado.
     */
    public ApiRestResponse delete(Long id) {
        contatosRepository.findByIdAndActive(id)
            .orElseThrow(() -> new ResourceNotFoundException("Contato com ID " + id + " não encontrado."));
        
        logger.info("Deleting contato with ID " + id);
        contatosRepository.deleteById(id);
        return new ApiRestResponse(true, "Contato deletado com sucesso!");
    }
}
