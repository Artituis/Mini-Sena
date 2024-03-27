package br.arturslampert.minisena.modules.draw.utils;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@AllArgsConstructor
@Component
public class RandomNumberGenerator implements NumberGenerator{
    @Override
    public int getNumber() {
        Random random = new Random();
        return random.nextInt(49)+1;
    }
}
