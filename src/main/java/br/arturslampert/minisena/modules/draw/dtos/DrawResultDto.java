package br.arturslampert.minisena.modules.draw.dtos;

import br.arturslampert.minisena.modules.draw.entities.DrawEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DrawResultDto {
    private List<WinnerDTO> winners;
    private DrawEntity draw;
}
