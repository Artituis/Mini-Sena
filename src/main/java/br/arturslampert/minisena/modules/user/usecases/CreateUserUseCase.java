package br.arturslampert.minisena.modules.user.usecases;

import br.arturslampert.minisena.modules.user.UserEntity;
import br.arturslampert.minisena.modules.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateUserUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity createUser(UserEntity userEntity){
        this.userRepository.findByEmail(userEntity.getEmail()).ifPresent(bettorEntity -> {
            throw new RuntimeException("User with this e-mail already exists");
        });
        String password = this.passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(password);
        return this.userRepository.save(userEntity);
    }
}
