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
    @Test
    void testParser() {
        assertProduces(";=next_ast_name_0+'__ast_fn_'0 next_ast_name_0", "__ast_fn_0");
    }
    @Test
    void testBlockTakesVariablesAfterwards() {
        assertProduces("; = bar BLOCK + 4 foo ; = foo 3 : CALL bar", 7);
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
    @Test
    void testEval() {
        assertProduces("+++ E \"23\" E \"23\" E \"12\" E \"+ 10 11\"", 23+23+12+10+11);
    }
}

class BinaryTest extends KnightTest {
    @Test
    void testSeq() {
        assertProduces("; 1 ; 2 ; 3 ; 4 : 5", 5);
        assertProduces("; 1 ; P ; 3 ; 4 : P", "fdsa");
    }
    @Test
    void testVariable() {
        assertProduces("; = a 3 ; = u BLOCK a : + a C u", 6);
    }
    @Test
    void testWhile() {
        assertProduces("; = a 0 ; W < a 5 = a + a 1 a", 5);
    }
    @Test
    void testComparison() {
        assertProduces("> 5 5", false);
        assertProduces("< 5 5", false);
        assertProduces("> 4 5", false);
        assertProduces("< 5 4", false);
        assertProduces("? 5 5", true);
    }
    @Test
    void testShortCircuit() {
        assertProduces("; = a 3 : CALL | 0 BLOCK a", 3);
        assertProduces("; = a 3 : CALL & 1 BLOCK a", 3);
    }
}