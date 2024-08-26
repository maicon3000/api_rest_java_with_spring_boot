package br.com.maicon.mapper;

import java.util.ArrayList;
import java.util.List;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

/**
 * Classe utilitária para mapeamento de objetos usando o Dozer.
 * 
 * <p>
 * Esta classe fornece métodos para converter um objeto de um tipo para outro,
 * bem como para converter listas de objetos. O mapeamento é realizado pelo
 * framework Dozer, que permite a transformação de objetos complexos em objetos
 * de diferentes tipos.
 * </p>
 * 
 * <b>Métodos principais:</b>
 * <ul>
 *   <li>{@link #parseObject(Object, Class)}: Converte um único objeto de um tipo para outro.</li>
 *   <li>{@link #parseListObjects(List, Class)}: Converte uma lista de objetos de um tipo para outro.</li>
 * </ul>
 * 
 * <b>Considerações:</b>
 * <ul>
 *   <li>A classe utiliza uma instância singleton de {@link Mapper}, criada através do {@link DozerBeanMapperBuilder}.</li>
 *   <li>Os métodos desta classe são estáticos, facilitando o acesso e uso em toda a aplicação.</li>
 *   <li>O framework Dozer é utilizado para mapear automaticamente os campos entre objetos, desde que os nomes dos campos sejam compatíveis ou estejam configurados adequadamente.</li>
 * </ul>
 * 
 * @see Mapper
 * @see DozerBeanMapperBuilder
 * 
 * @author Maicon
 * @version 1.0
 */
public class DozerMapper {
    
    private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();
    
    /**
     * Converte um objeto de um tipo para outro.
     * 
     * <p>Este método usa o Dozer para mapear os campos de um objeto de origem
     * para um objeto de destino do tipo especificado.</p>
     * 
     * @param <O> O tipo do objeto de origem
     * @param <D> O tipo do objeto de destino
     * @param origin O objeto de origem a ser convertido
     * @param destination A classe do objeto de destino
     * @return Um novo objeto do tipo de destino, com os campos mapeados a partir do objeto de origem
     */
    public static <O, D> D parseObject(O origin, Class<D> destination) {
        return mapper.map(origin, destination);
    }
    
    /**
     * Converte uma lista de objetos de um tipo para outro.
     * 
     * <p>Este método itera sobre uma lista de objetos de origem, convertendo
     * cada um deles para o tipo especificado de destino usando o Dozer.</p>
     * 
     * @param <O> O tipo dos objetos de origem
     * @param <D> O tipo dos objetos de destino
     * @param origin A lista de objetos de origem a ser convertida
     * @param destination A classe dos objetos de destino
     * @return Uma nova lista de objetos do tipo de destino, com os campos mapeados a partir dos objetos de origem
     */
    public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
        List<D> destinationObjects = new ArrayList<D>();
        for (O o : origin) {
            destinationObjects.add(mapper.map(o, destination));
        }
        return destinationObjects;
    }
}
