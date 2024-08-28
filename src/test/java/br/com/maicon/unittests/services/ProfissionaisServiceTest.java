package br.com.maicon.unittests.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.maicon.data.dto.v1.ProfissionaisDTO;
import br.com.maicon.exception.ResourceNotFoundException;
import br.com.maicon.mapper.DozerMapper;
import br.com.maicon.models.Contatos;
import br.com.maicon.models.Profissionais;
import br.com.maicon.repositories.ContatosRepository;
import br.com.maicon.repositories.ProfissionaisRepository;
import br.com.maicon.services.ProfissionaisService;
import br.com.maicon.services.validation.ProfissionaisValidator;
import br.com.maicon.utils.ApiRestResponse;

class ProfissionaisServiceTest {

    private static final Long MOCK_ID = 1L;
    private static final String MOCK_NAME = "Nome Teste";
    private static final String MOCK_POSITION = "Cargo Teste";
    private static final String VALIDATION_SUCCESS = "Validado com sucesso";
    private static final String VALIDATION_FAILURE = "Falha na validação";
    private static final String CREATE_SUCCESS_MESSAGE = "Profissional com ID 1 cadastrado com sucesso!";
    private static final String UPDATE_SUCCESS_MESSAGE = "Cadastro alterado com sucesso!";
    private static final String DELETE_SUCCESS_MESSAGE = "Profissional excluído com sucesso!";

    @Mock
    private ProfissionaisRepository profissionaisRepository;
    
    @Mock
    private ContatosRepository contatosRepository;

    @Mock
    private ProfissionaisValidator profissionaisValidator;

    @InjectMocks
    private ProfissionaisService profissionaisService;

    private ProfissionaisDTO mockProfissionalDto;
    private Profissionais mockProfissional;
    private Contatos mockContato1;
    private Contatos mockContato2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockProfissionalDto = new ProfissionaisDTO();
        mockProfissionalDto.setId(MOCK_ID);
        mockProfissionalDto.setNome(MOCK_NAME);
        mockProfissionalDto.setCargo(MOCK_POSITION);
        mockProfissionalDto.setNascimento(new Date());
        mockProfissionalDto.setCreatedDate(new Date());
        mockProfissionalDto.setDeleted(false);
        mockProfissionalDto.setDeletedDate(null);

        mockProfissional = DozerMapper.parseObject(mockProfissionalDto, Profissionais.class);
        
        mockProfissional = new Profissionais();
        mockProfissional.setId(MOCK_ID);
        mockProfissional.setNome(MOCK_NAME);
        mockProfissional.setDeleted(false);

        mockContato1 = new Contatos();
        mockContato1.setId(1L);
        mockContato1.setProfissionalId(MOCK_ID);
        mockContato1.setDeletedProfissional(false);

