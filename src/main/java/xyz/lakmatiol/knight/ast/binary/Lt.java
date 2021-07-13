package xyz.lakmatiol.knight.ast.binary;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import xyz.lakmatiol.knight.ast.Types;

@NodeInfo(shortName = "<")
public abstract class Lt extends Binary{
    @Specialization
    boolean ltNum(long l, long r) {
        return l < r;
    }
    @Specialization
    boolean ltNumGen(long l, Object r) {
        return l < Types.longCoerce(r);
    }
    @Specialization
    boolean ltStr(String l, String r) {
        return l.compareTo(r) < 0;
    }
    @Specialization
    boolean ltStrGen(String l, Object r) {
        return l.compareTo(Types.stringCoerce(r)) < 0;
    }
    @Specialization
    boolean ltBoolGen(boolean l, Object r) {
        return !l  && Types.boolCoerce(r);
    }
}
