package br.arturslampert.minisena.modules.draw.usecases;

import br.arturslampert.minisena.modules.bet.BetEntity;
import br.arturslampert.minisena.modules.bet.BetRepository;
import br.arturslampert.minisena.modules.draw.dtos.DrawResultDto;
import br.arturslampert.minisena.modules.draw.entities.DrawStatus;
import br.arturslampert.minisena.modules.draw.DrawRepository;
import br.arturslampert.minisena.modules.draw.utils.NumberGenerator;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StartDrawUseCase {
    private final GetCurrentDrawUseCase getCurrentDrawUseCase;
    private final DrawRepository drawRepository;
    private final NumberGenerator numberGenerator;
    private final BetRepository betRepository;
    private final GetResultUseCase getResultUseCase;

    public StartDrawUseCase(GetCurrentDrawUseCase getCurrentDrawUseCase, DrawRepository drawRepository, NumberGenerator numberGenerator, BetRepository betRepository, GetResultUseCase getResultUseCase) {
        this.getCurrentDrawUseCase = getCurrentDrawUseCase;
        this.drawRepository = drawRepository;
        this.numberGenerator = numberGenerator;
        this.betRepository = betRepository;
        this.getResultUseCase = getResultUseCase;
    }

    public DrawResultDto startDraw(){
        var currentDraw = getCurrentDrawUseCase.getDraw();
        if (currentDraw.getStatus().equals(DrawStatus.OPEN)){

            List<BetEntity> bets = betRepository.findByDrawId(currentDraw.getId());

            if (bets.size()==0) throw new RuntimeException("No bets have been placed this round");

            currentDraw.setStatus(DrawStatus.DRAWING);
            drawRepository.save(currentDraw);

            int[] numbers = getNumbers();

            currentDraw.setNumbers(numbers);

            drawRepository.save(currentDraw);
        }
        DrawResultDto winnerResponse = getResultUseCase.getResult();
        return DrawResultDto.builder()
                .winners(winnerResponse.getWinners())
                .draw(currentDraw)
                .build();
    }
    private int[] getNumbers(){
        Set<Integer> integerSet = new HashSet<>();
        while (integerSet.size() <= 5){
            integerSet.add(numberGenerator.getNumber());
        }
        return integerSet.stream().mapToInt(Number::intValue).toArray();
    }
}