        mockContato2 = new Contatos();
        mockContato2.setId(2L);
        mockContato2.setProfissionalId(MOCK_ID);
        mockContato2.setDeletedProfissional(false);
    }

    @Test
    void testFindAll() {
        // Arrange
        when(profissionaisRepository.findAllActive()).thenReturn(List.of(mockProfissional));

        // Act
        List<ProfissionaisDTO> result = profissionaisService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(mockProfissionalDto.getId(), result.get(0).getId());
    }

    @Test
    void testFindAllWithQuery() {
        // Arrange
        when(profissionaisRepository.findByQuery(MOCK_NAME)).thenReturn(List.of(mockProfissional));

        // Act
        List<ProfissionaisDTO> result = profissionaisService.findAll(MOCK_NAME);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(mockProfissionalDto.getId(), result.get(0).getId());
    }
    
    @Test
    void testFindAllWithEmptyQuery() {
        // Arrange
        when(profissionaisRepository.findAllActive()).thenReturn(List.of(mockProfissional));

        // Act
        List<ProfissionaisDTO> result = profissionaisService.findAll("");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(mockProfissionalDto.getId(), result.get(0).getId());
    }

    @Test
    void testFindAllWithNullQuery() {
        // Arrange
        when(profissionaisRepository.findAllActive()).thenReturn(List.of(mockProfissional));

        // Act
        List<ProfissionaisDTO> result = profissionaisService.findAll(null);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(mockProfissionalDto.getId(), result.get(0).getId());
    }

    @Test
    void testFindById() {
        // Arrange
        when(profissionaisRepository.findByIdAndActive(MOCK_ID)).thenReturn(Optional.of(mockProfissional));

        // Act
        ProfissionaisDTO result = profissionaisService.findById(MOCK_ID);

        // Assert
        assertNotNull(result);
        assertEquals(mockProfissionalDto.getId(), result.getId());
    }

    @Test
    void testFindById_ResourceNotFoundException() {
        // Arrange
        when(profissionaisRepository.findByIdAndActive(MOCK_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> profissionaisService.findById(MOCK_ID));
    }

    @Test
    void testCreate() {
        // Arrange
        when(profissionaisValidator.validate(any(ProfissionaisDTO.class)))
            .thenReturn(new ApiRestResponse(true, VALIDATION_SUCCESS));
        when(profissionaisRepository.save(any(Profissionais.class))).thenReturn(mockProfissional);

        // Act
        ApiRestResponse response = profissionaisService.create(mockProfissionalDto);

        // Assert
        verify(profissionaisValidator, times(1)).validate(mockProfissionalDto);
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals(CREATE_SUCCESS_MESSAGE, response.getMessage());
    }

    @Test
    void testCreate_InvalidData() {
        // Arrange
        when(profissionaisValidator.validate(any(ProfissionaisDTO.class)))
            .thenReturn(new ApiRestResponse(false, VALIDATION_FAILURE));

        // Act
        ApiRestResponse response = profissionaisService.create(mockProfissionalDto);

        // Assert
        verify(profissionaisValidator, times(1)).validate(mockProfissionalDto);
        assertNotNull(response);
        assertFalse(response.isSuccess());
        assertEquals(VALIDATION_FAILURE, response.getMessage());
    }

    @Test
    void testUpdate() {
        // Arrange
        when(profissionaisValidator.validate(any(ProfissionaisDTO.class)))
            .thenReturn(new ApiRestResponse(true, VALIDATION_SUCCESS));
        when(profissionaisRepository.findByIdAndActive(MOCK_ID)).thenReturn(Optional.of(mockProfissional));
        when(profissionaisRepository.save(any(Profissionais.class))).thenReturn(mockProfissional);

        // Act
        ApiRestResponse response = profissionaisService.update(mockProfissionalDto);

        // Assert
        verify(profissionaisValidator, times(1)).validate(mockProfissionalDto);
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals(UPDATE_SUCCESS_MESSAGE, response.getMessage());
    }

    @Test
    void testUpdate_InvalidData() {
        // Arrange
        when(profissionaisValidator.validate(any(ProfissionaisDTO.class)))
            .thenReturn(new ApiRestResponse(false, VALIDATION_FAILURE));

        // Act
        ApiRestResponse response = profissionaisService.update(mockProfissionalDto);

        // Assert
        verify(profissionaisValidator, times(1)).validate(mockProfissionalDto);
        assertNotNull(response);
        assertFalse(response.isSuccess());
        assertEquals(VALIDATION_FAILURE, response.getMessage());
    }

    @Test
    void testUpdate_ResourceNotFoundException() {
        // Arrange
        when(profissionaisValidator.validate(any(ProfissionaisDTO.class)))
            .thenReturn(new ApiRestResponse(true, VALIDATION_SUCCESS));
        when(profissionaisRepository.findByIdAndActive(MOCK_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> profissionaisService.update(mockProfissionalDto));
    }

    @Test
    void testDelete() {
        // Arrange
        List<Contatos> mockContatosList = List.of(mockContato1, mockContato2);

        when(profissionaisRepository.findByIdAndActive(MOCK_ID)).thenReturn(Optional.of(mockProfissional));
        when(contatosRepository.findAll()).thenReturn(mockContatosList);

        // Act
        ApiRestResponse response = profissionaisService.delete(MOCK_ID);

        // Assert
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals(DELETE_SUCCESS_MESSAGE, response.getMessage());

        assertTrue(mockProfissional.isDeleted());
        assertNotNull(mockProfissional.getDeletedDate());

        for (Contatos contato : mockContatosList) {
            assertTrue(contato.getDeletedProfissional());
        }

        verify(profissionaisRepository, times(1)).save(mockProfissional);
        verify(contatosRepository, times(mockContatosList.size())).save(any(Contatos.class));
    }

    @Test
    void testDelete_ResourceNotFoundException_NotFound() {
        // Arrange
        when(profissionaisRepository.findById(MOCK_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> profissionaisService.delete(MOCK_ID));
    }

    @Test
    void testDelete_ResourceNotFoundException_AlreadyDeleted() {
        // Arrange
        mockProfissional.setDeleted(true);
        when(profissionaisRepository.findById(MOCK_ID)).thenReturn(Optional.of(mockProfissional));

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> profissionaisService.delete(MOCK_ID));
    }
}
