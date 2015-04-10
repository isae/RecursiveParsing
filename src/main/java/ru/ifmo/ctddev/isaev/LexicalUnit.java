package ru.ifmo.ctddev.isaev;

/**
 * Created by isae on 07.04.15.
 */
public interface LexicalUnit {
    public String getStringRepresentation();
    public boolean equalsLexically(LexicalUnit other);

    String getGraphRepresentation();
}
