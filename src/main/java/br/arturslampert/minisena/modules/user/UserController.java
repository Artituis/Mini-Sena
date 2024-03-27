package br.arturslampert.minisena.modules.user;

import br.arturslampert.minisena.modules.user.dtos.AuthUserRequestDTO;
import br.arturslampert.minisena.modules.user.dtos.AuthUserResponseDTO;
import br.arturslampert.minisena.modules.user.dtos.CreateUserRequestDTO;
import br.arturslampert.minisena.modules.user.dtos.CreateUserResponseDTO;
import br.arturslampert.minisena.modules.user.usecases.AuthUserUseCase;
import br.arturslampert.minisena.modules.user.usecases.CreateUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "User operations")
public class UserController {
    CreateUserUseCase createUserUseCase;
    AuthUserUseCase authUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase, AuthUserUseCase authUserUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.authUserUseCase = authUserUseCase;
    }

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
    @PostMapping("/login/")
    @Operation(summary = "User Login", description = "Function used to log in the user account")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = AuthUserResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Username/password incorrect")
    })
    public ResponseEntity<Object> login(@RequestBody AuthUserRequestDTO authUserRequestDTO){
        try {
            var response = this.authUserUseCase.authenticate(authUserRequestDTO);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
