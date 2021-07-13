package xyz.lakmatiol.knight.ast.binary;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import xyz.lakmatiol.knight.ast.Types;
import xyz.lakmatiol.knight.ast.TypesGen;

@NodeInfo(shortName = "/")
public abstract class Div extends Binary{
    @Specialization
    public long doDiv(long l, long r) {
        return l / r;
    }
    @Specialization
    public long doDivGen(long l, Object r) {
        return l / TypesGen.longCoerce(r);
    }
    @Specialization
    public long doUB(Object l, Object r) {
        Types.errorUB();
        return 0;
    }
}
