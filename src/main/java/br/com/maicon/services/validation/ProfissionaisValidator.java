package br.com.maicon.services.validation;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import br.com.maicon.data.dto.v1.ProfissionaisDTO;
import br.com.maicon.util.ApiResponse;
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
    private final List<String> validCargos = List.of("Desenvolvedor", "Designer", "Suporte", "Tester");

    /**
     * Construtor para inicializar o validador com uma instância de {@link Validator}.
     * 
     * @param validator Instância do validador do Jakarta Bean Validation.
     */
    public ProfissionaisValidator(Validator validator) {
        this.validator = validator;
    }

    /**
     * Valida os dados do profissional fornecido, incluindo a validação do cargo.
     * 
     * @param profissional Dados do profissional a serem validados.
     * @return ApiResponse com o resultado da validação do cargo.
     * @throws IllegalArgumentException se os dados do profissional forem inválidos.
     */
    public ApiResponse validate(ProfissionaisDTO profissional) {
        String normalizedCargo = capitalizeCargo(profissional.getCargo());
        if (!validCargos.contains(normalizedCargo)) {
            return new ApiResponse(false, "O cargo do profissional deve ser: Desenvolvedor, Designer, Suporte ou Tester.");
        }
        
        profissional.setCargo(normalizedCargo);

        Set<ConstraintViolation<ProfissionaisDTO>> violations = validator.validate(profissional);
        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Erros de validação: ");
            for (ConstraintViolation<ProfissionaisDTO> violation : violations) {
                errorMessage.append(violation.getPropertyPath()).append(" - ").append(violation.getMessage()).append(".");
            }
            throw new IllegalArgumentException(errorMessage.toString());
        }

        return new ApiResponse(true, "Validação realizada com sucesso.");
    }
    
    /**
     * Normaliza o texto do cargo para capitalizar a primeira letra de cada palavra.
     * 
     * @param cargo O cargo fornecido pelo usuário.
     * @return O cargo normalizado com a primeira letra maiúscula.
     */
    public String capitalizeCargo(String cargo) {
        if (cargo == null || cargo.isEmpty()) {
            return cargo;
        }
        return cargo.substring(0, 1).toUpperCase() + cargo.substring(1).toLowerCase();
    }
}