package xyz.lakmatiol.knight.ast.binary;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import xyz.lakmatiol.knight.ast.Expression;
import xyz.lakmatiol.knight.ast.Types;

public abstract class ShortCircuit extends Expression {
    @Node.Child
    Expression left;
    @Node.Child
    Expression right;
    private final ConditionProfile erProfile = ConditionProfile.createCountingProfile();
    public ShortCircuit(Expression l, Expression r) {
        left = l;
        right = r;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        boolean l;
        Object leftRes = left.execute(frame);
        l = Types.boolCoerce(leftRes);
        if(erProfile.profile(evalRight(l))) {
             return right.execute(frame);
        }
        return leftRes;
    }

    protected abstract boolean evalRight(boolean leftValue);
}
