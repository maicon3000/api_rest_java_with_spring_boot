package br.com.maicon.controllers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.maicon.data.dto.v1.ProfissionaisDTO;
import br.com.maicon.data.dto.v1.utils.DtoUtils;
import br.com.maicon.services.ProfissionaisService;
import br.com.maicon.utils.ApiResponse;
import jakarta.validation.Valid;

/**
 * Controlador REST para gerenciar operações relacionadas à entidade {@link ProfissionaisDTO}.
 * 
 * <p>Este controlador fornece endpoints para criar, atualizar, buscar e deletar profissionais. 
 * Todos os métodos retornam respostas em formato JSON.</p>
 * 
 * @author Maicon
 * @version 1.0
 */
@RestController
@RequestMapping("/api/profissionais/v1")
public class ProfissionaisController {

    private final ProfissionaisService service;

    /**
     * Construtor para injeção de dependências.
     * 
     * @param service Serviço que lida com a lógica de negócios para {@link ProfissionaisDTO}.
     */
    public ProfissionaisController(ProfissionaisService service) {
        this.service = service;
    }

    /**
     * Retorna a lista de todos os profissionais cadastrados, possivelmente filtrada por campos específicos.
     * 
     * <p>Se um parâmetro de pesquisa (q) for fornecido, os profissionais cujo nome ou cargo contenham o texto
     * serão retornados. Se a lista de campos (fields) for fornecida, apenas os campos especificados serão incluídos
     * na resposta. Se não houver profissionais cadastrados, uma lista vazia será retornada.</p>
     * 
     * @param q Texto para filtrar profissionais pelo nome ou cargo (opcional).
     * @param fields Lista de campos a serem retornados (opcional).
     * @return Lista de profissionais cadastrados, possivelmente filtrada pelos campos especificados.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Map<String, Object>>> findAll(
        @RequestParam(required = false) String q,
        @RequestParam(required = false) List<String> fields) {

        List<ProfissionaisDTO> profissionais = service.findAll(q);

        // Se fields estiver presente, filtrar os campos
        if (fields != null && !fields.isEmpty()) {
            List<Map<String, Object>> filteredResponse = profissionais.stream()
                .map(profissional -> DtoUtils.filterFields (profissional, fields))
                .collect(Collectors.toList());
            return ResponseEntity.ok(filteredResponse);
        }

        // Retornar todos os campos se fields não estiver presente
        return ResponseEntity.ok(profissionais.stream()
                .map(DtoUtils::convertToMap)
                .collect(Collectors.toList()));
    }

    /**
     * Retorna um profissional específico pelo ID.
     * 
     * <p>Se o ID fornecido não corresponder a um profissional existente, não deletado, 
     * uma exceção {@link br.com.maicon.exception.ResourceNotFoundException} será lançada.</p>
     * 
     * @param id ID do profissional a ser encontrado.
     * @return Profissional correspondente ao ID fornecido.
     * @throws br.com.maicon.exception.ResourceNotFoundException se o profissional não for encontrado.
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProfissionaisDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    /**
     * Cria um novo profissional.
     * 
     * <p>Os dados do profissional são validados antes de serem persistidos. Se a validação falhar,
     * uma exceção {@link org.springframework.web.bind.MethodArgumentNotValidException} será lançada.
     * O campo `createdDate` é gerado automaticamente pelo sistema e não deve ser fornecido no corpo da requisição.</p>
     * 
     * @param profissional Dados do novo profissional a ser criado.
     * @return Resposta contendo o status da operação e uma mensagem de sucesso.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> create(@RequestBody ProfissionaisDTO profissional) {
        ApiResponse response = service.create(profissional);
        if (response.isSuccess()) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Atualiza os dados de um profissional existente.
     * 
     * <p>Os dados do profissional são validados antes da atualização. Se a validação falhar,
     * uma exceção {@link org.springframework.web.bind.MethodArgumentNotValidException} será lançada.
     * O campo `createdDate` do profissional não é alterado durante a atualização.
     * Se o ID fornecido não corresponder a um profissional existente, uma exceção 
     * {@link br.com.maicon.exception.ResourceNotFoundException} será lançada.</p>
     * 
     * @param id ID do profissional a ser atualizado.
     * @param profissional Dados atualizados do profissional.
     * @return Resposta contendo o status da operação e uma mensagem de sucesso.
     * @throws br.com.maicon.exception.ResourceNotFoundException se o profissional não for encontrado.
     */
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @Valid @RequestBody ProfissionaisDTO profissional) {
        profissional.setId(id);
        ApiResponse response = service.update(profissional);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Marca um profissional como deletado.
     * 
     * <p>O profissional não é removido fisicamente do banco de dados, mas é marcado como 
     * deletado através do campo {@code deleted}. Se o ID fornecido não corresponder a 
     * um profissional existente ou se o profissional já estiver deletado, 
     * uma exceção {@link br.com.maicon.exception.ResourceNotFoundException} será lançada.</p>
     * 
     * @param id ID do profissional a ser marcado como deletado.
     * @return Resposta contendo o status da operação e uma mensagem de sucesso.
     * @throws br.com.maicon.exception.ResourceNotFoundException se o profissional não for encontrado.
     */
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        ApiResponse response = service.delete(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}