package xyz.lakmatiol.knight.ast.binary;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.nodes.NodeInfo;
import xyz.lakmatiol.knight.ast.Expression;

@NodeInfo(shortName = "&")
public class And extends ShortCircuit {
    public And(Expression l, Expression r) {
        super(l, r);
    }

    @Override
    protected boolean evalRight(boolean leftValue) {
        return leftValue;
    }

    @Override
    protected boolean executeBoolInternal(boolean leftValue, boolean rightValue) {
        return leftValue && rightValue;
    }
}
