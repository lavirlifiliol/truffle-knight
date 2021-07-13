package xyz.lakmatiol.knight.ast.binary;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.LoopNode;
import xyz.lakmatiol.knight.ast.Expression;
import xyz.lakmatiol.knight.types.Null;

public class While extends Expression {

    @Child
    private LoopNode loop;

    public While(Expression cond, Expression body) {
        this.loop = Truffle.getRuntime().createLoopNode(new WhileRepeating(cond, body));
    }

    @Override
    public Object execute(VirtualFrame frame) {
        loop.execute(frame);
        return Null.SINGLETON;
    }
}
