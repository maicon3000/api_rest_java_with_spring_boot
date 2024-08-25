package br.com.maicon.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

/**
 * Representa a entidade "Profissionais" no sistema.
 * 
 * <p>
 * Esta classe está mapeada para a tabela "profissionais" no banco de dados.
 * Ela contém informações sobre profissionais, incluindo seu nome, cargo, data de nascimento
 * e a data de criação do registro.
 * </p>
 * 
 * <b>Campos:</b>
 * <ul>
 *   <li><b>{@code id}:</b> Identificador único do profissional.</li>
 *   <li><b>{@code nome}:</b> Nome completo do profissional.</li>
 *   <li><b>{@code cargo}:</b> Cargo ou posição ocupada pelo profissional dentro da organização.</li>
 *   <li><b>{@code nascimento}:</b> Data de nascimento do profissional.</li>
 *   <li><b>{@code createdDate}:</b> Data em que o registro do profissional foi criado no sistema.</li>
 * </ul>
 * 
 * <b>Considerações:</b>
 * <ul>
 *   <li>As anotações {@link Table} e {@link Column} são utilizadas para mapear explicitamente a classe e seus atributos para a estrutura do banco de dados, especialmente quando os nomes diferem.</li>
 *   <li>O uso do Lombok (@{@link Data}) simplifica o código, eliminando a necessidade de escrever manualmente os métodos getters e setters.</li>
 * </ul>
 * 
 * @author
 *   Maicon
 * @version
 *   1.0
 */
@Data
@Entity
@Table(name = "profissionais")
public class Profissionais implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador único do profissional.
     * Este campo é a chave primária e é gerado automaticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome do profissional.
     * Mapeado para a coluna "nome" na tabela "profissionais".
     */
    @NotBlank(message = "O nome do profissional não pode ser vazio ou apenas espaços em branco.")
    @Column(name = "nome", nullable = false)
    private String nome;

    /**
     * Cargo ou posição do profissional.
     * Mapeado para a coluna "cargo" na tabela "profissionais".
     */
    @Column(name = "cargo", nullable = false)
    private String cargo;

    /**
     * Data de nascimento do profissional.
     * Mapeado para a coluna "nascimento" na tabela "profissionais".
     */
    @Column(name = "nascimento", nullable = false)
    private Date nascimento;

    /**
     * Data em que o registro do profissional foi criado.
     * Mapeado para a coluna "created_date" na tabela "profissionais".
     */
    @Column(name = "created_date", nullable = false)
    private Date createdDate;
}
