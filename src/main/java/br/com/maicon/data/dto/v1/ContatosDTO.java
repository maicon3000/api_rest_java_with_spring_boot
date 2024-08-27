package br.com.maicon.data.dto.v1;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.maicon.models.Contatos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Data Transfer Object (DTO) para a entidade {@link Contatos}.
 * 
 * <p>
 * Esta classe é usada para transportar dados entre as camadas de aplicação,
 * representando as informações relacionadas aos contatos dos profissionais cadastrados.
 * </p>
 * 
 * <b>Campos:</b>
 * <ul>
 *   <li>{@link #id}: Identificador único do contato.</li>
 *   <li>{@link #nome}: Nome completo do contato.</li>
 *   <li>{@link #contato}: Informações de contato, como telefone ou email.</li>
 *   <li>{@link #createdDate}: Data em que o registro do contato foi criado no sistema.</li>
 *   <li>{@link #profissionalId}: Identificador do profissional associado a este contato.</li>
 * </ul>
 * 
 * <b>Considerações:</b>
 * <ul>
 *   <li>Esta classe utiliza a anotação {@link lombok.Data}, que gera automaticamente os métodos getters, setters, 
 *       {@code equals()}, {@code hashCode()} e {@code toString()}.</li>
 *   <li>O campo {@link #createdDate} é armazenado como um objeto {@link Date} e configurado para ser serializado 
 *       em formato JSON com o padrão ISO 8601.</li>
 *   <li>O campo {@link #profissionalId} é uma chave estrangeira que referencia o profissional associado a este contato.</li>
 * </ul>
 * 
 * @author Maicon
 * @version 1.0
 */
@Data
public class ContatosDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador único do contato.
     */
    private Long id;

    /**
     * Nome completo do contato.
     * 
     * <p>Anotação {@link NotBlank}: Garante que o campo {@code nome} não seja nulo, 
     * vazio ou composto apenas de espaços em branco. Se essa condição não for atendida, 
     * uma exceção de validação será lançada.</p>
     */
    @NotBlank
    private String nome;

    /**
     * Informações de contato, como telefone ou email.
     * 
     * <p>Anotação {@link NotBlank}: Garante que o campo {@code contato} não seja nulo, 
     * vazio ou composto apenas de espaços em branco. Se essa condição não for atendida, 
     * uma exceção de validação será lançada.</p>
     */
    @NotBlank
    private String contato;

    /**
     * Data em que o registro do contato foi criado no sistema.
     * 
     * <p>Anotação {@link JsonFormat}: Define o formato da data de criação no JSON. 
     * O formato especificado é {@code "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"} com o fuso horário 
     * configurado para GMT-3. Isso garante que a data seja serializada e desserializada corretamente 
     * entre o formato JSON e o objeto Java, respeitando o fuso horário configurado.</p>
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "GMT-3")
    private Date createdDate;

    /**
     * Identificador do profissional associado a este contato.
     * 
     * <p>Anotação {@link NotNull}: Garante que o campo {@code profissionalId} não seja nulo. 
     * Se essa condição não for atendida, uma exceção de validação será lançada.</p>
     */
    @NotNull
    private Long profissionalId;

    /**
     * Método para definir a data de criação, ignorado durante a serialização JSON.
     * 
     * @param createdDate Data de criação do contato.
     */
    @JsonIgnore
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
