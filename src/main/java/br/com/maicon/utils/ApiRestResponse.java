package br.com.maicon.utils;

import lombok.Data;

/**
 * Classe que representa a resposta padronizada das operações da API.
 * 
 * <p>Esta classe é utilizada para encapsular o resultado das operações executadas nos serviços da aplicação,
 * indicando o sucesso ou falha da operação e fornecendo uma mensagem descritiva.</p>
 * 
 * <b>Campos:</b>
 * <ul>
 *   <li>{@link #success}: Indica se a operação foi bem-sucedida ou não.</li>
 *   <li>{@link #message}: Mensagem descritiva que detalha o resultado da operação.</li>
 * </ul>
 * 
 * <b>Considerações:</b>
 * <ul>
 *   <li>Esta classe é anotada com {@link lombok.Data}, que automaticamente gera os métodos getters, setters, 
 *       {@code equals()}, {@code hashCode()} e {@code toString()}.</li>
 * </ul>
 * 
 * @author Maicon
 * @version 1.0
 */
@Data
public class ApiRestResponse {
    
    /**
     * Indica se a operação foi bem-sucedida ou não de acordo com regras de negócio definidas.
     */
    private boolean success;
    
    /**
     * Mensagem descritiva que detalha o resultado da operação.
     */
    private String message;
    
    /**
     * Construtor que inicializa a resposta da API com o status de sucesso e a mensagem correspondente.
     * 
     * @param success Indica se a operação foi bem-sucedida ou não de acordo com regras de negócio definidas.
     * @param message Mensagem descritiva do resultado da operação
     */
    public ApiRestResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}