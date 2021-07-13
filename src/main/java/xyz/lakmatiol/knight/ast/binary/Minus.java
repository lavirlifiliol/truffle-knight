package xyz.lakmatiol.knight.ast.binary;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import xyz.lakmatiol.knight.ast.TypesGen;

@NodeInfo(shortName = "-")
public abstract class Minus extends Binary{
    @Specialization
    public long doSub(long l, long r) {
        return l - r;
    }
    @Specialization
    public long doSubGen(long l, Object r) {
        return l - TypesGen.longCoerce(r);
    }
}
