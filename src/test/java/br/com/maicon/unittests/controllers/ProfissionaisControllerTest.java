package br.com.maicon.unittests.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.maicon.controllers.ProfissionaisController;
import br.com.maicon.data.dto.v1.ProfissionaisDTO;
import br.com.maicon.data.dto.v1.utils.DtoUtils;
import br.com.maicon.services.ProfissionaisService;
import br.com.maicon.utils.ApiRestResponse;

class ProfissionaisControllerTest {

    private MockMvc mockMvc;

    private static final String BASE_URL = "/api/profissionais/v1";
    private static final String APPLICATION_JSON = MediaType.APPLICATION_JSON_VALUE;
    private static final String SUCCESS_MESSAGE_CREATE = "Profissional cadastrado com sucesso!";
    private static final String SUCCESS_MESSAGE_UPDATE = "Cadastro atualizado com sucesso!";
    private static final String SUCCESS_MESSAGE_DELETE = "Profissional excluído com sucesso!";
    private static final String ERROR_MESSAGE_INVALID_DATA = "Dados inválidos!";
    private static final String ERROR_MESSAGE_INVALID_CARGO = "O cargo do profissional deve ser: Desenvolvedor, Designer, Suporte ou Tester.";
    private static final String ERROR_MESSAGE_NOT_FOUND = "Profissional não encontrado!";
    private static final String FIELD_ID = "$[0].id";
    private static final String FIELD_NAME = "$[0].nome";
    private static final String FIELD_SUCCESS = "$.success";
    private static final String FIELD_MESSAGE = "$.message";
    private static final Long MOCK_ID = 1L;
    private static final String MOCK_NAME = "Nome Teste";
    private static final String MOCK_CARGO = "Desenvolvedor";

    private final String mockDateString = LocalDate.of(2000, 1, 1).toString();

    @Mock
    private ProfissionaisService profissionaisService;

