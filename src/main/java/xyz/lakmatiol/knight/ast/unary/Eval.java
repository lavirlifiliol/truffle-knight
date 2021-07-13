package xyz.lakmatiol.knight.ast.unary;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.CachedContext;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.source.Source;
import xyz.lakmatiol.knight.Context;
import xyz.lakmatiol.knight.Knight;
import xyz.lakmatiol.knight.ast.Types;

@NodeInfo(shortName = "EVAL")
public abstract class Eval extends Unary {
    @Specialization(
            guards={"stringEquals(s, cacheS)"}, limit = "3"
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
        var src = Source.newBuilder("Knight", code, "(eval)").build();
        return c.env.parsePublic(src);
    }

    @Specialization
    @CompilerDirectives.TruffleBoundary
    public Object evalGen(Object o, @CachedContext(Knight.class) Context c) {
        return parse(Types.stringCoerce(o), c).call();
    }
    public boolean stringEquals(String l, String r) {
        return l.equals(r);
    }
}
