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
        return executeBool(frame);
    }

    @Override
    public long executeLong(VirtualFrame frame) throws UnexpectedResultException {
        return executeBool(frame)?1:0;
    }

    @Override
    public boolean executeBool(VirtualFrame frame){
        boolean l;
        l = Types.boolCoerce(left.execute(frame));
        boolean r;
        if(erProfile.profile(evalRight(l))) {
            r = Types.boolCoerce(right.execute(frame));
        } else {
            r = false;
        }
        return executeBoolInternal(l, r);
    }
    protected abstract boolean evalRight(boolean leftValue);
    protected abstract boolean executeBoolInternal(boolean leftValue, boolean rightValue);
}
