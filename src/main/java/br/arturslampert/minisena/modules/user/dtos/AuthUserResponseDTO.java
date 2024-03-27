package br.arturslampert.minisena.modules.user.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserResponseDTO {

    @Schema(example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MTA3Njc4ODcsInN1YiI6IjAxMjM0NTY3OCJ9.kAfVaIq1iYs9lC8p0uRyaQBQMFI18CZrGkce-phutIM")
    private String access_token;

    @Schema(example = "1710767887498")
    private Long expires_in;

}
