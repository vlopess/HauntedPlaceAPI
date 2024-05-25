package com.hauntedplace.HauntedPlaceAPI.Services;

import com.hauntedplace.HauntedPlaceAPI.DTOS.UserDTO;
import com.hauntedplace.HauntedPlaceAPI.Entitys.User;
import com.hauntedplace.HauntedPlaceAPI.Repository.UserRepository;
import com.hauntedplace.HauntedPlaceAPI.Security.infra.NotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    protected UserRepository userRepository;

    @Mock
    private  PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;




    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Verifica se a busca pelo id do usuário foi feita com sucesso")
    void getUserByIdCase1() {

        //Arrange
        User user = new User(1L, "carlos", "carlos@gmail.com", "carlos123", "www.image.com", "eu sou o carlos", "carlopolis");
        when(userService.getUserById(any())).thenReturn(Optional.of(user));

        //Act
        var userGetted = userService.getUserById(1L);
        //Assert
        assertNotNull(userGetted);
        assertThat(userGetted.isPresent()).isTrue();

    }
    @Test
    @DisplayName("Lança um erro ao tentar buscar usuário pelo id não existente")
    void getUserByIdCase2() {
        //Arrange
        //Act
        var userGetted = userService.getUserById(1L);
        //Assert
        assertThat(userGetted.isEmpty()).isTrue();

    }

    @Test
    @DisplayName("Lança um erro ao tentar buscar usuário pelo username não existente")
    void getUserByUsername() {
        Exception thrown = assertThrows(NotFoundException.class, () -> userService.getUserByUsername("usertest"));
        assertThat(thrown).isInstanceOf(NotFoundException.class);
        assertThat(thrown.getMessage()).isEqualTo("User not found");

    }

    @Test
    @DisplayName("Verifica se a adição foi feita com sucesso")
    void addUser() {
        //Arrange
        User user = new User(1L, "carlos", "carlos@gmail.com", "carlos123", "www.image.com", "eu sou o carlos", "carlopolis");
        UserDTO userDTO = new UserDTO(user);
        when(userRepository.save(any())).thenReturn(user);
        when(passwordEncoder.encode(any())).thenReturn("password");

        //act
        User userSaved = userService.addUser(userDTO);

        ///assert
        verify(userRepository, times(1)).save(any());
        assertNotNull(userSaved);

    }

    @Test
    @DisplayName("Verifica se a edição foi feita com sucesso")
    void updateUserCase1() {
        //arrange
        User user = new User(1L, "carlos", "carlos@gmail.com", "carlos123", "www.image.com", "eu sou o carlos", "carlopolis");
        User newUser = new User(1L, "carlos cesar", "carlos@gmail.com", "carlos123", "www.image.com", "eu sou o carlos", "carlopolis");
        UserDTO userDTO = new UserDTO(user);
        UserDTO newUserDTO = new UserDTO(newUser);
        when(userRepository.save(any())).thenReturn(user);
        when(passwordEncoder.encode(any())).thenReturn("password");
        when(userService.getUserById(any())).thenReturn(Optional.of(user));
        when(userRepository.saveAndFlush(any())).thenReturn(new User(newUserDTO));

        //act
        userService.addUser(userDTO);
        userDTO.setUsername("carlos cesar");
        var userUpdated = userService.updateUser(1L,userDTO);
        verify(userRepository, times(1)).saveAndFlush(any());
        assertNotNull(userUpdated);
        assertThat(userUpdated.getUsername()).isEqualTo(userDTO.getUsername());

    }

    @Test
    @DisplayName("Lança um erro ao tentar editar usuário não existente")
    void updateUserCase2() {
        //arrange
        User user = new User(1L, "carlos", "carlos@gmail.com", "carlos123", "www.image.com", "eu sou o carlos", "carlopolis");
        UserDTO userDTO = new UserDTO(user);


        //assert
        Exception thrown = assertThrows(NotFoundException.class, () ->  userService.updateUser(9L,userDTO));
        assertThat(thrown).isInstanceOf(NotFoundException.class);
        assertThat(thrown.getMessage()).isEqualTo("User not found");
    }

    @Test
    @DisplayName("Lança um erro ao tentar deletar usuário não existente")
    void deleteUser() {
        Exception thrown = assertThrows(NotFoundException.class, () ->  userService.deleteUser(9L));
        assertThat(thrown).isInstanceOf(NotFoundException.class);
        assertThat(thrown.getMessage()).isEqualTo("User not found");
    }

}