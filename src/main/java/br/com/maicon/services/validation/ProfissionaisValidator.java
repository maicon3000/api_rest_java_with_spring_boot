package br.com.maicon.services.validation;

import java.util.List;

import org.springframework.stereotype.Component;
import br.com.maicon.data.dto.v1.ProfissionaisDTO;
import br.com.maicon.services.validation.base.ValidatorBase;
import br.com.maicon.utils.ApiRestResponse;
import jakarta.validation.Validator;

/**
 * Validador especializado para a entidade {@link ProfissionaisDTO}.
 * 
 * <p>
 * Esta classe estende a funcionalidade básica do {@link ValidatorBase}, adicionando validações específicas para a entidade {@link ProfissionaisDTO}.
 * Especificamente, verifica se o campo {@code cargo} contém um valor válido entre "Desenvolvedor", "Designer", "Suporte" ou "Tester".
 * </p>
 * 
 * <b>Métodos principais:</b>
 * <ul>
 *   <li>{@link #validate(ProfissionaisDTO)}: Realiza a validação completa dos dados de um profissional, incluindo a validação do cargo.</li>
 *   <li>{@link #capitalizeCargo(String)}: Normaliza o texto do cargo, garantindo que a primeira letra seja maiúscula.</li>
 * </ul>
 * 
 * <b>Considerações:</b>
 * <ul>
 *   <li>A classe utiliza uma lista de cargos válidos para assegurar que o valor do cargo esteja entre as opções permitidas.</li>
 *   <li>O método {@link #capitalizeCargo(String)} ajuda a padronizar o valor do cargo antes da validação.</li>
 * </ul>
 * 
 * @author Maicon
 * @version 1.0
 */
@Component
public class ProfissionaisValidator extends ValidatorBase<ProfissionaisDTO> {

    private final List<String> validCargos = List.of("Desenvolvedor", "Designer", "Suporte", "Tester");

    /**
     * Construtor para inicializar o validador com uma instância de {@link Validator}.
     *
     * @param validator Instância do validador do Jakarta Bean Validation.
     */
    public ProfissionaisValidator(Validator validator) {
        super(validator);
    }

    /**
     * Valida os dados do profissional fornecido, incluindo a validação do cargo.
     *
     * @param profissional Dados do profissional a serem validados.
     * @return ApiRestResponse com o resultado da validação do cargo.
     * @throws IllegalArgumentException se os dados do profissional forem inválidos.
     */
    public ApiRestResponse validate(ProfissionaisDTO profissional) {
        String normalizedCargo = capitalizeCargo(profissional.getCargo());
        if (!validCargos.contains(normalizedCargo)) {
            return new ApiRestResponse(false, "O cargo do profissional deve ser: Desenvolvedor, Designer, Suporte ou Tester.");
        }

        profissional.setCargo(normalizedCargo);
        return validateBase(profissional);
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
