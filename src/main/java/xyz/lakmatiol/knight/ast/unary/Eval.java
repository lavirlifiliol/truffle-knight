package xyz.lakmatiol.knight.ast.unary;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.CachedContext;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.source.Source;
import xyz.lakmatiol.knight.Context;
import xyz.lakmatiol.knight.Knight;
import xyz.lakmatiol.knight.Parser;
import xyz.lakmatiol.knight.ast.Types;

import java.io.IOException;
import java.io.StringReader;

@NodeInfo(shortName = "EVAL")
public abstract class Eval extends Unary {
    @Specialization(
            guards = {"stringEquals(s, cacheS)"}, limit = "3"
    )
    public Object evalCached(
            String s,
            @Cached("s") String cacheS,
            @CachedContext(Knight.class) Context c,
            @Cached("create(parse(s, c))") DirectCallNode n
    ) {
        return n.call();
    }

    @CompilerDirectives.TruffleBoundary
    @Specialization(replaces = "evalCached")
    public Object evalUncached(String s, @CachedContext(Knight.class) Context c) {
        return parse(s, c).call();
    }

    protected CallTarget parse(String code, Context c) {
//        var src = Source.newBuilder("Knight", code, "(eval)").build();
//        return c.env.parsePublic(src);
        try {
            return Truffle.getRuntime().createCallTarget(new Parser(new StringReader(code), null).parse());
        } catch (IOException e) {
            Types.errorUB();
            return null;
        }
    }

    @Specialization
    public Object evalGen(Object o, @CachedContext(Knight.class) Context c) {
        return parse(Types.stringCoerce(o), c).call();
    }

    public boolean stringEquals(String l, String r) {
        return l.equals(r);
    }
}
