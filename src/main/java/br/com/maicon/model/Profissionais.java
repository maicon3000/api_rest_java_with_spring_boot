package br.com.maicon.model;

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
 * Representa a entidade "Profissionais" no sistema.
 * 
 * <p>
 * Esta classe está mapeada para a tabela "profissionais" no banco de dados.
 * Ela contém informações sobre profissionais, incluindo seu nome, cargo, data de nascimento,
 * data de criação do registro, informação se o registro foi deletado e data de deleção do registro.
 * </p>
 * 
 * <b>Campos:</b>
 * <ul>
 *   <li><b>{@code id}:</b> Identificador único do profissional.</li>
 *   <li><b>{@code nome}:</b> Nome completo do profissional.</li>
 *   <li><b>{@code cargo}:</b> Cargo ou posição ocupada pelo profissional dentro da organização.</li>
 *   <li><b>{@code nascimento}:</b> Data de nascimento do profissional.</li>
 *   <li><b>{@code createdDate}:</b> Data em que o registro do profissional foi criado no sistema.</li>
 *   <li><b>{@code deleted}:</b> Campo que informa se o registro do profissional foi deletado no sistema.</li>
 *   <li><b>{@code deletedDate}:</b> Data em que o registro do profissional foi deletado do sistema.</li>
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
@Table(name = "profissionais")
public class Profissionais implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador único do profissional.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome completo do profissional.
     */
    @Column(name = "nome", nullable = false)
    private String nome;

    /**
     * Cargo ou posição ocupada pelo profissional dentro da organização.
     */
    @Column(name = "cargo", nullable = false)
    private String cargo;

    /**
     * Data de nascimento do profissional.
     */
    @Column(name = "nascimento", nullable = false)
    private Date nascimento;

    /**
     * Data em que o registro do profissional foi criado no sistema.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate;
    
    /**
     * Indica se o profissional foi marcado como deletado (exclusão lógica).
     */
    @Column(name = "deleted")
    private boolean deleted;
    
    /**
     * Data em que o profissional foi marcado como deletado.
     */
    @Column(name = "deleted_date")
    private Date deletedDate;
}
