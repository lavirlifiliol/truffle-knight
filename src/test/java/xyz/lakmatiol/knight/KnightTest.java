package xyz.lakmatiol.knight;

import org.graalvm.polyglot.Source;
import org.junit.jupiter.api.Test;
import org.graalvm.polyglot.Context;


import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class KnightTest {
    @Test
    public void doesRun() {
        var ctx = Context.newBuilder("Knight")
                .in(new ByteArrayInputStream("asdf\r\n".getBytes(StandardCharsets.UTF_8)))
                .out(System.out)
                .build();
        var src = Source.create("Knight", "P");
        System.out.println(ctx.eval(src));
    }

}