package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public  void serUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    public void registerUser() {
        String email = "tester@example.com";
        String name = "Tester";
        String password = "test";

        userService.registerUser(email,name, password);
        
        verify(userRepository).save(any());
    }

    @Test
    public void registerUserWithExistedEmail() {
        Assertions.assertThrows(EmailExistedException.class, () -> {
            String email = "tester@example.com";
            String name = "Tester";
            String password = "test";

            User user = User.builder().build();
            given(userRepository.findByEmail(email)).willReturn(Optional.of(user));

            userService.registerUser(email,name, password);

            verify(userRepository, never()).save(any());
        });
    }

    @Test
    public void authenticateWithValidAttributes() {
        String email = "tester@example.com";
        String password = "test";

        User mockUser = User.builder().email(email).build();

        given(userRepository.findByEmail(email))
                .willReturn(Optional.of(mockUser));

        given(passwordEncoder.matches(any(), any())).willReturn(true);

        User user = userService.authenticate(email, password);

        assertThat(user.getEmail(), is(email));
    }

    @Test
    public void authenticateWithNotExistedEmail() {
        Assertions.assertThrows(EmailNotExistedException.class, () -> {
            String email = "x@example.com";
            String password = "test";

            given(userRepository.findByEmail(email)).willReturn(Optional.empty());

            User user = userService.authenticate(email, password);

            assertThat(user.getEmail(), is(email));
        });

    }

    @Test
    public void authenticateWithWrongPassword() {
        String email = "tester@example.com";
        String password = "x";

        User mockUser = User.builder().email(email).build();

        given(userRepository.findByEmail(email))
                .willReturn(Optional.of(mockUser));

        given(passwordEncoder.matches(any(), any())).willReturn(false);

        assertThatThrownBy(() -> {
            userService.authenticate(email, password);
        }).isInstanceOf(PasswordWrongException.class);
    }
    
}