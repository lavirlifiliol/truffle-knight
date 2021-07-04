package xyz.lakmatiol.knight.ast.binary;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import xyz.lakmatiol.knight.ast.Expression;

public class Seq extends Expression{
    @Node.Children public Expression[] toRun;

    public Seq(Expression[] toRun) {
        if(toRun.length == 0) {
            throw new RuntimeException("a Seq must have at least one subexpression");
        }
        this.toRun = toRun;
    }


    @Override
    public Object execute(VirtualFrame frame) {
        Object res = null;
        for(var a: toRun) {
            res = a.execute(frame);
        }
        return res;
    }
}
