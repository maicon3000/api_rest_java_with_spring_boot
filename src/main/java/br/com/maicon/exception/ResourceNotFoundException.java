package br.com.maicon.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção personalizada lançada quando um recurso não é encontrado.
 * 
 * <p>Esta exceção é usada para sinalizar que uma operação tentou acessar um recurso que não existe.
 * Ela é anotada com {@link ResponseStatus}, o que faz com que o Spring MVC retorne um status HTTP 404
 * (Not Found) quando a exceção for lançada.</p>
 * 
 * <b>Campos:</b>
 * <ul>
 *   <li>{@link #serialVersionUID}: Identificador de versão para serialização.</li>
 * </ul>
 * 
 * <b>Considerações:</b>
 * <ul>
 *   <li>Esta exceção estende {@link RuntimeException}, permitindo que seja lançada sem ser explicitamente declarada.</li>
 * </ul>
 * 
 * @see RuntimeException
 * @see ResponseStatus
 * @see HttpStatus
 * 
 * @author Maicon
 * @version 1.0
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Construtor que inicializa a exceção com uma mensagem específica.
     * 
     * @param ex A mensagem de erro associada à exceção
     */
    public ResourceNotFoundException(String ex) {
        super(ex);
    }
}