package com.hauntedplace.HauntedPlaceAPI.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hauntedplace.HauntedPlaceAPI.DTOS.UserDTO;
import com.hauntedplace.HauntedPlaceAPI.Models.StringWrapper;
import com.hauntedplace.HauntedPlaceAPI.Services.UserService;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;



import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@WithMockUser
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private UserService userService;




    @Autowired
    public UserControllerTest(MockMvc mockMvc, UserService userService, ObjectMapper objectMapper){
        this.mockMvc = mockMvc;
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @Test
    @DisplayName("Buscar com sucesso todos os usuários")
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Busca com sucesso um usuário pelo seu username")
    void getByUsername() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/Samira"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Busca com sucesso um usuário pelo seu id")
    void getByIdCase1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user").param("encryptedId", "MTE="))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Busca um usuário não existente pelo seu id")
    void getByIdCase2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user").param("encryptedId", "MzQ="))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Adiciona com sucesso um usuário")
    @Transactional
    void addUser() throws Exception {
        UserDTO newuser = new UserDTO("Kevin", "kevin@gmail.com", "123456");
        var responseBody = mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newuser)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();
        assertNotNull(responseBody);
        var result = objectMapper.readValue(responseBody.getContentAsString(), StringWrapper.class);
        var resultEsperado = new StringWrapper("User added!");
        Assertions.assertThat(result.getValue()).isEqualTo(resultEsperado.getValue());
    }
}