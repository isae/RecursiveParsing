package ru.ifmo.ctddev.isaev;

/**
 * Created by isae on 07.04.15.
 */
public class Number implements LexicalUnit {
    private int number;

    public int getNumber() {
        return number;
    }

    public Number(int number) {
        this.number = number;
    }

    @Override
    public String getStringRepresentation() {
        return Integer.toString(number);
    }

    @Override
    public boolean equalsLexically(LexicalUnit other) {
        return other instanceof Number;
    }

    @Override
    public String getGraphRepresentation() {
        return getStringRepresentation();
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }
}
