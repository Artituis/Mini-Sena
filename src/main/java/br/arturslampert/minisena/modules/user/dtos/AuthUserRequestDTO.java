package br.arturslampert.minisena.modules.user.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AuthUserRequestDTO {

    @Schema(example = "arturslampert@gmail.com")
    private String email;

    @Schema(example = "password")
    private String password;

}
