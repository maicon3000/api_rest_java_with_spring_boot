package br.com.maicon.data.dto.v1;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.maicon.model.Profissionais;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
     * 
     * <p>Anotação {@link NotBlank}: Garante que o campo {@code nome} não seja nulo, 
	 * vazio ou composto apenas de espaços em branco. Se essa condição não for atendida, 
	 * uma exceção de validação será lançada.</p>
	 */
    @NotBlank
    private String nome;

    /**
     * Cargo ou posição ocupada pelo profissional dentro da organização.
     * 
     * <p>Anotação {@link NotBlank}: Garante que o campo {@code cargo} não seja nulo, 
	 * vazio ou composto apenas de espaços em branco. Se essa condição não for atendida, 
	 * uma exceção de validação será lançada.</p>
	 */
    @NotBlank
    private String cargo;

    /**
     * Data de nascimento do profissional.
     * 
     * <p>Anotação {@link NotNull}: Garante que o campo {@code nascimento} não seja nulo. 
	 * Se esse campo for nulo, uma exceção de validação será lançada.</p>
	 * 
	 * <p>Anotação {@link JsonFormat}: Define o formato da data de nascimento no JSON. 
	 * O formato especificado é {@code "yyyy-MM-dd"}. Isso garante que a data seja serializada e 
	 * desserializada corretamente entre o formato JSON e o objeto Java.</p>
	 */
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date nascimento;

    /**
     * Data em que o registro do profissional foi criado no sistema.
     * 
     * <p>Anotação {@link JsonFormat}: Define o formato da data de criação no JSON. 
	 * O formato especificado é {@code "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"} com o fuso horário 
	 * configurado para GMT-3. Isso garante que a data seja serializada e desserializada corretamente 
	 * entre o formato JSON e o objeto Java, respeitando o fuso horário configurado.</p>
	 */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "GMT-3")
    private Date createdDate;

    /**
     * Indica se o profissional foi marcado como deletado (exclusão lógica).
     * 
     * <p>Anotação {@link JsonIgnore}: Garante que o campo {@code deleted} não seja 
	 * serializado/deserializado para JSON, ocultando-o do cliente.</p>
	 */
    @JsonIgnore
    private boolean deleted;

    /**
     * Data em que o profissional foi marcado como deletado.
     * 
     * <p>Anotação {@link JsonIgnore}: Garante que o campo {@code deleted} não seja 
	 * serializado/deserializado para JSON, ocultando-o do cliente.</p>
	 */
    @JsonIgnore
    private Date deletedDate;
    
    /**
     * Define a data de criação do registro.
     * 
     * <p>Este método é anotado com {@link JsonIgnore} para garantir que a data de criação não seja 
     * incluída na serialização JSON, impedindo que o cliente altere este valor ao enviar uma 
     * requisição para o servidor.</p>
     * 
     * @param createdDate A data de criação do registro.
     */
    @JsonIgnore
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
