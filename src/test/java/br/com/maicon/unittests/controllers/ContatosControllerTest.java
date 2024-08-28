package br.com.maicon.unittests.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import br.com.maicon.controllers.ContatosController;
import br.com.maicon.data.dto.v1.ContatosDTO;
import br.com.maicon.data.dto.v1.utils.DtoUtils;
import br.com.maicon.services.ContatosService;
import br.com.maicon.utils.ApiRestResponse;

class ContatosControllerTest {

    private MockMvc mockMvc;

    private static final String BASE_URL = "/api/contatos/v1";
    private static final String APPLICATION_JSON = MediaType.APPLICATION_JSON_VALUE;
    private static final String SUCCESS_MESSAGE_CREATE = "Contato cadastrado com sucesso!";
    private static final String SUCCESS_MESSAGE_UPDATE = "Cadastro atualizado com sucesso!";
    private static final String SUCCESS_MESSAGE_DELETE = "Contato excluído com sucesso!";
    private static final String ERROR_MESSAGE_INVALID_DATA = "Dados inválidos!";
    private static final String ERROR_MESSAGE_NOT_FOUND = "Contato não encontrado!";
    private static final String FIELD_ID = "$[0].id";
    private static final String FIELD_NAME = "$[0].nome";
    private static final String FIELD_SUCCESS = "$.success";
    private static final String FIELD_MESSAGE = "$.message";
    private static final Long MOCK_ID = 1L;

    @Mock
    private ContatosService contatosService;

