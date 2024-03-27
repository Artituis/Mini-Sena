package br.arturslampert.minisena.modules.draw;

import br.arturslampert.minisena.modules.draw.dtos.DrawResultDto;
import br.arturslampert.minisena.modules.draw.entities.DrawEntity;
import br.arturslampert.minisena.modules.draw.usecases.GetCurrentDrawUseCase;
import br.arturslampert.minisena.modules.draw.usecases.StartDrawUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/draw")
@Tag(name = "Draw", description = "Functions related to drawing the numbers")
public class DrawController {
    private final GetCurrentDrawUseCase getCurrentDrawUseCase;
    private final StartDrawUseCase startDrawUseCase;

    public DrawController(GetCurrentDrawUseCase getCurrentDrawUseCase, StartDrawUseCase startDrawUseCase) {
        this.getCurrentDrawUseCase = getCurrentDrawUseCase;
        this.startDrawUseCase = startDrawUseCase;
    }

    @GetMapping("/")
    @Operation(summary = "Get the current draw", description = "Returns the draw that is currently open to bets, if there are none it creates a new draw and returns it.")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('USER')")
    public DrawEntity getCurrentDraw(){
        return getCurrentDrawUseCase.getDraw();
    }

    @GetMapping("/start")
    @Operation(summary = "Starts the current draw")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('USER')")
    public DrawResultDto startDraw(){
        return startDrawUseCase.startDraw();
    }

}