    @InjectMocks
    private ProfissionaisController profissionaisController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(profissionaisController).build();
    }

    @Test
    void testFindAll() throws Exception {
        // Arrange
        ProfissionaisDTO profissionalDTO = new ProfissionaisDTO();
        profissionalDTO.setId(MOCK_ID);
        profissionalDTO.setNome(MOCK_NAME);
        when(profissionaisService.findAll(null)).thenReturn(List.of(profissionalDTO));

        // Act & Assert
        mockMvc.perform(get(BASE_URL)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(FIELD_ID).value(MOCK_ID))
                .andExpect(jsonPath(FIELD_NAME).value(MOCK_NAME));
    }

    @Test
    void testFindAllWithQuery() throws Exception {
        // Arrange
        ProfissionaisDTO profissionalDTO = new ProfissionaisDTO();
        profissionalDTO.setId(MOCK_ID);
        profissionalDTO.setNome(MOCK_NAME);
        when(profissionaisService.findAll(MOCK_NAME)).thenReturn(List.of(profissionalDTO));

        // Act & Assert
        mockMvc.perform(get(BASE_URL)
                .param("q", MOCK_NAME)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(FIELD_ID).value(MOCK_ID))
                .andExpect(jsonPath(FIELD_NAME).value(MOCK_NAME));
    }

    @Test
    void testFindAllWithFields() throws Exception {
        // Arrange
        ProfissionaisDTO profissionalDTO = new ProfissionaisDTO();
        profissionalDTO.setId(MOCK_ID);
        profissionalDTO.setNome(MOCK_NAME);
        List<String> fields = List.of("id", "nome");
        Map<String, Object> filteredFields = DtoUtils.filterFields(profissionalDTO, fields);
        when(profissionaisService.findAll(null)).thenReturn(List.of(profissionalDTO));

        // Act & Assert
        mockMvc.perform(get(BASE_URL)
                .param("fields", "id,nome")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(FIELD_ID).value(filteredFields.get("id")))
                .andExpect(jsonPath(FIELD_NAME).value(filteredFields.get("nome")));
    }

    @Test
    void testFindById() throws Exception {
        // Arrange
        ProfissionaisDTO profissionalDTO = new ProfissionaisDTO();
        profissionalDTO.setId(MOCK_ID);
        profissionalDTO.setNome(MOCK_NAME);
        when(profissionaisService.findById(MOCK_ID)).thenReturn(profissionalDTO);

        // Act & Assert
        mockMvc.perform(get(BASE_URL + "/1")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(MOCK_ID))
                .andExpect(jsonPath("$.nome").value(MOCK_NAME));
    }

    @Test
    void testCreate() throws Exception {
        // Arrange
        ProfissionaisDTO profissionalDTO = new ProfissionaisDTO();
        profissionalDTO.setNome(MOCK_NAME);
        ApiRestResponse apiResponse = new ApiRestResponse(true, SUCCESS_MESSAGE_CREATE);
        when(profissionaisService.create(any(ProfissionaisDTO.class))).thenReturn(apiResponse);

        // Act & Assert
        mockMvc.perform(post(BASE_URL)
                .contentType(APPLICATION_JSON)
                .content("{\"nome\":\"" + MOCK_NAME + "\",\"cargo\":\"" + MOCK_CARGO + "\",\"nascimento\":\"" + mockDateString + "\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(FIELD_SUCCESS).value(true))
                .andExpect(jsonPath(FIELD_MESSAGE).value(SUCCESS_MESSAGE_CREATE));
    }

    @Test
    void testCreate_InvalidData() throws Exception {
        // Arrange
        ApiRestResponse apiResponse = new ApiRestResponse(false, ERROR_MESSAGE_INVALID_DATA);
        when(profissionaisService.create(any(ProfissionaisDTO.class))).thenReturn(apiResponse);

        // Act & Assert
        mockMvc.perform(post(BASE_URL)
                .contentType(APPLICATION_JSON)
                .content("{\"nome\":\"\",\"cargo\":\"" + MOCK_CARGO + "\",\"nascimento\":\"" + mockDateString + "\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(FIELD_SUCCESS).value(false))
                .andExpect(jsonPath(FIELD_MESSAGE).value(ERROR_MESSAGE_INVALID_DATA));
    }

    @Test
    void testUpdate() throws Exception {
        // Arrange
        ApiRestResponse apiResponse = new ApiRestResponse(true, SUCCESS_MESSAGE_UPDATE);

        when(profissionaisService.update(any(ProfissionaisDTO.class))).thenReturn(apiResponse);

        // Act & Assert
        mockMvc.perform(put(BASE_URL + "/1")
                .contentType(APPLICATION_JSON)
                .content("{\"nome\":\"Nome Atualizado\",\"cargo\":\"Designer\",\"nascimento\":\"" + mockDateString + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(FIELD_SUCCESS).value(true))
                .andExpect(jsonPath(FIELD_MESSAGE).value(SUCCESS_MESSAGE_UPDATE));
    }

    @Test
    void testUpdate_InvalidData() throws Exception {
        // Arrange
        ApiRestResponse apiResponse = new ApiRestResponse(false, ERROR_MESSAGE_INVALID_CARGO);

        when(profissionaisService.update(any(ProfissionaisDTO.class))).thenReturn(apiResponse);

        // Act & Assert
        mockMvc.perform(put(BASE_URL + "/1")
                .contentType(APPLICATION_JSON)
                .content("{\"nome\":\"Nome Inválido\",\"cargo\":\"Cargo Inválido\",\"nascimento\":\"" + mockDateString + "\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(FIELD_SUCCESS).value(false))
                .andExpect(jsonPath(FIELD_MESSAGE).value(ERROR_MESSAGE_INVALID_CARGO));
    }

    @Test
    void testDelete() throws Exception {
        // Arrange
        ApiRestResponse apiResponse = new ApiRestResponse(true, SUCCESS_MESSAGE_DELETE);
        when(profissionaisService.delete(MOCK_ID)).thenReturn(apiResponse);

        // Act & Assert
        mockMvc.perform(delete(BASE_URL + "/1")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(FIELD_SUCCESS).value(true))
                .andExpect(jsonPath(FIELD_MESSAGE).value(SUCCESS_MESSAGE_DELETE));
    }

    @Test
    void testDelete_NotFound() throws Exception {
        // Arrange
        ApiRestResponse apiResponse = new ApiRestResponse(false, ERROR_MESSAGE_NOT_FOUND);
        when(profissionaisService.delete(MOCK_ID)).thenReturn(apiResponse);

        // Act & Assert
        mockMvc.perform(delete(BASE_URL + "/1")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(FIELD_SUCCESS).value(false))
                .andExpect(jsonPath(FIELD_MESSAGE).value(ERROR_MESSAGE_NOT_FOUND));
    }
}
