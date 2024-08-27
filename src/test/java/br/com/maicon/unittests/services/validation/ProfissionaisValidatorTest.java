//package br.com.maicon.unittests.services.validation;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//import java.util.Date;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import br.com.maicon.data.dto.v1.ProfissionaisDTO;
//import br.com.maicon.services.validation.ProfissionaisValidator;
//import br.com.maicon.utils.ApiRestResponse;
//import jakarta.validation.Validation;
//import jakarta.validation.Validator;
//import jakarta.validation.ValidatorFactory;
//
//public class ProfissionaisValidatorTest {
//
//    private Validator validator;
//    private ProfissionaisValidator profissionaisValidator;
//
//    @BeforeEach
//    public void setUp() {
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        validator = factory.getValidator();
//        profissionaisValidator = new ProfissionaisValidator(validator);
//    }
//
//    @Test
//    public void testValidateValidProfissional() {
//        // Arrange
//        ProfissionaisDTO profissional = new ProfissionaisDTO();
//        profissional.setNome("Nome Válido");
//        profissional.setCargo("Desenvolvedor");
//        profissional.setNascimento(new Date());
//        profissional.setCreatedDate(new Date());
//
//        // Act
//        ApiRestResponse response = profissionaisValidator.validate(profissional);
//
//        // Assert
//        assertEquals(true, response.isSuccess());
//        assertEquals("Validação realizada com sucesso.", response.getMessage());
//    }
//
//    @Test
//    public void testValidateInvalidCargoProfissional() {
//        // Arrange
//        ProfissionaisDTO profissional = new ProfissionaisDTO();
//        profissional.setNome("Nome Válido");
//        profissional.setCargo("Cargo inválido");
//        profissional.setNascimento(new Date());
//        profissional.setCreatedDate(new Date());
//
//        // Act
//        ApiRestResponse response = profissionaisValidator.validate(profissional);
//
//        // Assert
//        assertEquals(false, response.isSuccess());
//        assertEquals("O cargo do profissional deve ser: Desenvolvedor, Designer, Suporte ou Tester.", response.getMessage());
//    }
//
//    @Test
//    public void testValidateInvalidNameProfissional() {
//        // Arrange
//        ProfissionaisDTO profissional = new ProfissionaisDTO();
//        profissional.setCargo("Desenvolvedor");
//
//        // Act & Assert
//        assertThrows(IllegalArgumentException.class, () -> profissionaisValidator.validate(profissional));
//    }
//
//    @Test
//    public void testValidateEmptyNomeProfissional() {
//        // Arrange
//        ProfissionaisDTO profissional = new ProfissionaisDTO();
//        profissional.setNome("Dummy");
//        profissional.setCargo("");
//        profissional.setNascimento(new Date());
//        profissional.setCreatedDate(new Date());
//
//        // Act
//        ApiRestResponse response = profissionaisValidator.validate(profissional);
//
//        // Assert
//        assertEquals(false, response.isSuccess());
//        assertEquals("O cargo do profissional deve ser: Desenvolvedor, Designer, Suporte ou Tester.", response.getMessage());
//    }
//}
