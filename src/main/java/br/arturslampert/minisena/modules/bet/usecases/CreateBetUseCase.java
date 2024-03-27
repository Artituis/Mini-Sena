package br.arturslampert.minisena.modules.bet.usecases;

import br.arturslampert.minisena.modules.bet.BetEntity;
import br.arturslampert.minisena.modules.bet.BetRepository;
import br.arturslampert.minisena.modules.draw.entities.DrawStatus;
import br.arturslampert.minisena.modules.draw.usecases.GetCurrentDrawUseCase;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CreateBetUseCase {
    private final BetRepository betRepository;
    private final GetCurrentDrawUseCase getCurrentDrawUseCase;

    public CreateBetUseCase(BetRepository betRepository, GetCurrentDrawUseCase getCurrentDrawUseCase) {
        this.betRepository = betRepository;
        this.getCurrentDrawUseCase = getCurrentDrawUseCase;
    }

    public BetEntity createBet(BetEntity betEntity){
        checkNumbers(betEntity);
        var currentDraw = getCurrentDrawUseCase.getDraw();
        if (currentDraw.getStatus().equals(DrawStatus.DRAWING)){
            throw new RuntimeException("Bets can't be places while numbers are being drawn");
        }
        betEntity.setDrawId(currentDraw.getId());
        return this.betRepository.save(betEntity);
    }
    private void checkNumbers(BetEntity betEntity){
        Map<Integer,Integer> differentNumbers = new HashMap<>();

        for (int number : betEntity.getNumbers()) {

            if (number < 1 || 50 < number)
                throw new RuntimeException("Bet numbers have to be from 1 to 50");

            differentNumbers.put(number, number);

        }

        if(differentNumbers.size() < 5)
            throw new RuntimeException("Two bet numbers can't be equal");
    }
}
