package br.arturslampert.minisena.modules.user;


import br.arturslampert.minisena.modules.user.usecases.CreateUserUseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateUserUseCaseTest {
    @InjectMocks
    private CreateUserUseCase createUserUseCase;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("Should not be able to create new user if email already in use")
    public void should_not_be_able_to_create_new_user_if_email_already_in_use(){
        String email = "existing@email.com";
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));
        try{
            createUserUseCase.createUser(userEntity);
        }catch (Exception exception){
            Assertions.assertThat(exception).message().isEqualTo("User with this e-mail already exists");
        }
    }
/*
    I was not able to complete this test
    @Test
    @DisplayName("Should have password encoded when user is created")
    public void should_have_password_encoded_when_user_is_created(){
        UserEntity userEntity = new UserEntity();
        String password = UUID.randomUUID().toString();
        String email = "new@email.com";
        userEntity.setPassword(password);
        userEntity.setEmail(email);
        UserEntity resultUser = createUserUseCase.createUser(userEntity);
        String encodedPassword = passwordEncoder.encode(password);
        assertThat(resultUser.getPassword()).isEqualTo(encodedPassword);
    }
 */
}
