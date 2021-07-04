package xyz.lakmatiol.knight.ast;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import xyz.lakmatiol.knight.types.Null;
import xyz.lakmatiol.knight.types.Proc;

@NodeInfo(shortName = "call")
@NodeChild("funExpr")
public class Call extends Expression {

    @Override
    public Object execute(VirtualFrame frame) {
        return Null.SINGLETON;
    }
}
