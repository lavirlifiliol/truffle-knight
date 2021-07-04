package xyz.lakmatiol.knight.ast.unary;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import xyz.lakmatiol.knight.Knight;
import xyz.lakmatiol.knight.ast.Expression;
import xyz.lakmatiol.knight.ast.Root;

@NodeInfo(shortName = "BLOCK")
public class Block extends Expression {
    @Child private Expression block;
    public Block(Expression contents) {
        this.block = contents;
    }
    @Override
    public Object execute(VirtualFrame frame) {
        return block;
    }
}
