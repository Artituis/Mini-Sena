package br.arturslampert.minisena.modules.draw.usecases;

import br.arturslampert.minisena.modules.draw.entities.DrawEntity;
import br.arturslampert.minisena.modules.draw.entities.DrawStatus;
import br.arturslampert.minisena.modules.draw.DrawRepository;
import org.springframework.stereotype.Service;

@Service
public class GetCurrentDrawUseCase {
    private final DrawRepository drawRepository;

    public GetCurrentDrawUseCase(DrawRepository drawRepository) {
        this.drawRepository = drawRepository;
    }

    public DrawEntity getDraw(){
        var draw = drawRepository.findLastDraw();
        if (draw.isEmpty()
                || draw.get().getStatus().equals(DrawStatus.CLOSED)){
            DrawEntity newDraw = DrawEntity.builder().status(DrawStatus.OPEN).build();
            return drawRepository.save(newDraw);
        }
        return draw.get();
    }
}
