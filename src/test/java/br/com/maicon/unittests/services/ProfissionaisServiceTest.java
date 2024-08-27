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
import br.com.maicon.model.Profissionais;
import br.com.maicon.repositories.ProfissionaisRepository;
import br.com.maicon.services.ProfissionaisService;
import br.com.maicon.services.validation.ProfissionaisValidator;
import br.com.maicon.utils.ApiResponse;

class ProfissionaisServiceTest {

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
        profissionalDto.setId(1L);
        profissionalDto.setNome("Nome Teste");
        profissionalDto.setCargo("Cargo Teste");
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
        when(profissionaisRepository.findByQuery("Nome")).thenReturn(List.of(profissional));

        // Act
        List<ProfissionaisDTO> result = profissionaisService.findAll("Nome");

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
        when(profissionaisRepository.findByIdAndActive(1L)).thenReturn(Optional.of(profissional));

        // Act
        ProfissionaisDTO result = profissionaisService.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(profissionalDto.getId(), result.getId());
    }

    @Test
    void testFindById_ResourceNotFoundException() {
        // Arrange
        when(profissionaisRepository.findByIdAndActive(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> profissionaisService.findById(1L));
    }

    @Test
    void testCreate() {
        // Arrange
        when(profissionaisValidator.validate(any(ProfissionaisDTO.class)))
            .thenReturn(new ApiResponse(true, "Validado com sucesso"));
        when(profissionaisRepository.save(any(Profissionais.class))).thenReturn(profissional);

        // Act
        ApiResponse response = profissionaisService.create(profissionalDto);

        // Assert
        verify(profissionaisValidator, times(1)).validate(profissionalDto);
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals("Profissional com ID 1 cadastrado com sucesso!", response.getMessage());
    }

    @Test
    void testCreate_InvalidData() {
        // Arrange
        when(profissionaisValidator.validate(any(ProfissionaisDTO.class)))
            .thenReturn(new ApiResponse(false, "Falha na validação"));

        // Act
        ApiResponse response = profissionaisService.create(profissionalDto);

        // Assert
        verify(profissionaisValidator, times(1)).validate(profissionalDto);
        assertNotNull(response);
        assertFalse(response.isSuccess());
        assertEquals("Falha na validação", response.getMessage());
    }

    @Test
    void testUpdate() {
        // Arrange
        when(profissionaisValidator.validate(any(ProfissionaisDTO.class)))
            .thenReturn(new ApiResponse(true, "Validado com sucesso"));
        when(profissionaisRepository.findByIdAndActive(1L)).thenReturn(Optional.of(profissional));
        when(profissionaisRepository.save(any(Profissionais.class))).thenReturn(profissional);

        // Act
        ApiResponse response = profissionaisService.update(profissionalDto);

        // Assert
        verify(profissionaisValidator, times(1)).validate(profissionalDto);
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals("Cadastro alterado com sucesso!", response.getMessage());
    }

    @Test
    void testUpdate_InvalidData() {
        // Arrange
        when(profissionaisValidator.validate(any(ProfissionaisDTO.class)))
            .thenReturn(new ApiResponse(false, "Falha na validação"));

        // Act
        ApiResponse response = profissionaisService.update(profissionalDto);

        // Assert
        verify(profissionaisValidator, times(1)).validate(profissionalDto);
        assertNotNull(response);
        assertFalse(response.isSuccess());
        assertEquals("Falha na validação", response.getMessage());
    }

    @Test
    void testUpdate_ResourceNotFoundException() {
        // Arrange
        when(profissionaisValidator.validate(any(ProfissionaisDTO.class)))
            .thenReturn(new ApiResponse(true, "Validado com sucesso"));
        when(profissionaisRepository.findByIdAndActive(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> profissionaisService.update(profissionalDto));
    }

    @Test
    void testDelete() {
        // Arrange
        when(profissionaisRepository.findById(1L)).thenReturn(Optional.of(profissional));

        // Act
        ApiResponse response = profissionaisService.delete(1L);

        // Assert
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals("Profissional excluído com sucesso!", response.getMessage());
    }

    @Test
    void testDelete_ResourceNotFoundException_NotFound() {
        // Arrange
        when(profissionaisRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> profissionaisService.delete(1L));
    }

    @Test
    void testDelete_ResourceNotFoundException_AlreadyDeleted() {
        // Arrange
        profissional.setDeleted(true);
        when(profissionaisRepository.findById(1L)).thenReturn(Optional.of(profissional));

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> profissionaisService.delete(1L));
    }
}
