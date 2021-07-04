package xyz.lakmatiol.knight.ast.nullary;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import xyz.lakmatiol.knight.ast.Expression;

@NodeInfo(shortName = "str")
public class Str extends Expression {
    public Str(String value) {
        this.value = value;
    }

    public final String value;

    @Override
    public Object execute(VirtualFrame frame) {
        return value;
    }
}
