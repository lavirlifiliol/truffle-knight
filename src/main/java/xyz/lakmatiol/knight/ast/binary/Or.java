package xyz.lakmatiol.knight.ast.binary;

import com.oracle.truffle.api.nodes.NodeInfo;
import xyz.lakmatiol.knight.ast.Expression;

@NodeInfo(shortName = "|")
public class Or extends ShortCircuit{
    public Or(Expression l, Expression r) {
        super(l, r);
    }

    @Override
    protected boolean evalRight(boolean leftValue) {
        return !leftValue;
    }
}
