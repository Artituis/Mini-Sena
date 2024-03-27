package br.arturslampert.minisena.modules.bet.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateBetResponseDTO {
    @Schema(example = "[1, 2, 3, 4, 5]")
    private int[] numbers;
}
