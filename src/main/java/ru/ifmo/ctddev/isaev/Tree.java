package ru.ifmo.ctddev.isaev;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by isae on 07.04.15.
 */
public class Tree {
    String node;
    String graphName;
    List<Tree> children;

    public Tree(String node, Tree... subtrees) {
        this(node, Arrays.asList(subtrees));
    }

    public Tree(String node) {
        this(node, Collections.<Tree>emptyList());
    }

    public Tree(LexicalUnit token) {
        this(token.getStringRepresentation());
        this.graphName = token.getGraphRepresentation();
    }

    @Override
    public String toString() {
        if (children == null || children.size() == 0) return node;
        StringBuilder sb = new StringBuilder();
        for (Tree t : children) sb.append(t);
        return sb.toString();
    }

    public Tree(String node, List<Tree> subtrees) {
        this.node = node;
        this.children = subtrees;
        this.graphName = node;
    }

    public void writeGraphToFile(PrintWriter writer) {
        writeGraphToFile(writer, 0);
    }

    private class IntWrapper {
        int value = 0;

        public void inc() {
            ++value;
        }
    }


    private void writeGraphToFile(PrintWriter writer, Integer number) {
        writeGraphToFile(writer, new IntWrapper());
    }

    private void writeGraphToFile(PrintWriter writer, IntWrapper number) {
        int num = number.value;
        if (children.size() == 0&& !graphName.equals("eps")) {
            writer.println(String.format("\ta_%s [label=\"%s\"; style=filled; fillcolor=green;];", num, graphName));
        } else {
            writer.println(String.format("\ta_%s [label=\"%s\"];", num, graphName));
        }
        if (children != null) {
            List<Integer> numbers = new ArrayList<>();
            if (children != null) for (Tree t : children) {
                number.inc();
                numbers.add(number.value);
                t.writeGraphToFile(writer, number);
            }
            for (Integer i : numbers) {
                writer.println(String.format("\ta_%s -> a_%s", num, i));
            }
        }

    }
}
