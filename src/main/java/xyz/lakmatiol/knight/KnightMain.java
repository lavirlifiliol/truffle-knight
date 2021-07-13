package xyz.lakmatiol.knight;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class KnightMain {
    public static void main(String[] args) throws IOException {
        if (args.length == 1) {
            System.out.println("usage: knight (-e 'expr' | -f filename)");
            return;
        }
        if (args.length == 3) {
            String source;
            if (args[1].equals("-f")) {
                source = Files.readString(Path.of(args[2]));
            } else if (args[1].equals("-e")) {
                source = args[2];
            } else {
                System.out.println("incorrect usage");
                return;
            }
            exec(source);
        }
    }

    private static void exec(String s) {
        var ctx = Context.newBuilder("Knight")
                .in(System.in)
                .out(System.out)
                .build();
        ctx.eval(Source.create("Knight", s));
    }
}
