package xyz.lakmatiol.knight.ast.unary;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import xyz.lakmatiol.knight.ast.Types;

@NodeInfo(shortName = "EVAL")
public abstract class Eval extends Unary{
    @Specialization
    public Object eval(String s) {
        throw new RuntimeException("Eval not implemented");

    }
    @Specialization
    public Object evalUB(Object o) {
        return eval(Types.stringCoerce(o));
    }
}
