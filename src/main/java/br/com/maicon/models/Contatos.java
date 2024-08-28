package br.com.maicon.models;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

/**
 * Representa a entidade "Contatos" no sistema.
 * 
 * <p>
 * Esta classe está mapeada para a tabela "contatos" no banco de dados.
 * Ela contém informações sobre os contatos dos profissionais, incluindo nome, informações de contato, data de criação,
 * e a referência ao profissional associado.
 * </p>
 * 
 * <b>Campos:</b>
 * <ul>
 *   <li><b>{@code id}:</b> Identificador único do contato.</li>
 *   <li><b>{@code nome}:</b> Nome completo do contato.</li>
 *   <li><b>{@code contato}:</b> Informações de contato, como telefone ou email.</li>
 *   <li><b>{@code createdDate}:</b> Data em que o registro do contato foi criado no sistema.</li>
 *   <li><b>{@code profissionalId}:</b> Identificador do profissional associado a este contato.</li>
 * </ul>
 * 
 * <b>Considerações:</b>
 * <ul>
 *   <li>As anotações {@link Table} e {@link Column} são utilizadas para mapear explicitamente a classe e seus atributos para a estrutura do banco de dados, especialmente quando os nomes diferem.</li>
 *   <li>O uso do Lombok (@{@link Data}) simplifica o código, eliminando a necessidade de escrever manualmente os métodos getters e setters.</li>
 * </ul>
 * 
 * @author Maicon
 * @version 1.0
 */
@Data
@Entity
@Table(name = "Contatos")
public class Contatos implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador único do contato.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome completo do contato.
     */
    @Column(name = "nome", nullable = false)
    private String nome;

    /**
     * Informações de contato, como telefone ou email.
     */
    @Column(name = "contato", nullable = false)
    private String contato;

    /**
     * Data em que o registro do contato foi criado no sistema.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate;
    
    /**
     * Identificador do profissional associado a este contato.
     */
    @Column(name = "profissional_id", nullable = false)
    private Long profissionalId;
    
    /**
     * Identificador de deleção do profissional.
     */
    @Column(name = "deleted_profissional")
    private Boolean deletedProfissional;
}
