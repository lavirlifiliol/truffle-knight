package xyz.lakmatiol.knight.ast.binary;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.BlockNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import xyz.lakmatiol.knight.ast.Expression;
import xyz.lakmatiol.knight.ast.unary.Block;


/*yes, the implementation is not strictly binary, but it does make it much faster*/
@NodeInfo(shortName = ";")
public class Seq extends Expression implements BlockNode.ElementExecutor<Expression> {
    @Child private BlockNode<Expression> block;

    public Seq(Expression[] toRun) {
        if(toRun.length == 0) {
            throw new RuntimeException("a Seq must have at least one subexpression");
        }
        this.block = BlockNode.create(toRun, this);
    }


    @Override
    public Object execute(VirtualFrame frame) {
        return this.block.executeGeneric(frame, BlockNode.NO_ARGUMENT);
    }

    @Override
    public long executeLong(VirtualFrame frame) throws UnexpectedResultException {
        return this.block.executeLong(frame, BlockNode.NO_ARGUMENT);
    }

    @Override
    public boolean executeBool(VirtualFrame frame) throws UnexpectedResultException {
        return this.block.executeBoolean(frame, BlockNode.NO_ARGUMENT);
    }

    @Override
    public void executeVoid(VirtualFrame frame, Expression node, int index, int argument) {
        node.execute(frame);
    }

    @Override
    public boolean executeBoolean(VirtualFrame frame, Expression node, int index, int argument) throws UnexpectedResultException {
        return node.executeBool(frame);
    }

    @Override
    public Object executeGeneric(VirtualFrame frame, Expression node, int index, int argument) {
        return node.execute(frame);
    }

    @Override
    public long executeLong(VirtualFrame frame, Expression node, int index, int argument) throws UnexpectedResultException {
        return node.executeLong(frame);
    }
}
