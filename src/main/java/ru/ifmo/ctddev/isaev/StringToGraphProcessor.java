package ru.ifmo.ctddev.isaev;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;

/**
 * Created by isae on 10.04.15.
 */
public class StringToGraphProcessor {
    public static void main(String[] args) {
        if (args.length != 2) {
            stopExecution(0, "Usage: java StringToGraphProcessor <string> <file>");
        }
        PrintWriter writer = null;
        String str = args[0];
        try {
            writer = new PrintWriter(args[1]);

            writer.println("digraph parseTree {");
            writer.println("\tordering=out;");
            Tree tree = Parser.parse(str);
            tree.writeGraphToFile(writer);
            writer.println("}");
            writer.close();
        } catch (ParseException e) {
            System.out.println("Cannot parse " + str + " : " + e.getMessage() + " at position " + e.getErrorOffset());
        } catch (FileNotFoundException e) {
            stopExecution(1, "File not found: " + args[1]);
        }
    }

    private static void stopExecution(int code, String message) {
        System.out.println(message);
        System.exit(code);
    }
}