    @InjectMocks
    private ContatosController contatosController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(contatosController).build();
    }

    @Test
    void testFindAll() throws Exception {
        // Arrange
        ContatosDTO contatoDTO = new ContatosDTO();
        contatoDTO.setId(MOCK_ID);
        contatoDTO.setNome("Nome Teste");
        when(contatosService.findAll(null)).thenReturn(List.of(contatoDTO));

        // Act & Assert
        mockMvc.perform(get(BASE_URL)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(FIELD_ID).value(MOCK_ID))
                .andExpect(jsonPath(FIELD_NAME).value("Nome Teste"));
    }

    @Test
    void testFindAllWithQuery() throws Exception {
        // Arrange
        ContatosDTO contatoDTO = new ContatosDTO();
        contatoDTO.setId(MOCK_ID);
        contatoDTO.setNome("Nome Teste");
        when(contatosService.findAll("Nome")).thenReturn(List.of(contatoDTO));

        // Act & Assert
        mockMvc.perform(get(BASE_URL)
                .param("q", "Nome")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(FIELD_ID).value(MOCK_ID))
                .andExpect(jsonPath(FIELD_NAME).value("Nome Teste"));
    }

    @Test
    void testFindAllWithFields() throws Exception {
        // Arrange
        ContatosDTO contatoDTO = new ContatosDTO();
        contatoDTO.setId(MOCK_ID);
        contatoDTO.setNome("Nome Teste");
        List<String> fields = List.of("id", "nome");
        Map<String, Object> filteredFields = DtoUtils.filterFields(contatoDTO, fields);
        when(contatosService.findAll(null)).thenReturn(List.of(contatoDTO));

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
        ContatosDTO contatoDTO = new ContatosDTO();
        contatoDTO.setId(MOCK_ID);
        contatoDTO.setNome("Nome Teste");
        when(contatosService.findById(MOCK_ID)).thenReturn(contatoDTO);

        // Act & Assert
        mockMvc.perform(get(BASE_URL + "/1")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(MOCK_ID))
                .andExpect(jsonPath("$.nome").value("Nome Teste"));
    }

    @Test
    void testCreate() throws Exception {
        // Arrange
        ContatosDTO contatoDTO = new ContatosDTO();
        contatoDTO.setNome("Nome Teste");
        ApiRestResponse apiResponse = new ApiRestResponse(true, SUCCESS_MESSAGE_CREATE);
        when(contatosService.create(any(ContatosDTO.class))).thenReturn(apiResponse);

        // Act & Assert
        mockMvc.perform(post(BASE_URL)
                .contentType(APPLICATION_JSON)
                .content("{\"nome\":\"Nome Teste\",\"contato\":\"Contato Teste\",\"profissionalId\":1}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(FIELD_SUCCESS).value(true))
                .andExpect(jsonPath(FIELD_MESSAGE).value(SUCCESS_MESSAGE_CREATE));
    }

    @Test
    void testCreate_InvalidData() throws Exception {
        // Arrange
        ApiRestResponse apiResponse = new ApiRestResponse(false, ERROR_MESSAGE_INVALID_DATA);
        when(contatosService.create(any(ContatosDTO.class))).thenReturn(apiResponse);

        // Act & Assert
        mockMvc.perform(post(BASE_URL)
                .contentType(APPLICATION_JSON)
                .content("{\"nome\":\"\",\"contato\":\"Contato Teste\",\"profissionalId\":1}"))
		        .andExpect(status().isBadRequest())
		        .andExpect(jsonPath(FIELD_SUCCESS).value(false))
		        .andExpect(jsonPath(FIELD_MESSAGE).value(ERROR_MESSAGE_INVALID_DATA));
    }

    @Test
    void testUpdate() throws Exception {
        // Arrange
        ApiRestResponse apiResponse = new ApiRestResponse(true, SUCCESS_MESSAGE_UPDATE);

        when(contatosService.update(any(ContatosDTO.class))).thenReturn(apiResponse);

        // Act & Assert
        mockMvc.perform(put(BASE_URL + "/1")
                .contentType(APPLICATION_JSON)
                .content("{\"nome\":\"Nome Atualizado\",\"contato\":\"Contato Atualizado\",\"profissionalId\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(FIELD_SUCCESS).value(true))
                .andExpect(jsonPath(FIELD_MESSAGE).value(SUCCESS_MESSAGE_UPDATE));
    }

    @Test
    void testUpdate_InvalidData() throws Exception {
        // Arrange
        ApiRestResponse apiResponse = new ApiRestResponse(false, ERROR_MESSAGE_INVALID_DATA);

        when(contatosService.update(any(ContatosDTO.class))).thenReturn(apiResponse);

        // Act & Assert
        String response = mockMvc.perform(put(BASE_URL + "/1")
                .contentType(APPLICATION_JSON)
                .content("{\"nome\":\"Nome Inválido\",\"contato\":\"\",\"profissionalId\":1}"))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        System.out.println(response);
    }
    
    @Test
    void testUpdate_Failure() throws Exception {
        // Arrange
        ApiRestResponse apiResponse = new ApiRestResponse(false, ERROR_MESSAGE_INVALID_DATA);

        when(contatosService.update(any(ContatosDTO.class))).thenReturn(apiResponse);

        // Act & Assert
        mockMvc.perform(put(BASE_URL + "/1")
                .contentType(APPLICATION_JSON)
                .content("{\"nome\":\"Nome Atualizado\",\"contato\":\"Contato Atualizado\",\"profissionalId\":1}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(FIELD_SUCCESS).value(false))
                .andExpect(jsonPath(FIELD_MESSAGE).value(ERROR_MESSAGE_INVALID_DATA));
    }

    @Test
    void testDelete() throws Exception {
        // Arrange
        ApiRestResponse apiResponse = new ApiRestResponse(true, SUCCESS_MESSAGE_DELETE);
        when(contatosService.delete(MOCK_ID)).thenReturn(apiResponse);

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
        when(contatosService.delete(MOCK_ID)).thenReturn(apiResponse);

        // Act & Assert
        mockMvc.perform(delete(BASE_URL + "/1")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(FIELD_SUCCESS).value(false))
                .andExpect(jsonPath(FIELD_MESSAGE).value(ERROR_MESSAGE_NOT_FOUND));
    }
}
