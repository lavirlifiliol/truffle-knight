package xyz.lakmatiol.knight.ast.nullary;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import xyz.lakmatiol.knight.ast.Expression;

@NodeInfo(shortName = "bool")
public class Bool extends Expression {
    private final boolean value;

    public Bool(boolean value) {
        this.value = value;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return value;
    }

    @Override
    public boolean executeBool(VirtualFrame frame) {
        return value;
    }
}
