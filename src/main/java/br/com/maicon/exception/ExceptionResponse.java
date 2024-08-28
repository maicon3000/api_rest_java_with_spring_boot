package br.com.maicon.exception;

import java.io.Serializable;
import java.util.Date;

/**
 * Classe que encapsula as informações de uma resposta de exceção.
 * 
 * <p>Esta classe é usada para padronizar as respostas de erro enviadas pelo servidor
 * em caso de exceções. Ela contém informações sobre a data e hora em que a exceção ocorreu,
 * a informação de sucess, a mensagem de erro e detalhes adicionais sobre a exceção.</p>
 * 
 * <b>Campos:</b>
 * <ul>
 *   <li>{@link #timestamp}: Data e hora em que a exceção ocorreu.</li>
 *   <li>{@link #success}: Mensagem de sucesso da operação, ocorrendo exceção sempre será false.</li>
 *   <li>{@link #message}: Mensagem de erro associada à exceção.</li>
 *   <li>{@link #details}: Detalhes adicionais sobre a exceção, como a URI da requisição.</li>
 * </ul>
 * 
 * <b>Considerações:</b>
 * <ul>
 *   <li>Esta classe implementa {@link Serializable}, permitindo que suas instâncias sejam serializadas.</li>
 * </ul>
 * 
 * @author Maicon
 * @version 1.0
 */
public class ExceptionResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Data e hora em que a exceção ocorreu.
     */
    private Date timestamp;
    
    /**
     * Informação de sucesso.
     */
    private Boolean success;

    /**
     * Mensagem de erro associada à exceção.
     */
    private String message;

    /**
     * Detalhes adicionais sobre a exceção, como a URI da requisição.
     */
    private String details;
    
    /**
     * Construtor que inicializa todos os campos da resposta de exceção.
     * 
     * @param timestamp Data e hora da exceção
     * @param message Mensagem de erro associada à exceção
     * @param details Detalhes adicionais sobre a exceção
     */
    public ExceptionResponse(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.success = false;
        this.message = message;
        this.details = details;
    }

    /**
     * Retorna a data e hora em que a exceção ocorreu.
     * 
     * @return A data e hora da exceção
     */
    public Date getTimestamp() {
        return timestamp;
    }
    
    /**
     * Retorna a informação de sucesso.
     * 
     * @return A informação de sucesso
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     * Retorna a mensagem de erro associada à exceção.
     * 
     * @return A mensagem de erro
     */
    public String getMessage() {
        return message;
    }

    /**
     * Retorna detalhes adicionais sobre a exceção.
     * 
     * @return Detalhes da exceção
     */
    public String getDetails() {
        return details;
    }
}
