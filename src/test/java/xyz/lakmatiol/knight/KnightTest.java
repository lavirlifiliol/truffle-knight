package xyz.lakmatiol.knight;

import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.graalvm.polyglot.Context;
import xyz.lakmatiol.knight.types.Null;


import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

class KnightTest {
    Context ctx = null;

    @BeforeEach
    void setupCtx() {
        ctx = Context.newBuilder("Knight")
                .in(new ByteArrayInputStream("asdf\r\nfdsa\r\n".getBytes(StandardCharsets.UTF_8)))
                .out(System.out)
                .build();
    }

    protected void assertProduces(String s, Object result) {
        var src = Source.create("Knight", s);
        Assertions.assertEquals(result, ctx.eval(src).as(result.getClass()));
    }

    protected void assertProduces(String s, boolean result) {
        var src = Source.create("Knight", s);
        Assertions.assertEquals(result, ctx.eval(src).asBoolean());
    }

    protected void assertProduces(String s, long result) {
        var src = Source.create("Knight", s);
        Assertions.assertEquals(result, ctx.eval(src).asLong());
    }

}

class NullaryTest extends KnightTest {
    @Test
    void testBool() {
        assertProduces("T", true);
        assertProduces("F", false);
    }

    @Test
    void testNull() {
        Assertions.assertEquals("null", ctx.eval(Source.create("Knight", "N")).toString());
    }

    @Test
    void testNum() {
        assertProduces("12", 12);
    }

    @Test
    void testStr() {
        assertProduces("\"hello world\"", "hello world");
    }

    @Test
    void testPrompt() {
        assertProduces("P", "asdf");
    }
    @Test
    void testRandom() {
        var src = Source.create("Knight", "R");
        Assertions.assertTrue(ctx.eval(src).fitsInLong());
    }
}

class UnaryTest extends KnightTest {
    @Test
    void testBlockCall() {
        assertProduces("C B 5", 5);
    }
    @Test
    void testNot() {
        assertProduces("! T", false);
        assertProduces("! F", true);
        assertProduces("! N", true);
        assertProduces("! \"\"", true);
        assertProduces("! 0", true);
        assertProduces("! 1", false);
        assertProduces("! \"a\"", false);
    }
    @Test
    void testLength() {
        assertProduces("L \"\"", 0);
        assertProduces("L \"12345\"", 5);
        assertProduces("L 12345", 5);
        assertProduces("L N", 4);
        assertProduces("L T", 4);
        assertProduces("L F", 5);
    }
    @Test
    void testAscii() {
        assertProduces("A 38", "&");
        assertProduces("A \"&asdf\"", 38);
    }
}

class BinaryTest extends KnightTest {
    @Test
    void testSeq() {
        assertProduces("; 1 ; 2 ; 3 ; 4 : 5", 5);
        assertProduces("; 1 ; P ; 3 ; 4 : P", "fdsa");
    }
}