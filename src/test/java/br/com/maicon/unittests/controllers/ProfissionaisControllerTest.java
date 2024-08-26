/**
 * 
 */
package br.com.maicon.unittests.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.maicon.controllers.ProfissionaisController;
import br.com.maicon.data.dto.v1.ProfissionaisDTO;
import br.com.maicon.services.ProfissionaisService;
import br.com.maicon.util.ApiResponse;

public class ProfissionaisControllerTest {

    @Mock
    private ProfissionaisService service;

    @InjectMocks
    private ProfissionaisController controller;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testFindAll() throws Exception {
        // Arrange
        ProfissionaisDTO dto1 = new ProfissionaisDTO();
        dto1.setId(1L);
        ProfissionaisDTO dto2 = new ProfissionaisDTO();
        dto2.setId(2L);
        List<ProfissionaisDTO> dtos = Arrays.asList(dto1, dto2);

        when(service.findAll()).thenReturn(dtos);
        

        // Act & Assert
        mockMvc.perform(get("/profissionais")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }

    @Test
    void testFindById() throws Exception {
        // Arrange
        ProfissionaisDTO dto = new ProfissionaisDTO();
        dto.setId(1L);

        when(service.findById(1L)).thenReturn(dto);

        // Act & Assert
        mockMvc.perform(get("/profissionais/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testCreate() throws Exception {
        // Arrange
        ProfissionaisDTO dto = new ProfissionaisDTO();
        dto.setNome("Nome Teste");

        ApiResponse response = new ApiResponse(true, "Profissional criado com sucesso!");

        when(service.create(any(ProfissionaisDTO.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/profissionais")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Profissional criado com sucesso!"));
    }

    @Test
    void testUpdate() throws Exception {
        // Arrange
        ProfissionaisDTO dto = new ProfissionaisDTO();
        dto.setId(1L);
        ApiResponse apiResponse = new ApiResponse(true, "Atualizado com sucesso");

        when(service.update(any(ProfissionaisDTO.class))).thenReturn(apiResponse);

        // Act & Assert
        mockMvc.perform(put("/profissionais/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"nome\": \"Novo Nome\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Atualizado com sucesso"));
    }

    @Test
    void testDelete() throws Exception {
        // Arrange
        ApiResponse response = new ApiResponse(true, "Profissional excluído com sucesso!");

        when(service.delete(1L)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(delete("/profissionais/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Profissional excluído com sucesso!"));
    }
}

