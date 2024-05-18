package com.hauntedplace.HauntedPlaceAPI.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hauntedplace.HauntedPlaceAPI.Services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@WithMockUser
@AutoConfigureMockMvc
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
        mockMvc.perform(MockMvcRequestBuilders.get("/user/Victor"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Busca com sucesso um usuário pelo seu username")
    void getById() throws Exception {
        LinkedMultiValueMap<String, String> userID = new LinkedMultiValueMap<>();
        userID.add("encryptedId", "MzQ=");
        mockMvc.perform(MockMvcRequestBuilders.get("/user").params(userID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}