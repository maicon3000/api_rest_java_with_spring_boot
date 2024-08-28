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
import br.com.maicon.models.Profissionais;
import br.com.maicon.unittests.mapper.mocks.MockProfissionais;

public class DozerMapperTest {

    private MockProfissionais inputObject;
    private OffsetDateTime expectedOffsetDateTime;
    private Date expectedDate;

    private static final String EXPECTED_NAME_PREFIX = "Nome Teste ";
    private static final String EXPECTED_CARGO_PREFIX = "Cargo Teste ";
    private static final Long ID_ZERO = 0L;
    private static final Long ID_ONE = 1L;
    private static final Long ID_FIVE = 5L;
    private static final Long ID_TEN = 10L;
    private static final boolean DELETED_FLAG = false;
    private static final String DATE_STRING = "2000-01-01T00:00:00.000+00:00";

    @BeforeEach
    public void setUp() {
        inputObject = new MockProfissionais();
        expectedOffsetDateTime = OffsetDateTime.parse(DATE_STRING, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        expectedDate = Date.from(expectedOffsetDateTime.toInstant());
    }

    @Test
    public void parseEntityToDTOTest() {
        // Arrange & Act
        ProfissionaisDTO output = DozerMapper.parseObject(inputObject.mockEntity(), ProfissionaisDTO.class);

        // Assert
        assertEquals(ID_ZERO, output.getId());
        assertEquals(EXPECTED_NAME_PREFIX + "0", output.getNome());
        assertEquals(EXPECTED_CARGO_PREFIX + "0", output.getCargo());
        assertEquals(expectedDate, output.getCreatedDate());
        assertEquals(expectedDate, output.getNascimento());
        assertEquals(DELETED_FLAG, output.isDeleted());
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
        assertEquals(ID_ONE, outputOne.getId());
        assertEquals(EXPECTED_NAME_PREFIX + "1", outputOne.getNome());
        assertEquals(EXPECTED_CARGO_PREFIX + "1", outputOne.getCargo());
        assertEquals(expectedDate, outputOne.getCreatedDate());
        assertEquals(expectedDate, outputOne.getNascimento());
        assertEquals(DELETED_FLAG, outputOne.isDeleted());
        assertEquals(expectedDate, outputOne.getDeletedDate());

        assertEquals(ID_FIVE, outputFive.getId());
        assertEquals(EXPECTED_NAME_PREFIX + "5", outputFive.getNome());
        assertEquals(EXPECTED_CARGO_PREFIX + "5", outputFive.getCargo());
        assertEquals(expectedDate, outputFive.getCreatedDate());
        assertEquals(expectedDate, outputFive.getNascimento());
        assertEquals(DELETED_FLAG, outputFive.isDeleted());
        assertEquals(expectedDate, outputFive.getDeletedDate());

        assertEquals(ID_TEN, outputTen.getId());
        assertEquals(EXPECTED_NAME_PREFIX + "10", outputTen.getNome());
        assertEquals(EXPECTED_CARGO_PREFIX + "10", outputTen.getCargo());
        assertEquals(expectedDate, outputTen.getCreatedDate());
        assertEquals(expectedDate, outputTen.getNascimento());
        assertEquals(DELETED_FLAG, outputTen.isDeleted());
        assertEquals(expectedDate, outputTen.getDeletedDate());
    }

    @Test
    public void parseDTOToEntityTest() {
        // Arrange & Act
        Profissionais output = DozerMapper.parseObject(inputObject.mockVO(), Profissionais.class);

        // Assert
        assertEquals(ID_ZERO, output.getId());
        assertEquals(EXPECTED_NAME_PREFIX + "0", output.getNome());
        assertEquals(EXPECTED_CARGO_PREFIX + "0", output.getCargo());
        assertEquals(expectedDate, output.getCreatedDate());
        assertEquals(expectedDate, output.getNascimento());
        assertEquals(DELETED_FLAG, output.isDeleted());
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
        assertEquals(ID_ONE, outputOne.getId());
        assertEquals(EXPECTED_NAME_PREFIX + "1", outputOne.getNome());
        assertEquals(EXPECTED_CARGO_PREFIX + "1", outputOne.getCargo());
        assertEquals(expectedDate, outputOne.getCreatedDate());
        assertEquals(expectedDate, outputOne.getNascimento());
        assertEquals(DELETED_FLAG, outputOne.isDeleted());
        assertEquals(expectedDate, outputOne.getDeletedDate());

        assertEquals(ID_FIVE, outputFive.getId());
        assertEquals(EXPECTED_NAME_PREFIX + "5", outputFive.getNome());
        assertEquals(EXPECTED_CARGO_PREFIX + "5", outputFive.getCargo());
        assertEquals(expectedDate, outputFive.getCreatedDate());
        assertEquals(expectedDate, outputFive.getNascimento());
        assertEquals(DELETED_FLAG, outputFive.isDeleted());
        assertEquals(expectedDate, outputFive.getDeletedDate());

        assertEquals(ID_TEN, outputTen.getId());
        assertEquals(EXPECTED_NAME_PREFIX + "10", outputTen.getNome());
        assertEquals(EXPECTED_CARGO_PREFIX + "10", outputTen.getCargo());
        assertEquals(expectedDate, outputTen.getCreatedDate());
        assertEquals(expectedDate, outputTen.getNascimento());
        assertEquals(DELETED_FLAG, outputTen.isDeleted());
        assertEquals(expectedDate, outputTen.getDeletedDate());
    }

}
