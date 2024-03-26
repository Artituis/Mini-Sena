package br.arturslampert.minisena.modules.user;

import br.arturslampert.minisena.modules.user.dtos.CreateUserRequestDTO;
import br.arturslampert.minisena.modules.user.dtos.CreateUserResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    public UserController(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }
    CreateUserUseCase createUserUseCase;
    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CreateUserRequestDTO createUserRequest){
        try{

            UserEntity userEntity = UserEntity.builder()
                    .name(createUserRequest.getName())
                    .email(createUserRequest.getEmail())
                    .password(createUserRequest.getPassword())
                    .build();

            UserEntity result = this.createUserUseCase.createUser(userEntity);

            CreateUserResponseDTO response = CreateUserResponseDTO.builder()
                    .name(result.getName())
                    .email(result.getEmail())
                    .build();

            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
