package br.com.maicon.unittests.mapper.mocks;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.maicon.data.dto.v1.ProfissionaisDTO;
import br.com.maicon.models.Profissionais;

/**
 * Classe utilitária para criar instâncias de objetos de teste relacionados a {@link Profissionais} e {@link ProfissionaisDTO}.
 * 
 * <p>Esta classe fornece métodos para criar instâncias únicas e listas de objetos de teste, com dados simulados
 * para uso em testes unitários. Ela também fornece uma data fixa para consistência nos testes.</p>
 * 
 * @author Maicon
 */
public class MockProfissionais {

    /**
     * Cria e retorna uma instância de {@link Profissionais} com dados simulados.
     * 
     * <p>Este método utiliza o valor {@code 0} como padrão para preencher os campos da entidade.
     * Para criar uma instância com um valor diferente, utilize {@link #mockEntity(Integer)}.</p>
     * 
     * @return Uma instância de {@link Profissionais} com dados padrão.
     */
    public Profissionais mockEntity() {
        return mockEntity(0);
    }
    
    /**
     * Cria e retorna uma instância de {@link ProfissionaisDTO} com dados simulados.
     * 
     * <p>Este método utiliza o valor {@code 0} como padrão para preencher os campos do DTO.
     * Para criar uma instância com um valor diferente, utilize {@link #mockVO(Integer)}.</p>
     * 
     * @return Uma instância de {@link ProfissionaisDTO} com dados padrão.
     */
    public ProfissionaisDTO mockVO() {
        return mockVO(0);
    }
    
    /**
     * Cria e retorna uma lista de instâncias de {@link Profissionais} com dados simulados.
     * 
     * <p>A lista contém 11 instâncias, com valores de {@code 0} a {@code 10} para os campos,
     * representando diferentes profissionais.</p>
     * 
     * @return Uma lista de instâncias de {@link Profissionais}.
     */
    public List<Profissionais> mockEntityList() {
        List<Profissionais> profissionais = new ArrayList<Profissionais>();
        for (int i = 0; i <= 10; i++) {
        	profissionais.add(mockEntity(i));
        }
        return profissionais;
    }

    /**
     * Cria e retorna uma lista de instâncias de {@link ProfissionaisDTO} com dados simulados.
     * 
     * <p>A lista contém 14 instâncias, com valores de {@code 0} a {@code 13} para os campos,
     * representando diferentes profissionais.</p>
     * 
     * @return Uma lista de instâncias de {@link ProfissionaisDTO}.
     */
    public List<ProfissionaisDTO> mockVOList() {
        List<ProfissionaisDTO> profissionais = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
        	profissionais.add(mockVO(i));
        }
        return profissionais;
    }
    
    /**
     * Cria e retorna uma instância de {@link Profissionais} com dados simulados, utilizando o valor fornecido para preencher os campos.
     * 
     * @param number O valor utilizado para preencher os campos da entidade (ID, nome, cargo, etc.).
     * @return Uma instância de {@link Profissionais} com os dados simulados.
     */
    public Profissionais mockEntity(Integer number) {
        Profissionais profissional = new Profissionais();
        profissional.setId(number.longValue());
        profissional.setNome("Nome Teste " + number);
        profissional.setCargo("Cargo Teste " + number);
        profissional.setNascimento(fixedDate());
        profissional.setCreatedDate(fixedDate());
        profissional.setDeleted(false);
        profissional.setDeletedDate(fixedDate());
        return profissional;
    }

    /**
     * Cria e retorna uma instância de {@link ProfissionaisDTO} com dados simulados, utilizando o valor fornecido para preencher os campos.
     * 
     * @param number O valor utilizado para preencher os campos do DTO (ID, nome, cargo, etc.).
     * @return Uma instância de {@link ProfissionaisDTO} com os dados simulados.
     */
    public ProfissionaisDTO mockVO(Integer number) {
    	ProfissionaisDTO profissional = new ProfissionaisDTO();
        profissional.setId(number.longValue());
        profissional.setNome("Nome Teste " + number);
        profissional.setCargo("Cargo Teste " + number);
        profissional.setNascimento(fixedDate());
        profissional.setCreatedDate(fixedDate());
        profissional.setDeleted(false);
        profissional.setDeletedDate(fixedDate());
        return profissional;
    }
    
    /**
     * Retorna uma instância de {@link Date} com uma data fixa, utilizada para consistência nos testes.
     * 
     * <p>A data retornada é sempre "2000-01-01T00:00:00.000+00:00", garantindo que os testes tenham
     * uma referência de data consistente.</p>
     * 
     * @return Uma instância de {@link Date} com uma data fixa.
     */
    public Date fixedDate() {
    	DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        OffsetDateTime fixedDate = OffsetDateTime.parse("2000-01-01T00:00:00.000+00:00", formatter);
        return Date.from(fixedDate.toInstant());
    }
}
