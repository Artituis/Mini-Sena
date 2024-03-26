package br.arturslampert.minisena.modules.user.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserResponseDTO {

    @Schema(example = "Artur Santos")
    private String name;

    @Schema(example = "arturslampert@gmail.com")
    private String email;

}
