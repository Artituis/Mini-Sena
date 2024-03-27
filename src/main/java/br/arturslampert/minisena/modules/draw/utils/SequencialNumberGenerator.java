package br.arturslampert.minisena.modules.draw.utils;

//@Component
public class SequencialNumberGenerator implements NumberGenerator{
    private int number;

    public SequencialNumberGenerator() {
        this.number = 0;
    }

    @Override
    public int getNumber() {
        return number++;
    }
}
