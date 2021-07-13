package xyz.lakmatiol.knight.ast.binary;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import xyz.lakmatiol.knight.types.Null;

@NodeInfo(shortName = "?")
public abstract class Eq extends Binary{
    @Specialization
    boolean numEq(long l, long r) {
        return l == r;
    }
    @Specialization
    boolean strEq(String l, String r) {
        return l.equals(r);
    }
    @Specialization
    boolean boolEq(boolean l, boolean r) {
        return l == r;
    }
    @Specialization
    boolean nullEq(Null l, Null r) {
        return true;
    }
    @Specialization
    boolean genEq(Object l, Object r) {
        return false;
    }
}
