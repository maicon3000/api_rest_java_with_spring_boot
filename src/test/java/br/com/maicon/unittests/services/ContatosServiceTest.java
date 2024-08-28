package br.com.maicon.unittests.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.maicon.data.dto.v1.ContatosDTO;
import br.com.maicon.exception.ResourceNotFoundException;
import br.com.maicon.mapper.DozerMapper;
import br.com.maicon.models.Contatos;
import br.com.maicon.models.Profissionais;
import br.com.maicon.repositories.ContatosRepository;
import br.com.maicon.repositories.ProfissionaisRepository;
import br.com.maicon.services.ContatosService;
import br.com.maicon.services.validation.base.ValidatorBase;
import br.com.maicon.utils.ApiRestResponse;

class ContatosServiceTest {

    private static final Long MOCK_ID = 1L;
    private static final String MOCK_NAME = "Nome Teste";
    private static final String MOCK_CONTACT = "Contato Teste";
    private static final String VALIDATION_SUCCESS = "Validado com sucesso";
    private static final String VALIDATION_FAILURE = "Falha na validação";
    private static final String CREATE_SUCCESS_MESSAGE = "Contato com ID 1 cadastrado com sucesso!";
    private static final String UPDATE_SUCCESS_MESSAGE = "Cadastro alterado com sucesso!";
    private static final String DELETE_SUCCESS_MESSAGE = "Contato deletado com sucesso!";
    @Mock
    private ContatosRepository contatosRepository;

    @Mock
    private ProfissionaisRepository profissionaisRepository;

    @Mock
    private ValidatorBase<ContatosDTO> validator;

    @InjectMocks
    private ContatosService contatosService;

    private ContatosDTO contatoDto;
    private Contatos contato;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        contatoDto = new ContatosDTO();
        contatoDto.setId(MOCK_ID);
        contatoDto.setNome(MOCK_NAME);
        contatoDto.setContato(MOCK_CONTACT);
        contatoDto.setProfissionalId(MOCK_ID);
        contatoDto.setCreatedDate(new Date());

