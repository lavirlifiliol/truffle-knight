package xyz.lakmatiol.knight.ast.binary;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import xyz.lakmatiol.knight.ast.Types;

@NodeInfo(shortName = ">")
public abstract class Gt extends Binary{
    @Specialization
    boolean gtNum(long l, long r) {
        return l > r;
    }
    @Specialization
    boolean gtNumGen(long l, Object r) {
        return l > Types.longCoerce(r);
    }
    @Specialization
    boolean gtStr(String l, String r) {
        return l.compareTo(r) > 0;
    }
    @Specialization
    boolean gtStrGen(String l, Object r) {
        return l.compareTo(Types.stringCoerce(r)) > 0;
    }
    @Specialization
    boolean gtBoolGen(boolean l, Object r) {
        return l  && !Types.boolCoerce(r);
    }
}
