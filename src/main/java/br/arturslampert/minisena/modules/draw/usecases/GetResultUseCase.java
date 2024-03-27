package br.arturslampert.minisena.modules.draw.usecases;

import br.arturslampert.minisena.modules.bet.BetEntity;
import br.arturslampert.minisena.modules.bet.BetRepository;
import br.arturslampert.minisena.modules.draw.dtos.DrawResultDto;
import br.arturslampert.minisena.modules.draw.dtos.WinnerDTO;
import br.arturslampert.minisena.modules.draw.entities.DrawEntity;
import br.arturslampert.minisena.modules.user.UserEntity;
import br.arturslampert.minisena.modules.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class GetResultUseCase {
    private final BetRepository betRepository;
    private final UserRepository userRepository;
    private final DrawEntity currentDraw;
    public GetResultUseCase(GetCurrentDrawUseCase getCurrentDrawUseCase, BetRepository betRepository, UserRepository userRepository) {
        this.betRepository = betRepository;
        this.userRepository = userRepository;
        currentDraw = getCurrentDrawUseCase.getDraw();
    }

    public DrawResultDto getResult(){
        List<BetEntity> bets = betRepository.findByDrawId(currentDraw.getId());
        List<WinnerDTO> winners = new ArrayList<>();
        for (BetEntity bet: bets) {
            if (Arrays.equals(bet.getNumbers(), currentDraw.getNumbers())) {
                Optional<UserEntity> winner = userRepository.findById(bet.getBettorId());
                winners.add(WinnerDTO.builder().name(winner.get().getName()).build());
            }
        }

        return DrawResultDto.builder()
                .winners(winners)
                .draw(currentDraw)
                .build();
    }
}
