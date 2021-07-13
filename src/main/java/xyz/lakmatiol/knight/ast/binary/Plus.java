package xyz.lakmatiol.knight.ast.binary;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import xyz.lakmatiol.knight.ast.Expression;
import xyz.lakmatiol.knight.ast.Types;

@NodeInfo(shortName = "+")
public abstract class Plus extends Binary {
    @Specialization
    public long doSum(long l, long r) {
        return l + r;
    }
    @Specialization
    public long doSum(long l, Object r) {
        return l + Types.longCoerce(r);
    }
    @Specialization
    public String doString(String l, String r) {
        return l + r;
    }

    @Specialization
    public String doStringGen(String l, Object r) {
        return l + Types.stringCoerce(r);
    }

    @Specialization
    public String doGeneric(Object l, Object r) {
        Types.errorUB();
        return null;
    }
}
