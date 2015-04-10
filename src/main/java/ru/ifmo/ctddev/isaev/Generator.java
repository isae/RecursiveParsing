package ru.ifmo.ctddev.isaev;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.*;

/**
 * Created by isae on 09.04.15.
 */
public class Generator {
    static Generator instance;
    static final Random rand = new Random(System.currentTimeMillis());

    static int WIDTH;

    private Generator() {
    }

    public static String generate(int depth, int width) {
        if (instance == null) instance = new Generator();
        WIDTH = width;
        StringBuilder sb = instance.E(depth);
        return sb.toString();
    }

    private StringBuilder E(int depth) {
        int width = rand.nextInt(WIDTH);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < width; ++i) {
            sb.append(T(depth));
            addSomeSpaces(sb);
            if (rand.nextBoolean()) {
                sb.append('+');

            } else {
                sb.append('-');
            }
        }
        addSomeSpaces(sb);
        sb.append(T(depth));
        return sb;
    }

    private StringBuilder T(int depth) {
        int width = rand.nextInt(WIDTH);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < width; ++i) {
            sb.append(F(depth));
            addSomeSpaces(sb);
            sb.append('*');
            addSomeSpaces(sb);
        }
        sb.append(F(depth));
        addSomeSpaces(sb);
        return sb;
    }

    private void addSomeSpaces(StringBuilder sb) {
        int t = rand.nextInt(7);
        for (int i = 0; i < t; ++i) {
            sb.append(' ');
        }
    }

    private StringBuilder F(int depth) {
        if (depth == 0) {
            return new StringBuilder(String.valueOf(rand.nextInt()));
        }
        int type = rand.nextInt(3);
        StringBuilder sb = new StringBuilder();
        switch (type) {
            case 0:
                sb.append('-');
                sb.append(F(depth));
                addSomeSpaces(sb);
                break;
            case 1:
                sb.append('(');
                addSomeSpaces(sb);
                sb.append(E(depth - 1));
                addSomeSpaces(sb);
                sb.append(')');
                break;
            case 2:
                sb.append(rand.nextInt());
                addSomeSpaces(sb);
                break;
        }
        return sb;
    }


}
