package xyz.lakmatiol.knight.ast;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.*;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;

/*
 * Base class of all nodes, since all nodes have a result
 */
@GenerateWrapper
public abstract class Expression extends Node implements InstrumentableNode {
    @Override
    public boolean isInstrumentable() {
        return true;
    }

    @Override
    public WrapperNode createWrapper(ProbeNode probe) {
        return new ExpressionWrapper(this, probe);
    }

    protected boolean hasExpressionTag;

    @Override
    public boolean hasTag(Class<? extends Tag> tag) {
        return hasExpressionTag && tag == StandardTags.ExpressionTag.class;
    }

    abstract public Object execute(VirtualFrame frame);
    public long executeLong(VirtualFrame frame) throws UnexpectedResultException {
        return TypesGen.expectLong(execute(frame));
    }
    public boolean executeBool(VirtualFrame frame) throws UnexpectedResultException {
        return TypesGen.expectBoolean(execute(frame));
    }
}
