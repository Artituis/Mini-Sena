package br.arturslampert.minisena.modules.bet;

import br.arturslampert.minisena.modules.bet.dtos.CreateBetResponseDTO;
import br.arturslampert.minisena.modules.bet.dtos.CreateBetRequestDTO;
import br.arturslampert.minisena.modules.bet.usecases.CreateBetUseCase;
import br.arturslampert.minisena.modules.bet.usecases.GetUserBetsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bet")
@Tag(name = "Bet", description = "Bet operations")
public class BetController {
    private final CreateBetUseCase createBetUseCase;
    private final GetUserBetsUseCase getUserBetsUseCase;

    public BetController(CreateBetUseCase createBetUseCase, GetUserBetsUseCase getUserBetsUseCase) {
        this.createBetUseCase = createBetUseCase;
        this.getUserBetsUseCase = getUserBetsUseCase;
    }

    @PostMapping("/")
    @PreAuthorize(value = "hasRole('USER')")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Bet creation", description = "Function used to create a new bet")
    public CreateBetResponseDTO create(@RequestBody CreateBetRequestDTO createBetRequestDTO, HttpServletRequest httpServletRequest){
        Object userId = httpServletRequest.getAttribute("user_id");

        BetEntity betEntity = BetEntity.builder()
                .bettorId(Integer.parseInt(userId.toString()))
                .numbers(createBetRequestDTO.getNumbers())
                .build();

        betEntity = this.createBetUseCase.createBet(betEntity);

        return CreateBetResponseDTO.builder()
                .numbers(betEntity.getNumbers())
                .build();
    }

    @GetMapping("/")
    @PreAuthorize(value = "hasRole('USER')")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Get user's bets", description = "Get all the users bets from the current draw")
    public List<BetEntity> getBets(HttpServletRequest httpServletRequest){
        Object userId = httpServletRequest.getAttribute("user_id");
        return this.getUserBetsUseCase.getBets(Integer.parseInt(userId.toString()));
    }
}
