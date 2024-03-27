package br.arturslampert.minisena.modules.bet.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateBetRequestDTO {
    @Schema(example = "[1, 2, 3, 4, 5]")
    private int[] numbers;
}
