package br.arturslampert.minisena.modules.user;

import br.arturslampert.minisena.modules.user.dtos.CreateUserRequestDTO;
import br.arturslampert.minisena.modules.user.dtos.CreateUserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "User operations")
public class UserController {
    public UserController(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }
    CreateUserUseCase createUserUseCase;
    @PostMapping("/")
    @Operation(summary = "User creation", description = "Function used to create a new account")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = CreateUserResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "User with this e-mail already exists")
    })
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
