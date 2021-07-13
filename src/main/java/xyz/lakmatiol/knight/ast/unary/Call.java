package xyz.lakmatiol.knight.ast.unary;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import xyz.lakmatiol.knight.ast.Expression;
import xyz.lakmatiol.knight.ast.Types;

public abstract class Call extends Unary {
    @Specialization
    public Object callNode( VirtualFrame frame, Expression s) {
        return s.execute(frame);
    }
    @Specialization
    public Object callGeneric(Object s) {
        Types.errorUB();
        return null;
    }
}
