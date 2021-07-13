package xyz.lakmatiol.knight.ast.other;


import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.ConditionProfile;
import xyz.lakmatiol.knight.ast.Expression;
import xyz.lakmatiol.knight.ast.Types;

public class If extends Expression{
    @Node.Child
    private Expression cond;
    @Node.Child
    private Expression t;
    @Node.Child
    private Expression f;

    private final ConditionProfile condition = ConditionProfile.createCountingProfile();

    public If(Expression cond, Expression t, Expression f) {
        this.cond = cond;
        this.t = t;
        this.f = f;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        if(condition.profile(executeCond(frame))) {
            return this.t.execute(frame);
        }
        else {
            return this.f.execute(frame);
        }
    }

    public boolean executeCond(VirtualFrame frame) {
        return Types.boolCoerce(cond.execute(frame));
    }
}
