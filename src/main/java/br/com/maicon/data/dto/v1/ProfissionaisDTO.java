package br.com.maicon.data.dto.v1;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.maicon.model.Profissionais;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Data Transfer Object (DTO) para a entidade {@link Profissionais}.
 * 
 * <p>
 * Esta classe é usada para transportar dados entre as camadas de aplicação,
 * representando as informações relacionadas aos profissionais cadastrados.
 * </p>
 * 
 * <b>Campos:</b>
 * <ul>
 *   <li>{@link #id}: Identificador único do profissional.</li>
 *   <li>{@link #nome}: Nome completo do profissional.</li>
 *   <li>{@link #cargo}: Cargo ou posição ocupada pelo profissional dentro da organização.</li>
 *   <li>{@link #nascimento}: Data de nascimento do profissional.</li>
 *   <li>{@link #createdDate}: Data em que o registro do profissional foi criado no sistema.</li>
 *   <li>{@link #deleted}: Indica se o profissional foi marcado como deletado (exclusão lógica).</li>
 *   <li>{@link #deletedDate}: Data em que o profissional foi marcado como deletado.</li>
 * </ul>
 * 
 * <b>Considerações:</b>
 * <ul>
 *   <li>Esta classe utiliza a anotação {@link lombok.Data}, que gera automaticamente os métodos getters, setters, 
 *       {@code equals()}, {@code hashCode()} e {@code toString()}.</li>
 *   <li>O campo {@link #deleted} é usado para indicar se o registro foi logicamente excluído.</li>
 *   <li>Os campos de data, como {@link #nascimento}, {@link #createdDate}, e {@link #deletedDate}, 
 *       são armazenados como objetos {@link Date}.</li>
 * </ul>
 * 
 * @author Maicon
 * @version 1.0
 */
@Data
public class ProfissionaisDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador único do profissional.
     */
    private Long id;

    /**
     * Nome completo do profissional.
     */
    @NotBlank(message = "O nome do profissional não pode ser vazio ou nulo.")
    private String nome;

    /**
     * Cargo ou posição ocupada pelo profissional dentro da organização.
     */
    private String cargo;

    /**
     * Data de nascimento do profissional.
     */
    private Date nascimento;

    /**
     * Data em que o registro do profissional foi criado no sistema.
     */
    private Date createdDate;

    /**
     * Indica se o profissional foi marcado como deletado (exclusão lógica).
     */
    @JsonIgnore
    private boolean deleted;

    /**
     * Data em que o profissional foi marcado como deletado.
     */
    @JsonIgnore
    private Date deletedDate;
}
