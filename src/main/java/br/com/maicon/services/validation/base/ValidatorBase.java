package br.com.maicon.services.validation.base;

import java.util.Set;

import org.springframework.stereotype.Component;

import br.com.maicon.utils.ApiRestResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

/**
 * Classe base para validação de DTOs utilizando Jakarta Bean Validation.
 * 
 * <p>
 * Esta classe fornece um método genérico de validação que pode ser reutilizado
 * em diferentes validadores, permitindo a validação de qualquer tipo de classe.
 * </p>
 */
@Component
public class ValidatorBase<T> {

    private final Validator validator;

    /**
     * Construtor para inicializar o validador com uma instância de {@link Validator}.
     * 
     * @param validator Instância do validador do Jakarta Bean Validation.
     */
    public ValidatorBase(Validator validator) {
        this.validator = validator;
    }

    /**
     * Valida a instância da classe genérica fornecida.
     * 
     * @param entity Instância da classe a ser validada.
     * @return {@link ApiRestResponse} com o resultado da validação.
     */
    public ApiRestResponse validateBase(T entity) {
        Set<ConstraintViolation<T>> violations = validator.validate(entity);
        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Erros de validação: ");
            for (ConstraintViolation<T> violation : violations) {
                errorMessage.append(violation.getPropertyPath()).append(" - ").append(violation.getMessage()).append(".");
            }
            throw new IllegalArgumentException(errorMessage.toString());
        }

        return new ApiRestResponse(true, "Validação realizada com sucesso.");
    }
}
