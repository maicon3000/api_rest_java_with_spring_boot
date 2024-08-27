package br.com.maicon.data.dto.v1.utils;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.maicon.data.dto.v1.ProfissionaisDTO;

/**
 * Classe utilitária para manipulação e conversão de objetos {@link ProfissionaisDTO}.
 * 
 * <p>Esta classe fornece métodos para filtrar campos específicos de um {@link ProfissionaisDTO} e 
 * para converter o objeto em um {@link Map} para acesso dinâmico às suas propriedades.</p>
 * 
 * <p>Os métodos desta classe são estáticos e podem ser utilizados diretamente sem a necessidade de instanciar a classe.</p>
 * 
 * <b>Métodos principais:</b>
 * <ul>
 *   <li>{@link #filterFields(ProfissionaisDTO, List)}: Filtra os campos de um {@link ProfissionaisDTO} de acordo com uma lista de campos especificados.</li>
 *   <li>{@link #convertToMap(ProfissionaisDTO)}: Converte um {@link ProfissionaisDTO} em um {@link Map} com suas propriedades como chaves.</li>
 * </ul>
 * 
 * @author Maicon
 * @version 1.0
 */
public class DtoUtils {

	/**
	 * Filtra os campos de um objeto {@link ProfissionaisDTO} com base em uma lista de campos especificados.
	 * 
	 * <p>Esse método converte o objeto {@link ProfissionaisDTO} em um {@link Map} e retém apenas 
	 * as chaves (campos) especificadas na lista fornecida.</p>
	 * 
	 * @param profissional Objeto do tipo {@link ProfissionaisDTO} a ser filtrado.
	 * @param fields Lista de campos a serem incluídos no resultado.
	 * @return Um {@link Map} contendo apenas os campos especificados do objeto {@link ProfissionaisDTO}.
	 */
	public static Map<String, Object> filterFields(ProfissionaisDTO profissional, List<String> fields) {
        Map<String, Object> filteredMap = convertToMap(profissional);
        filteredMap.keySet().retainAll(fields);
        return filteredMap;
    }

	/**
	 * Converte um objeto {@link ProfissionaisDTO} em um {@link Map} com suas propriedades como chaves.
	 * 
	 * <p>Esse método é utilizado para permitir o acesso dinâmico aos campos do objeto {@link ProfissionaisDTO}
	 * sem a necessidade de conhecer seus nomes em tempo de compilação.</p>
	 * 
	 * @param profissional Objeto do tipo {@link ProfissionaisDTO} a ser convertido.
	 * @return Um {@link Map} representando as propriedades do objeto {@link ProfissionaisDTO}.
	 */
    public static Map<String, Object> convertToMap(ProfissionaisDTO profissional) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(profissional, new TypeReference<Map<String, Object>>() {});
    }
}

