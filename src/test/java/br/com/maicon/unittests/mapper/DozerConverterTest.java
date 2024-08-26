package br.com.maicon.unittests.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.maicon.data.dto.v1.ProfissionaisDTO;
import br.com.maicon.mapper.DozerMapper;
import br.com.maicon.model.Profissionais;
import br.com.maicon.unittests.mapper.mocks.MockProfissionais;

public class DozerConverterTest {
    
    MockProfissionais inputObject;
    OffsetDateTime expectedOffsetDateTime;
    Date expectedDate;

    @BeforeEach
    public void setUp() {
        inputObject = new MockProfissionais();
        expectedOffsetDateTime = OffsetDateTime.parse("2000-01-01T00:00:00.000+00:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        expectedDate = Date.from(expectedOffsetDateTime.toInstant());
    }

    @Test
    public void parseEntityToDTOTest() {
        // Arrange
        ProfissionaisDTO output = DozerMapper.parseObject(inputObject.mockEntity(), ProfissionaisDTO.class);

        // Act
        // (A ação aqui é implicitamente realizada pela linha acima, onde o mapeamento ocorre)

        // Assert
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("Nome Teste 0", output.getNome());
        assertEquals("Cargo Teste 0", output.getCargo());
        assertEquals(expectedDate, output.getCreatedDate());
        assertEquals(expectedDate, output.getNascimento());
        assertEquals(false, output.isDeleted());
        assertEquals(expectedDate, output.getDeletedDate());
    }

    @Test
    public void parseEntityListToDTOListTest() {
        // Arrange
        List<ProfissionaisDTO> outputList = DozerMapper.parseListObjects(inputObject.mockEntityList(), ProfissionaisDTO.class);
        
        // Act
        ProfissionaisDTO outputOne = outputList.get(1);
        ProfissionaisDTO outputFive = outputList.get(5);
        ProfissionaisDTO outputTen = outputList.get(10);

        // Assert
        assertEquals(Long.valueOf(1L), outputOne.getId());
        assertEquals("Nome Teste 1", outputOne.getNome());
        assertEquals("Cargo Teste 1", outputOne.getCargo());
        assertEquals(expectedDate, outputOne.getCreatedDate());
        assertEquals(expectedDate, outputOne.getNascimento());
        assertEquals(false, outputOne.isDeleted());
        assertEquals(expectedDate, outputOne.getDeletedDate());

        assertEquals(Long.valueOf(5L), outputFive.getId());
        assertEquals("Nome Teste 5", outputFive.getNome());
        assertEquals("Cargo Teste 5", outputFive.getCargo());
        assertEquals(expectedDate, outputFive.getCreatedDate());
        assertEquals(expectedDate, outputFive.getNascimento());
        assertEquals(false, outputFive.isDeleted());
        assertEquals(expectedDate, outputFive.getDeletedDate());

        assertEquals(Long.valueOf(10L), outputTen.getId());
        assertEquals("Nome Teste 10", outputTen.getNome());
        assertEquals("Cargo Teste 10", outputTen.getCargo());
        assertEquals(expectedDate, outputTen.getCreatedDate());
        assertEquals(expectedDate, outputTen.getNascimento());
        assertEquals(false, outputTen.isDeleted());
        assertEquals(expectedDate, outputTen.getDeletedDate());
    }

    @Test
    public void parseDTOToEntityTest() {
        // Arrange
        Profissionais output = DozerMapper.parseObject(inputObject.mockVO(), Profissionais.class);

        // Act
        // (A ação aqui é implicitamente realizada pela linha acima, onde o mapeamento ocorre)

        // Assert
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("Nome Teste 0", output.getNome());
        assertEquals("Cargo Teste 0", output.getCargo());
        assertEquals(expectedDate, output.getCreatedDate());
        assertEquals(expectedDate, output.getNascimento());
        assertEquals(false, output.isDeleted());
        assertEquals(expectedDate, output.getDeletedDate());
    }

    @Test
    public void parserVOListToEntityListTest() {
        // Arrange
        List<Profissionais> outputList = DozerMapper.parseListObjects(inputObject.mockVOList(), Profissionais.class);

        // Act
        Profissionais outputOne = outputList.get(1);
        Profissionais outputFive = outputList.get(5);
        Profissionais outputTen = outputList.get(10);

        // Assert
        assertEquals(Long.valueOf(1L), outputOne.getId());
        assertEquals("Nome Teste 1", outputOne.getNome());
        assertEquals("Cargo Teste 1", outputOne.getCargo());
        assertEquals(expectedDate, outputOne.getCreatedDate());
        assertEquals(expectedDate, outputOne.getNascimento());
        assertEquals(false, outputOne.isDeleted());
        assertEquals(expectedDate, outputOne.getDeletedDate());

        assertEquals(Long.valueOf(5L), outputFive.getId());
        assertEquals("Nome Teste 5", outputFive.getNome());
        assertEquals("Cargo Teste 5", outputFive.getCargo());
        assertEquals(expectedDate, outputFive.getCreatedDate());
        assertEquals(expectedDate, outputFive.getNascimento());
        assertEquals(false, outputFive.isDeleted());
        assertEquals(expectedDate, outputFive.getDeletedDate());

        assertEquals(Long.valueOf(10L), outputTen.getId());
        assertEquals("Nome Teste 10", outputTen.getNome());
        assertEquals("Cargo Teste 10", outputTen.getCargo());
        assertEquals(expectedDate, outputTen.getCreatedDate());
        assertEquals(expectedDate, outputTen.getNascimento());
        assertEquals(false, outputTen.isDeleted());
        assertEquals(expectedDate, outputTen.getDeletedDate());
    }

}
