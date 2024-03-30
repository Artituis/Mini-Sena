package br.arturslampert.minisena.modules.bet;

import br.arturslampert.minisena.modules.bet.usecases.CreateBetUseCase;
import br.arturslampert.minisena.modules.draw.entities.DrawEntity;
import br.arturslampert.minisena.modules.draw.entities.DrawStatus;
import br.arturslampert.minisena.modules.draw.usecases.GetCurrentDrawUseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateBetUseCaseTest {
    @InjectMocks
    private CreateBetUseCase createBetUseCase;
    @Mock
    private BetRepository betRepository;
    @Mock
    private GetCurrentDrawUseCase getCurrentDrawUseCase;
    @Test
    @DisplayName("Should not place bet while draw is being drawn")
    public void should_not_place_bet_while_draw_is_being_drawn(){
        DrawEntity draw = new DrawEntity();
        draw.setStatus(DrawStatus.DRAWING);
        when(getCurrentDrawUseCase.getDraw()).thenReturn(draw);
        BetEntity bet = new BetEntity();
        try{
            createBetUseCase.createBet(bet);
        }catch (Exception exception){
            Assertions.assertThat(exception).message().isEqualTo("Bets can't be places while numbers are being drawn");
        }
    }
    @Test
    @DisplayName("Should not place bet that has numbers < 1")
    public void should_not_place_bet_that_has_numbers_less_then_one(){
        DrawEntity draw = new DrawEntity();
        draw.setStatus(DrawStatus.OPEN);
        when(getCurrentDrawUseCase.getDraw()).thenReturn(draw);
        BetEntity bet = new BetEntity();
        int[] numbers = {0, 1, 2, 3, 4};
        bet.setNumbers(numbers);
        try{
            createBetUseCase.createBet(bet);
        }catch (Exception exception){
            Assertions.assertThat(exception).message().isEqualTo("Bet numbers have to be from 1 to 50");
        }
    }
    @Test
    @DisplayName("Should not place bet that has numbers > 50")
    public void should_not_place_bet_that_has_numbers_more_then_fifty(){
        DrawEntity draw = new DrawEntity();
        draw.setStatus(DrawStatus.OPEN);
        when(getCurrentDrawUseCase.getDraw()).thenReturn(draw);
        BetEntity bet = new BetEntity();
        int[] numbers = {1, 2, 3, 4, 51};
        bet.setNumbers(numbers);
        try{
            createBetUseCase.createBet(bet);
        }catch (Exception exception){
            Assertions.assertThat(exception).message().isEqualTo("Bet numbers have to be from 1 to 50");
        }
    }
    @Test
    @DisplayName("Should not place bet that has repeated numbers")
    public void should_not_place_bet_that_has_repeated_numbers(){
        DrawEntity draw = new DrawEntity();
        draw.setStatus(DrawStatus.OPEN);
        when(getCurrentDrawUseCase.getDraw()).thenReturn(draw);
        BetEntity bet = new BetEntity();
        int[] numbers = {1, 2, 3, 4, 4};
        bet.setNumbers(numbers);
        try{
            createBetUseCase.createBet(bet);
        }catch (Exception exception){
            Assertions.assertThat(exception).message().isEqualTo("Two bet numbers can't be equal");
        }
    }
}
