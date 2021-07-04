package xyz.lakmatiol.knight.ast.unary;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "SAME")
public abstract class Same extends Unary{
    @Specialization
    public Object doSame(Object a) {
        return a;
    }
}
