package br.com.maicon.services.validation;

import java.util.Set;

import org.springframework.stereotype.Component;

import br.com.maicon.data.dto.v1.ProfissionaisDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

/**
 * Classe responsável por validar os dados dos profissionais utilizando o framework de validação do Jakarta Bean Validation.
 * 
 * <p>
 * Esta classe utiliza a interface {@link Validator} para realizar a validação dos campos de um objeto {@link ProfissionaisDTO}.
 * Caso algum campo obrigatório esteja vazio, nulo ou não atenda às restrições definidas, uma exceção {@link IllegalArgumentException} é lançada.
 * </p>
 * 
 * <b>Exemplo de Uso:</b>
 * <pre>{@code
 * ProfissionaisValidator validator = new ProfissionaisValidator(validator);
 * validator.validate(profissionaisDTO);
 * }</pre>
 * 
 * @author Maicon
 * @version 1.0
 */
@Component
public class ProfissionaisValidator {

    private final Validator validator;

    /**
     * Construtor para inicializar o validador com uma instância de {@link Validator}.
     * 
     * @param validator Instância do validador do Jakarta Bean Validation.
     */
    public ProfissionaisValidator(Validator validator) {
        this.validator = validator;
    }

    /**
     * Valida os dados do profissional fornecido.
     * 
     * <p>Se algum campo obrigatório estiver vazio ou nulo, uma exceção {@link IllegalArgumentException} será lançada.</p>
     * 
     * @param profissional Dados do profissional a serem validados.
     * @throws IllegalArgumentException se os dados do profissional forem inválidos.
     */
    public void validate(ProfissionaisDTO profissional) {
        Set<ConstraintViolation<ProfissionaisDTO>> violations = validator.validate(profissional);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("Nome do profissional não pode ser vazio ou nulo.");
        }
    }
}