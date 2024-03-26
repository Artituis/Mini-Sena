package br.arturslampert.minisena.modules.user;

import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase {
    private final UserRepository userRepository;

    public CreateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity createUser(UserEntity userEntity){
        this.userRepository.findByEmail(userEntity.getEmail()).ifPresent(bettorEntity -> {
            throw new RuntimeException("User with this e-mail already exists");
        });
        return this.userRepository.save(userEntity);
    }
}
