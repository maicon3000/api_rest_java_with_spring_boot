package br.com.maicon.unittests.services.validation.base;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.maicon.services.validation.base.ValidatorBase;
import br.com.maicon.utils.ApiRestResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;

class ValidatorBaseTest {

    private static final String VALIDATION_SUCCESS_MESSAGE = "Validação realizada com sucesso.";
    private static final String FIELD_NAME = "nome";
    private static final String ERROR_MESSAGE = "não pode ser vazio";
    private static final String EXPECTED_EXCEPTION_MESSAGE = "Erros de validação: nome - não pode ser vazio.";

    @Mock
    private Validator validator;

    @InjectMocks
    private ValidatorBase<TestDTO> validatorBase;

    private TestDTO testDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testDTO = new TestDTO();
    }

    @Test
    void testValidateBase_Success() {
        // Arrange
        when(validator.validate(any(TestDTO.class))).thenReturn(Set.of());

        // Act
        ApiRestResponse response = validatorBase.validateBase(testDTO);

        // Assert
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals(VALIDATION_SUCCESS_MESSAGE, response.getMessage());
    }

    @Test
    void testValidateBase_Failure() {
        // Arrange
        @SuppressWarnings("unchecked")
        ConstraintViolation<TestDTO> violation = mock(ConstraintViolation.class);

        Path mockPath = mock(Path.class);
        when(mockPath.toString()).thenReturn(FIELD_NAME);
        
        when(violation.getPropertyPath()).thenReturn(mockPath);
        when(violation.getMessage()).thenReturn(ERROR_MESSAGE);
        when(validator.validate(any(TestDTO.class))).thenReturn(Set.of(violation));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            validatorBase.validateBase(testDTO);
        });

        assertTrue(exception.getMessage().contains(EXPECTED_EXCEPTION_MESSAGE));
    }

    private static class TestDTO {}
}
