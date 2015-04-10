package ru.ifmo.ctddev.isaev;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.*;

/**
 * Created by isae on 07.04.15.
 */
public class Parser {
    LexicalAnalyzer lex;
    static Parser instance;
    LexicalUnit curToken;

    private Parser() {
    }

    public static Tree parse(InputStream is) throws ParseException {
        if (instance == null) instance = new Parser();
        instance.lex = new LexicalAnalyzer(is);
        instance.curToken = instance.lex.nextToken();
        Tree tree = instance.E();
        if (instance.curToken != Token.END)
            throw new ParseException("Parsing is finished, end of stream not reached", instance.lex.curPos);
        return tree;
    }

    public static Tree parse(String test) throws ParseException {
        return parse(new ByteArrayInputStream(test.getBytes(StandardCharsets.UTF_8)));
    }


    private void checkCurToken(LexicalUnit... tokens) throws ParseException {
        boolean f = false;
        for (LexicalUnit unit : tokens) {
            if (unit.equalsLexically(curToken)) {
                f = true;
                break;
            }
        }
        if (!f) {
            StringBuilder sb = new StringBuilder("Unexpected symbol ");
            sb.append(curToken.getStringRepresentation()).append(" ; expected one of the {");
            Arrays.asList(tokens).forEach(t -> sb.append(t.getStringRepresentation()).append(" ; "));
            sb.append("}");
            throw new ParseException(sb.toString(), lex.curPos);

        }
    }

    private Tree E() throws ParseException {
        checkCurToken(Token.MINUS, Token.LPAREN, new Number(-1));//first
        Tree tree;
        StringBuilder sb = new StringBuilder();
        List<Tree> subtrees = new ArrayList<>();
        tree = new Tree("E",subtrees);
        subtrees.add(T());
        subtrees.add(EPrime());
        return tree;
    }


    private Tree EPrime() throws ParseException {
        Tree tree;
        StringBuilder sb = new StringBuilder();
        List<Tree> subtrees = new ArrayList<>();
        tree = new Tree("E\'",subtrees);
        if (curToken == Token.PLUS) {
            subtrees.add(new Tree(Token.PLUS));
            curToken = lex.nextToken();
            subtrees.add(T());
            subtrees.add(EPrime());
        } else if (curToken == Token.MINUS) {
            subtrees.add(new Tree(Token.MINUS));
            curToken = lex.nextToken();
            subtrees.add(T());
            subtrees.add(EPrime());
        } else {
            subtrees.add(new Tree(Token.EPS));
        }
        checkCurToken(Token.RPAREN, Token.END);//follow
        return tree;
    }

    private Tree T() throws ParseException {
        checkCurToken(Token.LPAREN, Token.MINUS, new Number(-1));//first
        Tree tree;
        StringBuilder sb = new StringBuilder();
        List<Tree> subtrees = new ArrayList<>();
        tree = new Tree("T", subtrees);
        subtrees.add(F());
        subtrees.add(TPrime());
        return tree;
    }

    private Tree TPrime() throws ParseException {
        Tree tree;
        StringBuilder sb = new StringBuilder();
        List<Tree> subtrees = new ArrayList<>();
        tree = new Tree("T\'", subtrees);
        if (curToken == Token.MUL) {
            subtrees.add(new Tree(Token.MUL));
            curToken = lex.nextToken();
            subtrees.add(F());
            subtrees.add(TPrime());
        } else {
            subtrees.add(new Tree(Token.EPS));
        }
        checkCurToken(Token.PLUS, Token.MINUS, Token.RPAREN, Token.END);//follow
        return tree;
    }

    private Tree F() throws ParseException {
        checkCurToken(Token.MINUS, Token.LPAREN, new Number(-1));
        Tree tree;
        StringBuilder sb = new StringBuilder();
        List<Tree> subtrees = new ArrayList<>();
        tree = new Tree("F\'", subtrees);
        if (curToken == Token.MINUS) {
            subtrees.add(new Tree(Token.MINUS));
            curToken = lex.nextToken();
            subtrees.add(F());
        } else if (curToken == Token.LPAREN) {
            subtrees.add(new Tree(Token.LPAREN));
            curToken = lex.nextToken();
            subtrees.add(E());
            checkCurToken(Token.RPAREN);
            subtrees.add(new Tree(Token.RPAREN));
            curToken = lex.nextToken();
        } else {
            subtrees.add(new Tree(curToken));
            curToken = lex.nextToken();
        }
        return tree;
    }


}
