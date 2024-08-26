package br.com.maicon.unittests.services.validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.maicon.data.dto.v1.ProfissionaisDTO;
import br.com.maicon.services.validation.ProfissionaisValidator;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class ProfissionaisValidatorTest {

    private Validator validator;
    private ProfissionaisValidator profissionaisValidator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        profissionaisValidator = new ProfissionaisValidator(validator);
    }

    @Test
    public void testValidateValidProfissional() {
        // Arrange
        ProfissionaisDTO profissional = new ProfissionaisDTO();
        profissional.setNome("Nome Válido");
        profissional.setCargo("Desenvolvedor");
        profissional.setNascimento(new Date());
        profissional.setCreatedDate(new Date());

        // Act & Assert
        assertDoesNotThrow(() -> profissionaisValidator.validate(profissional));
    }

    @Test
    public void testValidateInvalidProfissional() {
        // Arrange
        ProfissionaisDTO profissional = new ProfissionaisDTO();
        // Nome não definido (nulo), o que é inválido

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> profissionaisValidator.validate(profissional));
    }

    @Test
    public void testValidateEmptyNomeProfissional() {
        // Arrange
        ProfissionaisDTO profissional = new ProfissionaisDTO();
        profissional.setNome(""); // Nome vazio, o que é inválido
        profissional.setCargo("Desenvolvedor");
        profissional.setNascimento(new Date());
        profissional.setCreatedDate(new Date());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> profissionaisValidator.validate(profissional));
    }
}

