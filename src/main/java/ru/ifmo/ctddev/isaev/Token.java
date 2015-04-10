package ru.ifmo.ctddev.isaev;

import java.io.Serializable;

/**
 * Created by isae on 07.04.15.
 */
public enum Token implements LexicalUnit {
    LPAREN("(","("),
    RPAREN(")",")"),
    PLUS("+","+"),
    MINUS("-","-"),
    MUL("*","*"),
    END("$","$"),
    EPS("","eps");

    String token;
    String graphName;

    Token(String token, String graphName) {
        this.token = token;
        this.graphName = graphName;
    }

    @Override
    public String getStringRepresentation() {
        return token;
    }

    public static Token fromChar(char curChar) {
        for (Token t : values()) {
            if (t != END && t != EPS) {
                if (t.token.equals(Character.toString(curChar))) {
                    return t;
                }
            }
        }
        return null;
    }

    @Override
    public boolean equalsLexically(LexicalUnit other) {
        return this == other;
    }

    @Override
    public String getGraphRepresentation() {
        return graphName;
    }

    @Override
    public String toString() {
        return token == null ? "" : token;
    }
}
