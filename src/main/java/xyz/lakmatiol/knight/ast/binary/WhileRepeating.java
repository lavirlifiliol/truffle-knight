package xyz.lakmatiol.knight.ast.binary;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RepeatingNode;
import xyz.lakmatiol.knight.ast.Expression;
import xyz.lakmatiol.knight.ast.Types;

public class WhileRepeating extends Node implements RepeatingNode {
    @Child
    private Expression cond;
    @Child
    private Expression body;

    public WhileRepeating(Expression cond, Expression body) {
        this.cond = cond;
        this.body = body;
    }


    @Override
    public boolean executeRepeating(VirtualFrame frame) {
        if(!evalCond(frame)){
            return false;
        }
        body.execute(frame);
        return true;
    }

    private boolean evalCond(VirtualFrame frame) {
        return Types.boolCoerce(cond.execute(frame));
    }
}
