package xyz.lakmatiol.knight.ast.binary;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import xyz.lakmatiol.knight.ast.Types;
import xyz.lakmatiol.knight.ast.TypesGen;

@NodeInfo(shortName = "*")
public abstract class Times extends Binary {
    @Specialization
    public long doTimes(long l, long r) {
        return l * r;
    }

    @Specialization
    public long doTimesGen(long l, Object r) {
        return l * TypesGen.longCoerce(r);
    }

    @Specialization
    public String doRepeat(String l, long r) {
        return l.repeat((int) r);
    }

    @Specialization
    public String doRepeatGen(String l, Object r) {
        return l.repeat((int) TypesGen.longCoerce(r));
    }

    @Specialization
    public Object doUB(Object l, Object r) {
        throw new RuntimeException("UB");
    }
}
