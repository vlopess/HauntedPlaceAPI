package com.hauntedplace.HauntedPlaceAPI.Services;

import com.hauntedplace.HauntedPlaceAPI.Entitys.User;
import com.hauntedplace.HauntedPlaceAPI.Entitys.UserFollower;
import com.hauntedplace.HauntedPlaceAPI.Repository.UserFollowerRepository;
import com.hauntedplace.HauntedPlaceAPI.Repository.UserRepository;
import com.hauntedplace.HauntedPlaceAPI.Security.infra.BadRequestException;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.Mockito.when;

class UserFollowerServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserFollowerRepository userFollowerRepository;

    @InjectMocks
    private UserFollowerService userFollowerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve seguir um usuário com sucesso")
    void followerUserCase1() {
        //arrange
        User userFollower = new User(1L, "carlos", "carlos@gmail.com", "carlos123", "www.image.com", "eu sou o carlos", "carlopolis");
        User userFollowed = new User(2L, "Amanda", "amanda@gmail.com", "manda123", "www.image.com", "eu sou a amanda", "amandapolis");
        when(userFollowerService.getUserById(1L)).thenReturn(Optional.of(userFollower));
        when(userFollowerService.getUserById(2L)).thenReturn(Optional.of(userFollowed));

        //act
        userFollowerService.followerUser(1L, 2L);
        userFollower = userFollowerService.getUserById(1L).get();
        userFollowed = userFollowerService.getUserById(2L).get();

        //assert
        Assertions.assertThat(userFollowed.getFollowing()).contains(userFollower);
    }

    @Test
    @DisplayName("Deve dar erro ao tentar seguir usuário já seguido")
    void followerUserCase2() {
        User userFollower = new User(1L, "carlos", "carlos@gmail.com", "carlos123", "www.image.com", "eu sou o carlos", "carlopolis");
        User userFollowed = new User(2L, "Amanda", "amanda@gmail.com", "manda123", "www.image.com", "eu sou a amanda", "amandapolis");
        when(userFollowerService.getUserById(1L)).thenReturn(Optional.of(userFollower));
        when(userFollowerService.getUserById(2L)).thenReturn(Optional.of(userFollowed));
        when(userRepository.save(any())).thenThrow(new DataIntegrityViolationException("is Already Followed!"));

        //act
        Exception thrown = assertThrows(DataIntegrityViolationException.class,  () -> userFollowerService.followerUser(1L, 2L));

        //assert
        Assertions.assertThat(userFollowed.getFollowing()).contains(userFollower);
        assertThat(thrown).isInstanceOf(DataIntegrityViolationException.class);
        assertThat(thrown.getMessage()).isEqualTo("is Already Followed!");
    }

    @Test
    @DisplayName("Deve deixar de seguir um usuário com sucesso")
    void unFollowerUserCase1() {
        //arrange
        User userFollower = new User(1L, "carlos", "carlos@gmail.com", "carlos123", "www.image.com", "eu sou o carlos", "carlopolis");
        User userFollowed = new User(2L, "Amanda", "amanda@gmail.com", "manda123", "www.image.com", "eu sou a amanda", "amandapolis");
        when(userFollowerService.getUserById(1L)).thenReturn(Optional.of(userFollower));
        when(userFollowerService.getUserById(2L)).thenReturn(Optional.of(userFollowed));
        when(userFollowerRepository.isAlreadyFollowed(any(), any())).thenReturn(new UserFollower());

        //act
        userFollowerService.followerUser(1L, 2L);
        userFollowerService.unFollowerUser(1L, 2L);
        userFollower = userFollowerService.getUserById(1L).get();
        userFollowed = userFollowerService.getUserById(2L).get();

        //assert
        Assertions.assertThat(userFollower.getFollowing()).doesNotContain(userFollowed);
    }

    @Test
    @DisplayName("Deve dar erro ao tentar seguir usuário já não seguido")
    void unFollowerUserCase2() {
        //arrange
        User userFollower = new User(1L, "carlos", "carlos@gmail.com", "carlos123", "www.image.com", "eu sou o carlos", "carlopolis");
        User userFollowed = new User(2L, "Amanda", "amanda@gmail.com", "manda123", "www.image.com", "eu sou a amanda", "amandapolis");
        when(userFollowerService.getUserById(1L)).thenReturn(Optional.of(userFollower));
        when(userFollowerService.getUserById(2L)).thenReturn(Optional.of(userFollowed));
        when(userFollowerRepository.isAlreadyFollowed(any(), any())).thenReturn(null);

        //act
        Exception thrown = assertThrows(BadRequestException.class, () -> userFollowerService.unFollowerUser(1L, 2L));


        userFollower = userFollowerService.getUserById(1L).get();
        userFollowed = userFollowerService.getUserById(2L).get();

        //assert
        Assertions.assertThat(userFollower.getFollowing()).doesNotContain(userFollowed);
        assertThat(thrown).isInstanceOf(BadRequestException.class);
        assertThat(thrown.getMessage()).isEqualTo("is Already not Unfollowed!");
    }
}