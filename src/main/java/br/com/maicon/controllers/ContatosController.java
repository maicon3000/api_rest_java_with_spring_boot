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

import br.com.maicon.data.dto.v1.ContatosDTO;
import br.com.maicon.data.dto.v1.utils.DtoUtils;
import br.com.maicon.services.ContatosService;
import br.com.maicon.utils.ApiRestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Controlador REST para gerenciar operações relacionadas à entidade {@link ContatosDTO}.
 * 
 * <p>Este controlador fornece endpoints para criar, atualizar, buscar e deletar contatos profissionais. 
 * Todos os métodos retornam respostas em formato JSON.</p>
 * 
 * @author Maicon
 * @version 1.0
 */
@RestController
@RequestMapping("/api/contatos/v1")
@Tag(name = "Contatos", description = "Endpoint for managing professional contacts")
public class ContatosController {

    private final ContatosService service;

    /**
     * Construtor para injeção de dependências.
     * 
     * @param service Serviço que lida com a lógica de negócios para {@link ContatosDTO}.
     */
    public ContatosController(ContatosService service) {
        this.service = service;
    }

    /**
     * Retorna a lista de todos os contatos profissionais cadastrados, possivelmente filtrada por campos específicos.
     * 
     * <p>Se um parâmetro de pesquisa (q) for fornecido, os contatos cujo nome ou cargo contenham o texto
     * serão retornados. Se a lista de campos (fields) for fornecida, apenas os campos especificados serão incluídos
     * na resposta. Se não houver contatos cadastrados, uma lista vazia será retornada.</p>
     * 
     * @param q Texto para filtrar contatos pelo nome ou cargo (opcional).
     * @param fields Lista de campos a serem retornados (opcional).
     * @return Lista de contatos cadastrados, possivelmente filtrada pelos campos especificados.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Finds all professional contacts", description = "Finds all professional contacts",
        tags = {"Contatos"},
        responses = {
            @ApiResponse(responseCode = "200", description = "Success",
                content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ContatosDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
        }
    )
    public ResponseEntity<List<Map<String, Object>>> findAll(
        @RequestParam(required = false) String q,
        @RequestParam(required = false) List<String> fields) {

        List<ContatosDTO> contatos = service.findAll(q);

        if (fields != null && !fields.isEmpty()) {
            List<Map<String, Object>> filteredResponse = contatos.stream()
                .map(contato -> DtoUtils.filterFields(contato, fields))
                .collect(Collectors.toList());
            return ResponseEntity.ok(filteredResponse);
        }

        return ResponseEntity.ok(contatos.stream()
                .map(DtoUtils::convertToMap)
                .collect(Collectors.toList()));
    }

    /**
     * Retorna um contato específico pelo ID.
     * 
     * <p>Se o ID fornecido não corresponder a um contato existente, 
     * uma exceção {@link br.com.maicon.exception.ResourceNotFoundException} será lançada.</p>
     * 
     * @param id ID do contato a ser encontrado.
     * @return Contato correspondente ao ID fornecido.
     * @throws br.com.maicon.exception.ResourceNotFoundException se o contato não for encontrado.
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Find a contact by ID", description = "Find a contact by ID",
    tags = {"Contatos"},
    responses = {
        @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(schema = @Schema(implementation = ContatosDTO.class))),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
        @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
    })
    public ContatosDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    /**
     * Cria um novo contato.
     * 
     * <p>Os dados do contato são validados antes de serem persistidos. Se a validação falhar,
     * uma exceção {@link org.springframework.web.bind.MethodArgumentNotValidException} será lançada.
     * O campo `createdDate` é gerado automaticamente pelo sistema e não deve ser fornecido no corpo da requisição.</p>
     * 
     * @param contato Dados do novo contato a ser criado.
     * @return Resposta contendo o status da operação e uma mensagem de sucesso.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Adds a new contact", description = "Adds a new contact",
    tags = {"Contatos"},
    responses = {
        @ApiResponse(responseCode = "201", description = "Created",
            content = @Content(schema = @Schema(implementation = ApiRestResponse.class))),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
    })
    public ResponseEntity<ApiRestResponse> create(@RequestBody ContatosDTO contato) {
        ApiRestResponse response = service.create(contato);
        if (response.isSuccess()) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Atualiza os dados de um contato existente.
     * 
     * <p>Os dados do contato são validados antes da atualização. Se a validação falhar,
     * uma exceção {@link org.springframework.web.bind.MethodArgumentNotValidException} será lançada.
     * O campo `createdDate` do contato não é alterado durante a atualização.
     * Se o ID fornecido não corresponder a um contato existente, uma exceção 
     * {@link br.com.maicon.exception.ResourceNotFoundException} será lançada.</p>
     * 
     * @param id ID do contato a ser atualizado.
     * @param contato Dados atualizados do contato.
     * @return Resposta contendo o status da operação e uma mensagem de sucesso.
     * @throws br.com.maicon.exception.ResourceNotFoundException se o contato não for encontrado.
     */
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Updates a contact", description = "Updates a contact",
    tags = {"Contatos"},
    responses = {
        @ApiResponse(responseCode = "200", description = "Updated",
            content = @Content(schema = @Schema(implementation = ApiRestResponse.class))),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
    })
    public ResponseEntity<ApiRestResponse> update(@PathVariable Long id, @Valid @RequestBody ContatosDTO contato) {
        contato.setId(id);
        ApiRestResponse response = service.update(contato);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Deleta um contato.
     * 
     * <p>O contato é removido fisicamente do banco de dados. Se o ID fornecido não corresponder a 
     * um contato existente, uma exceção {@link br.com.maicon.exception.ResourceNotFoundException} será lançada.</p>
     * 
     * @param id ID do contato a ser deletado.
     * @return Resposta contendo o status da operação e uma mensagem de sucesso.
     * @throws br.com.maicon.exception.ResourceNotFoundException se o contato não for encontrado.
     */
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Deletes a contact", description = "Deletes a contact",
    tags = {"Contatos"},
    responses = {
        @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(schema = @Schema(implementation = ApiRestResponse.class))),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
        @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
    })
    public ResponseEntity<ApiRestResponse> delete(@PathVariable Long id) {
        ApiRestResponse response = service.delete(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
