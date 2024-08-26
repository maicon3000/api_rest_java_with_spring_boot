package br.com.maicon.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.maicon.exception.ExceptionResponse;
import br.com.maicon.exception.ResourceNotFoundException;

/**
 * Manipulador global de exceções para a aplicação.
 * 
 * <p>Esta classe captura e processa exceções lançadas pelos controladores REST da aplicação,
 * fornecendo uma resposta padronizada de erro para o cliente. Ela lida com exceções específicas,
 * como {@link ResourceNotFoundException}, além de capturar qualquer outra exceção geral não tratada.</p>
 * 
 * <b>Métodos principais:</b>
 * <ul>
 *   <li>{@link #handleResourceNotFoundException(ResourceNotFoundException, WebRequest)}: Lida com exceções de recurso não encontrado.</li>
 *   <li>{@link #handleGlobalException(Exception, WebRequest)}: Lida com todas as outras exceções não tratadas.</li>
 * </ul>
 * 
 * <b>Considerações:</b>
 * <ul>
 *   <li>A anotação {@link ControllerAdvice} faz com que esta classe seja aplicada globalmente a todos os controladores REST.</li>
 *   <li>A anotação {@link RestController} permite que as respostas retornadas pelos métodos sejam automaticamente convertidas para JSON ou XML.</li>
 * </ul>
 * 
 * @see ResponseEntityExceptionHandler
 * @see ExceptionHandler
 * @see ResourceNotFoundException
 * @see Exception
 * 
 * @version 1.0
 */
@ControllerAdvice
@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Manipula exceções do tipo {@link ResourceNotFoundException}.
     * 
     * <p>Este método cria uma resposta personalizada com status HTTP 404 (Not Found) quando um recurso
     * não é encontrado. A resposta inclui a data e hora da exceção, a mensagem de erro, e detalhes adicionais.</p>
     * 
     * @param ex A exceção lançada quando um recurso não é encontrado
     * @param request A requisição atual, usada para obter detalhes adicionais sobre a exceção
     * @return Um {@link ResponseEntity} contendo um {@link ExceptionResponse} com detalhes do erro
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Manipula todas as outras exceções não tratadas.
     * 
     * <p>Este método captura exceções gerais e retorna uma resposta com status HTTP 500 (Internal Server Error).
     * A resposta inclui a data e hora da exceção, a mensagem de erro, e detalhes adicionais.</p>
     * 
     * @param ex A exceção lançada
     * @param request A requisição atual, usada para obter detalhes adicionais sobre a exceção
     * @return Um {@link ResponseEntity} contendo um {@link ExceptionResponse} com detalhes do erro
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGlobalException(Exception ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
