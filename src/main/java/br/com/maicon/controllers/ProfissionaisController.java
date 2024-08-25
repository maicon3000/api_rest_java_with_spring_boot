package br.com.maicon.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import br.com.maicon.model.Profissionais;
import br.com.maicon.services.ProfissionaisService;
import br.com.maicon.util.ApiResponse;
import jakarta.validation.Valid;

/**
 * Controlador REST para gerenciar operações relacionadas à entidade {@link Profissionais}.
 * 
 * <p>Este controlador fornece endpoints para criar, atualizar, buscar e deletar profissionais. 
 * Todos os métodos retornam respostas em formato JSON.</p>
 * 
 * @author Maicon
 * @version 1.0
 */
@RestController
@RequestMapping("profissionais")
public class ProfissionaisController {

    private final ProfissionaisService service;

    /**
     * Construtor para injeção de dependências.
     * 
     * @param service Serviço que lida com a lógica de negócios para {@link Profissionais}.
     */
    public ProfissionaisController(ProfissionaisService service) {
        this.service = service;
    }

    /**
     * Retorna a lista de todos os profissionais cadastrados.
     * 
     * <p>Se não houver profissionais cadastrados, uma lista vazia será retornada.</p>
     * 
     * @return Lista de todos os profissionais cadastrados, não deletados.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Profissionais> findAll() {
        return service.findAll();
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
    public Profissionais findById(@PathVariable Long id) {
        return service.findById(id);
    }

    /**
     * Cria um novo profissional.
     * 
     * <p>Os dados do profissional são validados antes de serem persistidos. Se a validação falhar,
     * uma exceção {@link org.springframework.web.bind.MethodArgumentNotValidException} será lançada.</p>
     * 
     * @param profissional Dados do novo profissional a ser criado.
     * @return Resposta contendo o status da operação e uma mensagem de sucesso.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody Profissionais profissional) {
        ApiResponse response = service.create(profissional);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Atualiza os dados de um profissional existente.
     * 
     * <p>Os dados do profissional são validados antes da atualização. Se a validação falhar,
     * uma exceção {@link org.springframework.web.bind.MethodArgumentNotValidException} será lançada.
     * Se o ID fornecido não corresponder a um profissional existente, uma exceção 
     * {@link br.com.maicon.exception.ResourceNotFoundException} será lançada.</p>
     * 
     * @param id ID do profissional a ser atualizado.
     * @param profissional Dados atualizados do profissional.
     * @return Resposta contendo o status da operação e uma mensagem de sucesso.
     * @throws br.com.maicon.exception.ResourceNotFoundException se o profissional não for encontrado.
     */
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @Valid @RequestBody Profissionais profissional) {
        profissional.setId(id);
        ApiResponse response = service.update(profissional);
        return ResponseEntity.ok(response);
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