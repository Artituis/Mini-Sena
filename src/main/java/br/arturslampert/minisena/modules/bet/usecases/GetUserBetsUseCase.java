package br.arturslampert.minisena.modules.bet.usecases;

import br.arturslampert.minisena.modules.bet.BetEntity;
import br.arturslampert.minisena.modules.bet.BetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetUserBetsUseCase {
    private final BetRepository betRepository;

    public GetUserBetsUseCase(BetRepository betRepository) {
        this.betRepository = betRepository;
    }

    public List<BetEntity> getBets(int bettorId){
        return this.betRepository.findByBettorId(bettorId);
    }
}