        contato = DozerMapper.parseObject(contatoDto, Contatos.class);
    }

    @Test
    void testFindAll() {
        // Arrange
        when(contatosRepository.findAll()).thenReturn(List.of(contato));

        // Act
        List<ContatosDTO> result = contatosService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(contatoDto.getId(), result.get(0).getId());
    }

    @Test
    void testFindAllWithQuery() {
        // Arrange
        when(contatosRepository.findByQuery(MOCK_NAME)).thenReturn(List.of(contato));

        // Act
        List<ContatosDTO> result = contatosService.findAll(MOCK_NAME);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(contatoDto.getId(), result.get(0).getId());
    }
    
    @Test
    void testFindAllWithEmptyQuery() {
        // Arrange
        when(contatosRepository.findAll()).thenReturn(List.of(contato));

        // Act
        List<ContatosDTO> result = contatosService.findAll("");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(contatoDto.getId(), result.get(0).getId());
    }

    @Test
    void testFindAllWithNullQuery() {
        // Arrange
        when(contatosRepository.findAll()).thenReturn(List.of(contato));

        // Act
        List<ContatosDTO> result = contatosService.findAll(null);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(contatoDto.getId(), result.get(0).getId());
    }

    @Test
    void testFindById() {
        // Arrange
        when(contatosRepository.findById(MOCK_ID)).thenReturn(Optional.of(contato));

        // Act
        ContatosDTO result = contatosService.findById(MOCK_ID);

        // Assert
        assertNotNull(result);
        assertEquals(contatoDto.getId(), result.getId());
    }

    @Test
    void testFindById_ResourceNotFoundException() {
        // Arrange
        when(contatosRepository.findById(MOCK_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> contatosService.findById(MOCK_ID));
    }

    @Test
    void testCreate() {
        // Arrange
        Profissionais mockProfissional = new Profissionais();
        mockProfissional.setId(MOCK_ID);
        mockProfissional.setNome(MOCK_NAME);

        when(profissionaisRepository.findByIdAndActive(MOCK_ID)).thenReturn(Optional.of(mockProfissional));

        when(validator.validateBase(any(ContatosDTO.class)))
            .thenReturn(new ApiRestResponse(true, VALIDATION_SUCCESS));

        ContatosDTO contatoDto = new ContatosDTO();
        contatoDto.setProfissionalId(MOCK_ID);

        when(contatosRepository.save(any(Contatos.class))).thenAnswer(invocation -> {
            Contatos argument = invocation.getArgument(0);
            argument.setId(MOCK_ID);
            return argument;
        });

        // Act
        ApiRestResponse response = contatosService.create(contatoDto);

        // Assert
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals(CREATE_SUCCESS_MESSAGE, response.getMessage());
    }

    @Test
    void testCreate_InvalidData() {
        // Arrange
        when(validator.validateBase(any(ContatosDTO.class)))
            .thenReturn(new ApiRestResponse(false, VALIDATION_FAILURE));

        // Act
        ApiRestResponse response = contatosService.create(contatoDto);

        // Assert
        verify(validator, times(1)).validateBase(contatoDto);
        assertNotNull(response);
        assertFalse(response.isSuccess());
        assertEquals(VALIDATION_FAILURE, response.getMessage());
    }

    @Test
    void testCreate_ResourceNotFoundException() {
        // Arrange
        when(validator.validateBase(any(ContatosDTO.class)))
            .thenReturn(new ApiRestResponse(true, VALIDATION_SUCCESS));
        when(profissionaisRepository.findByIdAndActive(MOCK_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> contatosService.create(contatoDto));
    }

    @Test
    void testUpdate() {
        // Arrange
        when(validator.validateBase(any(ContatosDTO.class)))
            .thenReturn(new ApiRestResponse(true, VALIDATION_SUCCESS));
        
        when(contatosRepository.findByIdAndActive(MOCK_ID)).thenReturn(Optional.of(contato));
        
        when(contatosRepository.save(any(Contatos.class))).thenReturn(contato);

        // Act
        ApiRestResponse response = contatosService.update(contatoDto);

        // Assert
        verify(validator, times(1)).validateBase(contatoDto);
        verify(contatosRepository, times(1)).findByIdAndActive(MOCK_ID);
        verify(contatosRepository, times(1)).save(any(Contatos.class));

        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals(UPDATE_SUCCESS_MESSAGE, response.getMessage());
    }


    @Test
    void testUpdate_InvalidData() {
        // Arrange
        when(validator.validateBase(any(ContatosDTO.class)))
            .thenReturn(new ApiRestResponse(false, VALIDATION_FAILURE));

        // Act
        ApiRestResponse response = contatosService.update(contatoDto);

        // Assert
        verify(validator, times(1)).validateBase(contatoDto);
        assertNotNull(response);
        assertFalse(response.isSuccess());
        assertEquals(VALIDATION_FAILURE, response.getMessage());
    }

    @Test
    void testUpdate_ResourceNotFoundException() {
        // Arrange
        when(validator.validateBase(any(ContatosDTO.class)))
            .thenReturn(new ApiRestResponse(true, VALIDATION_SUCCESS));
        when(contatosRepository.findById(MOCK_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> contatosService.update(contatoDto));
    }

    @Test
    void testDelete() {
        // Arrange
        Contatos mockContato = new Contatos();
        mockContato.setId(MOCK_ID);

        when(contatosRepository.findByIdAndActive(MOCK_ID)).thenReturn(Optional.of(mockContato));

        // Act
        ApiRestResponse response = contatosService.delete(MOCK_ID);

        // Assert
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals(DELETE_SUCCESS_MESSAGE, response.getMessage());

        verify(contatosRepository, times(1)).deleteById(MOCK_ID);
    }

    @Test
    void testDelete_ResourceNotFoundException() {
        // Arrange
        when(contatosRepository.existsById(MOCK_ID)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> contatosService.delete(MOCK_ID));
    }
}
