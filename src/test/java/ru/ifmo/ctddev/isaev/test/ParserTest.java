package ru.ifmo.ctddev.isaev.test;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import ru.ifmo.ctddev.isaev.Generator;
import ru.ifmo.ctddev.isaev.Parser;
import ru.ifmo.ctddev.isaev.Tree;

import java.text.ParseException;
import java.util.Random;

/**
 * Created by isae on 07.04.15.
 */
public class ParserTest {

    static final String[] simpleOkTests = new String[]{
            " ( 1+2 )*  ( -3*(   7- 4 ) + 2)",
            "(     1 + - 2 )",
            "   (  2 - - - 3  )",
            "--------------------5",//
            "-((-(1))+(1))+(((9+(-3-((((((7))))))))))"
    };

    static final String[] simpleWrongTests = new String[]{
            "()",
            "-",
            "1)",
            "(2",
            "(1)+()",
            "(1)+)5("
    };

    static final int EXPR_DEPTH = 6;
    static final int EXPR_WIDTH = 3;

    @Test
    public void testOkSimple() throws Exception {
        for (String test : simpleOkTests) {
            Tree tree = Parser.parse(test);
            Assert.assertEquals(test.replaceAll("\\s+", ""), tree.toString());
        }
    }

    @Test
    public void testErrorSimple() throws Exception {
        for (String test : simpleWrongTests) {
            try {
                Tree tree = Parser.parse(test);
                Assert.fail("Should not be parsed: " + test);
            } catch (ParseException e) {
                //ok
            }
        }
    }

    @Test
    public void bigRandomTest() throws Exception {
        boolean f = true;
        for (int i = 0; i < 10; ++i) {
            String s = Generator.generate(EXPR_DEPTH, EXPR_WIDTH);
            String s2 = s.replaceAll("\\s+", "");
            Tree tree = Parser.parse(s);
            Assert.assertEquals(s2, tree.toString());
        }
    }


}
