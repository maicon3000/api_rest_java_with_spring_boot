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
import br.com.maicon.models.Profissionais;
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
    private ProfissionaisValidator profissionaisValidator;

    @InjectMocks
    private ProfissionaisService profissionaisService;

    private ProfissionaisDTO profissionalDto;
    private Profissionais profissional;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        profissionalDto = new ProfissionaisDTO();
        profissionalDto.setId(MOCK_ID);
        profissionalDto.setNome(MOCK_NAME);
        profissionalDto.setCargo(MOCK_POSITION);
        profissionalDto.setNascimento(new Date());
        profissionalDto.setCreatedDate(new Date());
        profissionalDto.setDeleted(false);
        profissionalDto.setDeletedDate(null);

        profissional = DozerMapper.parseObject(profissionalDto, Profissionais.class);
    }

    @Test
    void testFindAll() {
        // Arrange
        when(profissionaisRepository.findAllActive()).thenReturn(List.of(profissional));

        // Act
        List<ProfissionaisDTO> result = profissionaisService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(profissionalDto.getId(), result.get(0).getId());
    }

    @Test
    void testFindAllWithQuery() {
        // Arrange
        when(profissionaisRepository.findByQuery(MOCK_NAME)).thenReturn(List.of(profissional));

        // Act
        List<ProfissionaisDTO> result = profissionaisService.findAll(MOCK_NAME);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(profissionalDto.getId(), result.get(0).getId());
    }
    
    @Test
    void testFindAllWithEmptyQuery() {
        // Arrange
        when(profissionaisRepository.findAllActive()).thenReturn(List.of(profissional));

        // Act
        List<ProfissionaisDTO> result = profissionaisService.findAll("");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(profissionalDto.getId(), result.get(0).getId());
    }

    @Test
    void testFindAllWithNullQuery() {
        // Arrange
        when(profissionaisRepository.findAllActive()).thenReturn(List.of(profissional));

        // Act
        List<ProfissionaisDTO> result = profissionaisService.findAll(null);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(profissionalDto.getId(), result.get(0).getId());
    }

    @Test
    void testFindById() {
        // Arrange
        when(profissionaisRepository.findByIdAndActive(MOCK_ID)).thenReturn(Optional.of(profissional));

        // Act
        ProfissionaisDTO result = profissionaisService.findById(MOCK_ID);

        // Assert
        assertNotNull(result);
        assertEquals(profissionalDto.getId(), result.getId());
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
        when(profissionaisRepository.save(any(Profissionais.class))).thenReturn(profissional);

        // Act
        ApiRestResponse response = profissionaisService.create(profissionalDto);

        // Assert
        verify(profissionaisValidator, times(1)).validate(profissionalDto);
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
        ApiRestResponse response = profissionaisService.create(profissionalDto);

        // Assert
        verify(profissionaisValidator, times(1)).validate(profissionalDto);
        assertNotNull(response);
        assertFalse(response.isSuccess());
        assertEquals(VALIDATION_FAILURE, response.getMessage());
    }

    @Test
    void testUpdate() {
        // Arrange
        when(profissionaisValidator.validate(any(ProfissionaisDTO.class)))
            .thenReturn(new ApiRestResponse(true, VALIDATION_SUCCESS));
        when(profissionaisRepository.findByIdAndActive(MOCK_ID)).thenReturn(Optional.of(profissional));
        when(profissionaisRepository.save(any(Profissionais.class))).thenReturn(profissional);

        // Act
        ApiRestResponse response = profissionaisService.update(profissionalDto);

        // Assert
        verify(profissionaisValidator, times(1)).validate(profissionalDto);
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
        ApiRestResponse response = profissionaisService.update(profissionalDto);

        // Assert
        verify(profissionaisValidator, times(1)).validate(profissionalDto);
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
        assertThrows(ResourceNotFoundException.class, () -> profissionaisService.update(profissionalDto));
    }

    @Test
    void testDelete() {
        // Arrange
        when(profissionaisRepository.findById(MOCK_ID)).thenReturn(Optional.of(profissional));

        // Act
        ApiRestResponse response = profissionaisService.delete(MOCK_ID);

        // Assert
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals(DELETE_SUCCESS_MESSAGE, response.getMessage());
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
        profissional.setDeleted(true);
        when(profissionaisRepository.findById(MOCK_ID)).thenReturn(Optional.of(profissional));

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> profissionaisService.delete(MOCK_ID));
    }
}
