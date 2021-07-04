package xyz.lakmatiol.knight.ast.nullary;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import xyz.lakmatiol.knight.ast.Expression;

@NodeInfo(shortName = "RANDOM")
public class Random extends Expression {
    @Override
    public Object execute(VirtualFrame frame) {
        return (long) (Math.random() * 32767);
    }
}
