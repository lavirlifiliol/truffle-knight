package xyz.lakmatiol.knight.ast.nullary;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import xyz.lakmatiol.knight.ast.Expression;

@NodeInfo(shortName = "num")
public class Num extends Expression {
    public Num(long value) {
        this.value = value;
    }

    public final long value;

    @Override
    public long executeLong(VirtualFrame frame) {
        return value;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return value;
    }
}
