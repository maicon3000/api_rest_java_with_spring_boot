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
import br.com.maicon.utils.ApiResponse;

class ProfissionaisControllerTest {

    private MockMvc mockMvc;

    String mockDateString = LocalDate.of(2000, 1, 1).toString();

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
        profissionalDTO.setId(1L);
        profissionalDTO.setNome("Nome Teste");
        when(profissionaisService.findAll(null)).thenReturn(List.of(profissionalDTO));

        // Act & Assert
        mockMvc.perform(get("/api/profissionais/v1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nome").value("Nome Teste"));
    }

    @Test
    void testFindAllWithQuery() throws Exception {
        // Arrange
        ProfissionaisDTO profissionalDTO = new ProfissionaisDTO();
        profissionalDTO.setId(1L);
        profissionalDTO.setNome("Nome Teste");
        when(profissionaisService.findAll("Nome")).thenReturn(List.of(profissionalDTO));

        // Act & Assert
        mockMvc.perform(get("/api/profissionais/v1")
                .param("q", "Nome")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nome").value("Nome Teste"));
    }

    @Test
    void testFindAllWithFields() throws Exception {
        // Arrange
        ProfissionaisDTO profissionalDTO = new ProfissionaisDTO();
        profissionalDTO.setId(1L);
        profissionalDTO.setNome("Nome Teste");
        List<String> fields = List.of("id", "nome");
        Map<String, Object> filteredFields = DtoUtils.filterFields(profissionalDTO, fields);
        when(profissionaisService.findAll(null)).thenReturn(List.of(profissionalDTO));

        // Act & Assert
        mockMvc.perform(get("/api/profissionais/v1")
                .param("fields", "id,nome")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(filteredFields.get("id")))
                .andExpect(jsonPath("$[0].nome").value(filteredFields.get("nome")));
    }

    @Test
    void testFindById() throws Exception {
        // Arrange
        ProfissionaisDTO profissionalDTO = new ProfissionaisDTO();
        profissionalDTO.setId(1L);
        profissionalDTO.setNome("Nome Teste");
        when(profissionaisService.findById(1L)).thenReturn(profissionalDTO);

        // Act & Assert
        mockMvc.perform(get("/api/profissionais/v1/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Nome Teste"));
    }

    @Test
    void testCreate() throws Exception {
        // Arrange
        ProfissionaisDTO profissionalDTO = new ProfissionaisDTO();
        profissionalDTO.setNome("Nome Teste");
        ApiResponse apiResponse = new ApiResponse(true, "Profissional cadastrado com sucesso!");
        when(profissionaisService.create(any(ProfissionaisDTO.class))).thenReturn(apiResponse);

        // Act & Assert
        mockMvc.perform(post("/api/profissionais/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"Nome Teste\",\"cargo\":\"Desenvolvedor\",\"nascimento\":\"" + mockDateString + "\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Profissional cadastrado com sucesso!"));
    }

    @Test
    void testCreate_InvalidData() throws Exception {
        // Arrange
        ApiResponse apiResponse = new ApiResponse(false, "Dados inválidos!");
        when(profissionaisService.create(any(ProfissionaisDTO.class))).thenReturn(apiResponse);

        // Act & Assert
        mockMvc.perform(post("/api/profissionais/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"\",\"cargo\":\"Desenvolvedor\",\"nascimento\":\"" + mockDateString + "\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Dados inválidos!"));
    }

    @Test
    void testUpdate() throws Exception {
        // Arrange
        ApiResponse apiResponse = new ApiResponse(true, "Cadastro atualizado com sucesso!");

        when(profissionaisService.update(any(ProfissionaisDTO.class))).thenReturn(apiResponse);

        // Act & Assert
        mockMvc.perform(put("/api/profissionais/v1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"Nome Atualizado\",\"cargo\":\"Designer\",\"nascimento\":\"" + mockDateString + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Cadastro atualizado com sucesso!"));
    }

    @Test
    void testUpdate_InvalidData() throws Exception {
        // Arrange
        ApiResponse apiResponse = new ApiResponse(false, "O cargo do profissional deve ser: Desenvolvedor, Designer, Suporte ou Tester.");

        when(profissionaisService.update(any(ProfissionaisDTO.class))).thenReturn(apiResponse);

        // Act & Assert
        mockMvc.perform(put("/api/profissionais/v1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"Nome Inválido\",\"cargo\":\"Cargo Inválido\",\"nascimento\":\"" + mockDateString + "\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("O cargo do profissional deve ser: Desenvolvedor, Designer, Suporte ou Tester."));
    }

    @Test
    void testDelete() throws Exception {
        // Arrange
        ApiResponse apiResponse = new ApiResponse(true, "Profissional excluído com sucesso!");
        when(profissionaisService.delete(1L)).thenReturn(apiResponse);

        // Act & Assert
        mockMvc.perform(delete("/api/profissionais/v1/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Profissional excluído com sucesso!"));
    }

    @Test
    void testDelete_NotFound() throws Exception {
        // Arrange
        ApiResponse apiResponse = new ApiResponse(false, "Profissional não encontrado!");
        when(profissionaisService.delete(1L)).thenReturn(apiResponse);

        // Act & Assert
        mockMvc.perform(delete("/api/profissionais/v1/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Profissional não encontrado!"));
    }
}
