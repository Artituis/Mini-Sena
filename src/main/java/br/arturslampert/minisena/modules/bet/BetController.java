package br.arturslampert.minisena.modules.bet;

import br.arturslampert.minisena.modules.bet.usecases.CreateBetUseCase;
import br.arturslampert.minisena.modules.bet.usecases.GetUserBetsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bet")
@Tag(name = "Bet", description = "Bet operations")
public class BetController {
    private CreateBetUseCase createBetUseCase;
    private GetUserBetsUseCase getUserBetsUseCase;

    public BetController(CreateBetUseCase createBetUseCase, GetUserBetsUseCase getUserBetsUseCase) {
        this.createBetUseCase = createBetUseCase;
        this.getUserBetsUseCase = getUserBetsUseCase;
    }

    @PostMapping("/")
    @Operation(summary = "Bet creation", description = "Function used to create a new bet")
    public BetEntity create(@RequestBody BetEntity betEntity){
        return this.createBetUseCase.createBet(betEntity);
    }

    @GetMapping("/")
    @Operation(summary = "Get bettor's bets", description = "Get all the users bets from the current draw")
    public List<BetEntity> getBets(int bettorId){
        return this.getUserBetsUseCase.getBets(bettorId);
    }
}
