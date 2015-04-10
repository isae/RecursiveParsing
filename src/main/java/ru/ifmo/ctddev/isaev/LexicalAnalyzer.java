package ru.ifmo.ctddev.isaev;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

/**
 * Created by isae on 07.04.15.
 */
public class LexicalAnalyzer {
    public static final int EXPECTED_NUMBER_LENGTH = 8;
    InputStream is;
    private int curChar = -1;
    int curPos;
    boolean alreadyRead;

    public LexicalAnalyzer(InputStream is) throws ParseException {
        this.is = is;
        curPos = 0;
        nextChar();
    }

    private void skipSpaces() throws ParseException {
        while (Character.isWhitespace((char) curChar)) {
            nextChar();
        }
    }

    private void nextChar() throws ParseException {
            curPos++;
            try {
                curChar = is.read();
            } catch (IOException e) {
                throw new ParseException(e.getMessage(), curPos);
            }
    }

    public LexicalUnit nextToken() throws ParseException {
        skipSpaces();
        if (curChar == -1) {
            return Token.END;
        }
        char current = (char) curChar;
        Token token = Token.fromChar(current);
        if (token != null) {
            nextChar();
            return token;
        } else {
            if (Character.isDigit(current)) {
                StringBuilder sb = new StringBuilder(EXPECTED_NUMBER_LENGTH);
                sb.append(current);
                while (true) {
                    nextChar();
                    char ch = (char) curChar;
                    if (Character.isDigit((char) curChar)) {
                        sb.append((char) curChar);
                    } else break;
                }
                alreadyRead = true;
                return new Number(Integer.parseInt(sb.toString()));
            }
        }
        throw new ParseException("Illegal character "
                + (char) curChar, curPos);
    }

    public int curPos() {
        return curPos();
    }
}
