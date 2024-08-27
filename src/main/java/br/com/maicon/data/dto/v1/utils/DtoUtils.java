package br.com.maicon.data.dto.v1.utils;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Classe utilitária para manipulação e conversão de objetos DTO.
 * 
 * <p>Esta classe fornece métodos para filtrar campos específicos de qualquer objeto DTO e 
 * para converter o objeto em um {@link Map} para acesso dinâmico às suas propriedades.</p>
 * 
 * <p>Os métodos desta classe são estáticos e podem ser utilizados diretamente sem a necessidade de instanciar a classe.</p>
 * 
 * <b>Métodos principais:</b>
 * <ul>
 *   <li>{@link #filterFields(T, List)}: Filtra os campos de um objeto DTO de acordo com uma lista de campos especificados.</li>
 *   <li>{@link #convertToMap(T)}: Converte um objeto DTO em um {@link Map} com suas propriedades como chaves.</li>
 * </ul>
 * 
 * @author Maicon
 * @version 1.0
 */
public class DtoUtils {

    /**
     * Filtra os campos de um objeto DTO com base em uma lista de campos especificados.
     * 
     * <p>Esse método converte o objeto DTO em um {@link Map} e retém apenas 
     * as chaves (campos) especificadas na lista fornecida.</p>
     * 
     * @param <T> O tipo do objeto DTO.
     * @param dto Objeto do tipo {@link T} a ser filtrado.
     * @param fields Lista de campos a serem incluídos no resultado.
     * @return Um {@link Map} contendo apenas os campos especificados do objeto {@link T}.
     */
    public static <T> Map<String, Object> filterFields(T dto, List<String> fields) {
        Map<String, Object> filteredMap = convertToMap(dto);
        filteredMap.keySet().retainAll(fields);
        return filteredMap;
    }

    /**
     * Converte um objeto DTO em um {@link Map} com suas propriedades como chaves.
     * 
     * <p>Esse método é utilizado para permitir o acesso dinâmico aos campos do objeto DTO
     * sem a necessidade de conhecer seus nomes em tempo de compilação.</p>
     * 
     * @param <T> O tipo do objeto DTO.
     * @param dto Objeto do tipo {@link T} a ser convertido.
     * @return Um {@link Map} representando as propriedades do objeto {@link T}.
     */
    public static <T> Map<String, Object> convertToMap(T dto) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(dto, new TypeReference<Map<String, Object>>() {});
    }
}
