package br.com.maicon.service.validator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.maicon.data.dto.v1.ProfissionaisDTO;
import br.com.maicon.services.validation.ProfissionaisValidator;
import br.com.maicon.utils.ApiRestResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

class ProfissionaisValidatorTest {

    private static final String VALID_CARGO = "Desenvolvedor";
    private static final String INVALID_CARGO = "Gerente";
    private static final String VALIDATION_SUCCESS = "Validação realizada com sucesso.";
    private static final String VALIDATION_FAILURE_MESSAGE = "O cargo do profissional deve ser: Desenvolvedor, Designer, Suporte ou Tester.";
    private static final String MOCK_VIOLATION_PROPERTY = "nome";
    private static final String MOCK_VIOLATION_MESSAGE = "não pode ser vazio";
    private static final String VALIDATION_ERROR_PREFIX = "Erros de validação: ";
    
    @Mock
    private Validator validator;

    @InjectMocks
    private ProfissionaisValidator profissionaisValidator;

    private ProfissionaisDTO profissionalDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        profissionalDto = new ProfissionaisDTO();
        profissionalDto.setNome("Nome Teste");
        profissionalDto.setCargo(VALID_CARGO);
    }

    @Test
    void testValidate_ValidCargo() {
        // Arrange
        when(validator.validate(any(ProfissionaisDTO.class))).thenReturn(Set.of());

        // Act
        ApiRestResponse response = profissionaisValidator.validate(profissionalDto);

        // Assert
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals(VALIDATION_SUCCESS, response.getMessage());
    }

    @Test
    void testValidate_InvalidCargo() {
        // Arrange
        profissionalDto.setCargo(INVALID_CARGO);

        // Act
        ApiRestResponse response = profissionaisValidator.validate(profissionalDto);

        // Assert
        assertNotNull(response);
        assertFalse(response.isSuccess());
        assertEquals(VALIDATION_FAILURE_MESSAGE, response.getMessage());
    }

    @Test
    void testValidateBase_Failure() {
        // Arrange
        @SuppressWarnings("unchecked")
        ConstraintViolation<ProfissionaisDTO> violation = mock(ConstraintViolation.class);
        jakarta.validation.Path mockPath = mock(jakarta.validation.Path.class);
        when(mockPath.toString()).thenReturn(MOCK_VIOLATION_PROPERTY);

        when(violation.getPropertyPath()).thenReturn(mockPath);
        when(violation.getMessage()).thenReturn(MOCK_VIOLATION_MESSAGE);
        when(validator.validate(any(ProfissionaisDTO.class))).thenReturn(Set.of(violation));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            profissionaisValidator.validate(profissionalDto);
        });

        assertTrue(exception.getMessage().contains(VALIDATION_ERROR_PREFIX + MOCK_VIOLATION_PROPERTY + " - " + MOCK_VIOLATION_MESSAGE + "."));
    }

    @Test
    void testCapitalizeCargo_NullOrEmpty() {
        // Act & Assert
        assertNull(profissionaisValidator.capitalizeCargo(null));
        assertEquals("", profissionaisValidator.capitalizeCargo(""));
    }

    @Test
    void testCapitalizeCargo_SingleWord() {
        // Act
        String result = profissionaisValidator.capitalizeCargo("desenvolvedor");

        // Assert
        assertEquals(VALID_CARGO, result);
    }

    @Test
    void testCapitalizeCargo_MixedCase() {
        // Act
        String result = profissionaisValidator.capitalizeCargo("deSenVolVedOR");

        // Assert
        assertEquals(VALID_CARGO, result);
    }
}
